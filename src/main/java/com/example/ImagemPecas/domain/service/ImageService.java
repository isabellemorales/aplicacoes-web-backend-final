package com.example.ImagemPecas.domain.service;

import com.example.ImagemPecas.domain.entity.Image;
import java.util.Optional;

public interface ImageService {
    Image save (Image image);

    Optional<Image> getById(String id);

}
