package com.example.ImagemPecas.application.images;

import com.example.ImagemPecas.domain.entity.Image;
import com.example.ImagemPecas.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor

public class ImagesController {

    private final ImageService service;
    private final ImageMapper mapper;

    @PostMapping

    public ResponseEntity save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
    ) throws IOException {
        log.info("Imagem Recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());
        // log.info("Nome definido para Imagem: {}", name);
        // log.info("Tags: {}", tags);
        //log.info("Content type: {}", file.getContentType());

        Image image = mapper.mapToImage(file, name, tags);
        Image saveImage = service.save(image);

        URI imageUri = buildImageURL(saveImage);
        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping("{id}")

    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        var possibleImage = service.getById(id);
        if (possibleImage.isEmpty()) {
        return ResponseEntity.notFound().build();

    }
        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData("inline; filename = \"" + image.getFileName() + "\"",image.getFileName());

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
}
    private URI buildImageURL(Image image){
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
    }
}

