package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllUser()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun findUserByEmail(email: String): List<User>{
        return userDao.findUserByEmail(email)
    }
}