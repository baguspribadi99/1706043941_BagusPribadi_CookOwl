package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.Recipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.UserDao

class TransactionRepository(private val transactionDao: TransactionDao) {

    val readAllData: LiveData<List<Transaction>> = transactionDao.readAllTransaction()

    suspend fun addTransaction(transaction: Transaction){
        transactionDao.addTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction){
        transactionDao.updateTransaction(transaction)
    }
}