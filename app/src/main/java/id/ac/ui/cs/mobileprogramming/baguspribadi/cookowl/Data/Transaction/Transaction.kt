package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val chef_id: Int,
    val chef_name: String,
    val meal_id: Int,
    val meal_name: String,
    val time: String,
    val status: Boolean
){}