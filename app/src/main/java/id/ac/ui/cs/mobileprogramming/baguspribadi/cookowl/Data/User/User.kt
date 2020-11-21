package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val name: String,
    val password: String
){}