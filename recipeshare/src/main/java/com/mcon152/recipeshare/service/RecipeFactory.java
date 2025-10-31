package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.*;
import com.mcon152.recipeshare.web.RecipeRequest;

public class RecipeFactory {

    public static Recipe createFromRequest(RecipeRequest req) {
        String type = req != null && req.getType() != null ? req.getType().trim().toUpperCase() : "BASIC";

        Recipe out;
        switch (type) {
            case "VEGETARIAN":
                out = new VegetarianRecipe();
                break;
            case "DESSERT":
                out = new DessertRecipe();
                break;
            case "DAIRY":
                out = new DairyRecipe();
                break;
            case "SOUP":
                out = new SoupRecipe();
                break;
            case "BASIC":
            default:
                out = new BasicRecipe();
                break;
        }

         // Ensure new entity and safely copy common fields only if req provided
        out.setId(null);
        if (req != null) {
            out.setTitle(req.getTitle());
            out.setDescription(req.getDescription());
            out.setIngredients(req.getIngredients());
            out.setInstructions(req.getInstructions());
            out.setServings(req.getServings());
        }

        return out;
    }
}
