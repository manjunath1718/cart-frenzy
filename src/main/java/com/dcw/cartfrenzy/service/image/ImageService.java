package com.dcw.cartfrenzy.service.image;

import com.dcw.cartfrenzy.dto.ImageDto;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Image;
import com.dcw.cartfrenzy.model.Product;
import com.dcw.cartfrenzy.repository.ImageRepository;
import com.dcw.cartfrenzy.repository.ProductRepository;
import com.dcw.cartfrenzy.util.OwnershipUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final OwnershipUtil ownershipUtil;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, OwnershipUtil ownershipUtil) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.ownershipUtil = ownershipUtil;
    }

    @Override
    public Image getImageById(Long id) {

        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" No image found with id " + id));
    }

    @Override
    public void deleteImageById(Long id) {

        Image image = getImageById(id);

        checkOwnership(image.getProduct().getId());

        imageRepository.deleteById(id);
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {

        checkOwnership(productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No product found with id " + productId));

        ArrayList<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files) {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);

                image.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDtos.add(imageDto);

            } catch (SQLException | IOException e) {

                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

        Image image = getImageById(imageId);

        checkOwnership(image.getProduct().getId());

        try {
            image.setFileName(file.getName());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));

            imageRepository.save(image);
        } catch (SQLException | IOException e) {

            throw new RuntimeException(e.getMessage());

        }

    }

    private void checkOwnership(Long productId) {

        ownershipUtil.checkOwnership(productId);

    }

}
