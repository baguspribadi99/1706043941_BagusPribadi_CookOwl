package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User

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