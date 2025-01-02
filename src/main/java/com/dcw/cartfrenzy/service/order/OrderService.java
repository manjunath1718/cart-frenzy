package com.dcw.cartfrenzy.service.order;

import com.dcw.cartfrenzy.dto.OrderDto;
import com.dcw.cartfrenzy.enums.OrderStatus;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Cart;
import com.dcw.cartfrenzy.model.Order;
import com.dcw.cartfrenzy.model.OrderItem;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.repository.OrderRepository;
import com.dcw.cartfrenzy.repository.ProductRepository;
import com.dcw.cartfrenzy.service.cart.ICartService;
import com.dcw.cartfrenzy.util.OrderConverter;
import com.dcw.cartfrenzy.util.OwnershipUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final ICartService cartService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    public OrderService(ICartService cartService, ProductRepository productRepository, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;

    }

    @Transactional
    @Override
    public OrderDto placeOrder(Long userId) {

        checkOwnership(userId);
        Cart cart = cartService.getCartByUserId();
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart();

        return convertToDto(savedOrder);
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {

        return items.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {

        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }

    private Order createOrder(Cart cart) {

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    @Override
    public OrderDto getOrder(Long orderId) {

        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found "));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {

        return OrderConverter.convertToDto(order);
    }


    public void checkOwnership(Long userId) {

        if (!OwnershipUtil.getConsumerId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to modify this product");
        }
    }


}
