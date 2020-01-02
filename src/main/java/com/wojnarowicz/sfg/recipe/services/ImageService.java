package com.wojnarowicz.sfg.recipe.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jt on 7/3/17.
 */
public interface ImageService {

    void saveImageFile(String recipeId, MultipartFile file);
}
