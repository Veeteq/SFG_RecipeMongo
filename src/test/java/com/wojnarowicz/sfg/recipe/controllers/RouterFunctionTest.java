package com.wojnarowicz.sfg.recipe.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

import com.wojnarowicz.sfg.recipe.config.WebConfig;
import com.wojnarowicz.sfg.recipe.domain.Recipe;
import com.wojnarowicz.sfg.recipe.services.RecipeService;

import reactor.core.publisher.Flux;

class RouterFunctionTest {

    WebTestClient webTestClient;
    
    @Mock
    RecipeService recipeService;
        
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        WebConfig webConfig = new WebConfig();
        
        RouterFunction<?> routerFunction = webConfig.routes(recipeService);
        
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void testApiRecipes() {
        when(recipeService.getRecipes()).thenReturn(Flux.just());
        
        webTestClient.get().uri("/api/recipes")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();
    }

    @Test
    void testApiRecipesWithData() {
        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe()));
        
        webTestClient.get().uri("/api/recipes")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Recipe.class);
    }
}
