package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.Recipe;
import com.mcon152.recipeshare.service.RecipeFactory;
import com.mcon152.recipeshare.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Create a new recipe.
     * Returns 201 Created with Location header pointing to the new resource.
     */
    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeRequest recipeRequest) {
        // TRACE REQUEST
        MDC.put("recipeName", recipeRequest.getTitle());
        logger.info("Received request: POST /api/recipes");
        logger.debug("addRecipe request body summary: name={}, type={}",
                recipeRequest.getTitle(), recipeRequest.getType());

        try {
            Recipe toSave = RecipeFactory.createFromRequest(recipeRequest);
            Recipe saved = recipeService.addRecipe(toSave);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()           // /api/recipes
                    .path("/{id}")                  // /{id}
                    .buildAndExpand(saved.getId())
                    .toUri();

            logger.info("Successfully created recipe with id={}", saved.getId());
            return ResponseEntity.created(location).body(saved);
        } catch (Exception e) {
            logger.error("Error occurred while adding recipe: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        } finally {
            MDC.remove("recipeName");
        }
    }

    /**
     * Retrieve all recipes. 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        logger.info("Received request: GET /api/recipes");

        try {
            List<Recipe> recipes = recipeService.getAllRecipes();
            logger.info("Successfully retrieved {} recipes", recipes.size());
            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all recipes: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieve a recipe by id. 200 OK or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        logger.info("Received request: GET /api/recipes/{}", id);

        try {
            return recipeService.getRecipeById(id)
                    .map(recipe -> {
                        logger.info("Successfully found recipe with id={}", id);
                        return ResponseEntity.ok(recipe);
                    })
                    .orElseGet(() -> {
                        logger.warn("Recipe not found with id={}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error occurred while retrieving recipe with id={}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Delete a recipe. 204 No Content if deleted, 404 Not Found otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        logger.info("Received request: DELETE /api/recipes/{}", id);

        try {
            boolean deleted = recipeService.deleteRecipe(id);
            if (deleted) {
                logger.info("Successfully deleted recipe with id={}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("Attempted to delete non-existing recipe with id={}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting recipe with id={}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Replace a recipe (full update). 200 OK with updated entity or 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @RequestBody RecipeRequest updatedRequest) {
        MDC.put("recipeName", updatedRequest.getTitle());
        logger.info("Received request: PUT /api/recipes/{}", id);
        logger.debug("updateRecipe request body summary: name={}, type={}",
                updatedRequest.getTitle(), updatedRequest.getType());

        try {
            Recipe updatedRecipe = RecipeFactory.createFromRequest(updatedRequest);
            return recipeService.updateRecipe(id, updatedRecipe)
                    .map(recipe -> {
                        logger.info("Successfully updated recipe with id={}", id);
                        return ResponseEntity.ok(recipe);
                    })
                    .orElseGet(() -> {
                        logger.warn("Cannot update; recipe not found with id={}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error occurred while updating recipe with id={}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        } finally {
            MDC.remove("recipeName");
        }
    }

    /**
     * Partial update. 200 OK with updated entity or 404 Not Found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> patchRecipe(@PathVariable long id, @RequestBody RecipeRequest partialRequest) {
        MDC.put("recipeName", partialRequest.getTitle());
        logger.info("Received request: PATCH /api/recipes/{}", id);
        logger.debug("patchRecipe request body summary: name={}, type={}",
                partialRequest.getTitle(), partialRequest.getType());

        try {
            Recipe partialRecipe = RecipeFactory.createFromRequest(partialRequest);
            return recipeService.patchRecipe(id, partialRecipe)
                    .map(recipe -> {
                        logger.info("Successfully patched recipe with id={}", id);
                        return ResponseEntity.ok(recipe);
                    })
                    .orElseGet(() -> {
                        logger.warn("Cannot patch; recipe not found with id={}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error occurred while patching recipe with id={}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        } finally {
            MDC.remove("recipeName");
        }
    }
}
