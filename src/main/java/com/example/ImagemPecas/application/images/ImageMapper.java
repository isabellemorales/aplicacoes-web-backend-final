package com.example.ImagemPecas.application.images;


import com.example.ImagemPecas.domain.entity.Image;
import com.example.ImagemPecas.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ImageMapper {
    public Image mapToImage(MultipartFile file, String name, List<String> tags)  throws IOException {
        return Image.builder()
                .name(name)
                .tags(String.join(",", tags))
                .size(file.getSize())
                .extension(ImageExtension.valueof(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();
    }
    public ImageDTO imageToDTO(Image image, String url){
        return ImageDTO.builder()
                .url(url)
                .extension(image.getExtension().name())
                .name(image.getName())
                //.size(image.getSize())
                .uploadDate(image.getUploadingDate().toLocalDate())
                .build();
    }
}
