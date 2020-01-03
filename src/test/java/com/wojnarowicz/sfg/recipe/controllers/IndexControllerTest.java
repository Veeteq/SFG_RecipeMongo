package com.wojnarowicz.sfg.recipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.ui.Model;

import com.wojnarowicz.sfg.recipe.domain.Recipe;
import com.wojnarowicz.sfg.recipe.services.RecipeService;

import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
//@WebFluxTest
@SpringBootTest
@AutoConfigureWebTestClient
public class IndexControllerTest {

    @Autowired
    WebTestClient webTestClient;
    
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        controller = new IndexController(recipeService);
        
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void testMockMVC() throws Exception {

        BDDMockito.given(recipeService.getRecipes()).willReturn(Flux.just());
        
        webTestClient.get()
        .uri("/")
        .exchange()
        .expectStatus().isOk();
    }

    @Test
    public void getIndexPage() throws Exception {

        //given
        Set<Recipe> recipes = new HashSet<>();
        
        Recipe recipe1 = new Recipe();
        recipe1.setId("1");
        recipes.add(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setId("2");
        recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Flux<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Flux.class);

        //when
        String viewName = controller.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Flux<Recipe> fluxValues = argumentCaptor.getValue();
        List<Recipe> recipeList = fluxValues.collectList().block(); 
        assertEquals(2, recipeList.size());
    }
}