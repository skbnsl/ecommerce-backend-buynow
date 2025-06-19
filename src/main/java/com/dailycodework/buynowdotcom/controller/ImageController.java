package com.dailycodework.buynowdotcom.controller;

import com.dailycodework.buynowdotcom.dtos.ImageDto;
import com.dailycodework.buynowdotcom.model.Image;
import com.dailycodework.buynowdotcom.response.ApiResponse;
import com.dailycodework.buynowdotcom.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("${api.prefix}/images")
@RequestMapping("/api/v1/images")
public class ImageController {
    private final IImageService iImageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImages(
                                        @RequestParam("files") List<MultipartFile> files,
                                        @RequestParam("productId") Long productId){
        try {
            List<ImageDto> imageDto = iImageService.saveImage(productId,files);
            return ResponseEntity.ok(new ApiResponse("Images Uploaded Successfully!!", imageDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("upload Image error",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename\"" + image.getFileName() +"\"")
                .body(resource);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("productId") Long imageId){
        try {
            iImageService.updateImage(file,imageId);
            return ResponseEntity.ok(new ApiResponse("Image Updated Successfully!!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("update Image error",e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            iImageService.deleteImageById(imageId);
            return ResponseEntity.ok(new ApiResponse("Image Deleted Successfully!!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Delete Image error",e.getMessage()));
        }
    }

}
