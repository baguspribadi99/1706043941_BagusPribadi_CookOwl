package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY id ASC")
    fun readAllTransaction(): LiveData<List<Transaction>>
}