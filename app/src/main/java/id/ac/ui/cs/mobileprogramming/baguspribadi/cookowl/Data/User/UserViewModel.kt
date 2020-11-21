package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.UserDatabase
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application)!!.userDao()
        repository =
            UserRepository(
                userDao
            )
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}