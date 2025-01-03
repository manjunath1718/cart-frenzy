package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.dto.ImageDto;
import com.dcw.cartfrenzy.model.Image;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService imageService;

    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {

        List<ImageDto> imageDtos = imageService.saveImages(productId, files);

        return ResponseEntity.ok(new ApiResponse("Upload succes!", imageDtos));
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {

        Image image = imageService.getImageById(imageId);

        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) throws SQLException {

        Image image = imageService.getImageById(imageId);

        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .body(resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/image/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {

        imageService.updateImage(file, imageId);

        return ResponseEntity.ok().body(new ApiResponse("Update success!", null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("image/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {

        imageService.deleteImageById(imageId);

        return ResponseEntity.ok().body(new ApiResponse("delete success!", null));
    }

}
