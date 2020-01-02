package com.wojnarowicz.sfg.recipe.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wojnarowicz.sfg.recipe.domain.Recipe;
import com.wojnarowicz.sfg.recipe.repositories.RecipeReactiveRepository;
import com.wojnarowicz.sfg.recipe.services.ImageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
        .map(recipe -> {
            Byte[] byteObjects = new Byte[0];            
            try {
                byteObjects = new Byte[file.getBytes().length];
                int i = 0;
                for (byte b : file.getBytes()){
                    byteObjects[i++] = b;
                }
                recipe.setImage(byteObjects);
                return recipe;
            } catch (IOException e) {
                log.error("Error occurred", e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        
        recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}
