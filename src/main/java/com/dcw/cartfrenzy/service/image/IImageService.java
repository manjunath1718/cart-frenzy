package com.dcw.cartfrenzy.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dcw.cartfrenzy.dto.ImageDto;
import com.dcw.cartfrenzy.model.Image;

public interface IImageService {

	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto> saveImages(Long productId,List<MultipartFile> files);
	void updateImage(MultipartFile file,Long imageId);
	
}
