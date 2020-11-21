package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction

import androidx.lifecycle.LiveData

class TransactionRepository(private val transactionDao: TransactionDao) {

    val readAllData: LiveData<List<Transaction>> = transactionDao.readAllTransaction()

    suspend fun addTransaction(transaction: Transaction){
        transactionDao.addTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction){
        transactionDao.updateTransaction(transaction)
    }
}