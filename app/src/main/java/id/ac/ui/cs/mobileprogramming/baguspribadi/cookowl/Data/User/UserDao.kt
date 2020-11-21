package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE email LIKE :email")
    fun findUserByEmail(email: String): List<User>
}