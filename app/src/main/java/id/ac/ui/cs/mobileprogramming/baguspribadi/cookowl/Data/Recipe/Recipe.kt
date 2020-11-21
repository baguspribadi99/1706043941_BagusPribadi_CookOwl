package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val photo: String?,
    val time: Int,
    val ingredient: String,
    val step: String
){}