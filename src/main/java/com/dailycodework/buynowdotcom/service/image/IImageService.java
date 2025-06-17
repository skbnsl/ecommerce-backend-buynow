package com.dailycodework.buynowdotcom.service.image;

import com.dailycodework.buynowdotcom.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    List<Image> saveImaged(Long productId, List<MultipartFile> files);
}