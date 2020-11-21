package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 2, exportSchema = false)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object{
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "transaction_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}