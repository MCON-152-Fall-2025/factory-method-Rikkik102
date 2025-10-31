package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.*;
import com.mcon152.recipeshare.web.RecipeRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeFactoryTest {

    @Test
    void createsBasicByDefault_andCopiesFields() {
        RecipeRequest req = new RecipeRequest();
        req.setTitle("Title");
        req.setDescription("Desc");
        req.setIngredients("I");
        req.setInstructions("N");
        req.setServings(3);

        Recipe r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof BasicRecipe);
        assertNull(r.getId());
        assertEquals("Title", r.getTitle());
        assertEquals("Desc", r.getDescription());
        assertEquals("I", r.getIngredients());
        assertEquals("N", r.getInstructions());
        assertEquals(Integer.valueOf(3), r.getServings());
    }

    @Test
    void createsSpecifiedSubtypes_caseInsensitive() {
        RecipeRequest req = new RecipeRequest();
        req.setType("vegetarian");
        Recipe r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof VegetarianRecipe);

        req.setType("DESSERT");
        r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof DessertRecipe);

        req.setType("DAIRY");
        r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof DairyRecipe);

        req.setType("SOUP");
        r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof SoupRecipe);

        req.setType("BASIC");
        r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof BasicRecipe);
    }

    @Test
    void nullRequest_returnsBasic_withNullFields() {
        Recipe r = RecipeFactory.createFromRequest(null);
        assertTrue(r instanceof BasicRecipe);
        assertNull(r.getTitle());
        assertNull(r.getDescription());
        assertNull(r.getIngredients());
        assertNull(r.getInstructions());
        assertNull(r.getServings());
    }

    @Test
    void unknownType_defaultsToBasic() {
        RecipeRequest req = new RecipeRequest();
        req.setType("UNKNOWN");
        Recipe r = RecipeFactory.createFromRequest(req);
        assertTrue(r instanceof BasicRecipe);
    }
}

