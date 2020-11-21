package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDao) {

    val readAllData: LiveData<List<Recipe>> = recipeDao.readAllRecipe()

    suspend fun addRecipe(recipe: Recipe){
        recipeDao.addRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe){
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe){
        recipeDao.deleteRecipe(recipe)
    }
}