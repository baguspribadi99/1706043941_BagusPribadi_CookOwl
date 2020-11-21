package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Transaction>>
    private val repository: TransactionRepository

    init {
        val transactionDao = TransactionDatabase.getDatabase(application)!!.transactionDao()
        repository =
            TransactionRepository(
                transactionDao
            )
        readAllData = repository.readAllData
    }

    fun addTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransaction(transaction)
        }
    }
}