package com.example.ImagemPecas.application.images;

import com.example.ImagemPecas.domain.entity.Image;
import com.example.ImagemPecas.domain.service.ImageService;
import com.example.ImagemPecas.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceimpl implements ImageService {
    private  final ImageRepository repository;
    @Override
    @Transactional
    public Image save(Image image) {
        return repository.save(image);
    }

    @Override
    public Optional<Image> getById(String id) {
        return repository.findById(id);
    }
}
