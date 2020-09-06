package com.springframework.recipeproject.boostrap;

import com.springframework.recipeproject.domain.*;
import com.springframework.recipeproject.repositories.CategoryRepository;
import com.springframework.recipeproject.repositories.RecipeRepository;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBoostrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBoostrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipies() {
        List<Recipe> recipes = new ArrayList<>();

        Optional<UnitOfMeasure> mlUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("ml");
        Optional<UnitOfMeasure> litreUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("L");
        Optional<UnitOfMeasure> gramUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("g");
        Optional<UnitOfMeasure> kilogramUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Kg");
        Optional<UnitOfMeasure> teaspoonUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("tsp");
        Optional<UnitOfMeasure> tablespoonUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("tbsp");

        UnitOfMeasure mlUnitOfMeasure = mlUnitOfMeasureOptional.get();
        UnitOfMeasure litreUnitOfMeasure = litreUnitOfMeasureOptional.get();
        UnitOfMeasure gramUnitOfMeasure = gramUnitOfMeasureOptional.get();
        UnitOfMeasure kilogramUnitOfMeasure = kilogramUnitOfMeasureOptional.get();
        UnitOfMeasure teaspoonUnitOfMeasure = teaspoonUnitOfMeasureOptional.get();
        UnitOfMeasure tablespoonUnitOfMeasure = tablespoonUnitOfMeasureOptional.get();

        Optional<Category> frenchCategoryOptional = categoryRepository.findByDescription("French");
        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        Optional<Category> ChineseCategoryOptional = categoryRepository.findByDescription("Chinese");

        Category frenchCategory = frenchCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category chineseCategory = ChineseCategoryOptional.get();

        Recipe testRecipe = new Recipe();
        testRecipe.setDescription("Test");
        testRecipe.setPrepTime(30);
        testRecipe.setCookTime(90);
        testRecipe.setDifficulty(Difficulty.MEDIUM);
        testRecipe.setDirections("Mix Ingredients. Heat oven. Place in oven. Take out from oven");

        Notes testNotes = new Notes();
        testNotes.setRecipeNotes("Be safe");
        testRecipe.setNotes(testNotes);

        testRecipe.addIngredient(new Ingredient("milk", new BigDecimal(300), mlUnitOfMeasure, testRecipe));
        testRecipe.addIngredient(new Ingredient("flour", new BigDecimal(450), gramUnitOfMeasure, testRecipe));

        testRecipe.getCategories().add(italianCategory);

        recipes.add(testRecipe);

        return recipes;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipies());
    }
}
