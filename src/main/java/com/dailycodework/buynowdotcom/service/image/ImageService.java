package com.dailycodework.buynowdotcom.service.image;

import com.dailycodework.buynowdotcom.model.Image;
import com.dailycodework.buynowdotcom.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image Not Found!!"));
    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository :: delete, ()-> {
            throw new EntityNotFoundException("Image Not Found!");
        });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Image> saveImaged(Long productId, List<MultipartFile> files) {
        return List.of();
    }
}
