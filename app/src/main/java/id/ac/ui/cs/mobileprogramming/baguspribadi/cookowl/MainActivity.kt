package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.Recipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.Transaction
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util.PrefUtil
import java.io.File
import java.util.*

private const val requestCode = 79
private const val photoFile = "photo_"
private lateinit var picture: File
class MainActivity : AppCompatActivity() {

    var userLoggedIn: User? = null
    var language: String? = ""
    var pictureTaken: Bitmap? = null

    private val PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE)
        }
        else {
            loadFragment(LoginFragment.newInstance(), false)
        }
    }

    fun loadFragment(fragment: Fragment, back: Boolean){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if(!back) {
            transaction.disallowAddToBackStack()
        }
        else{
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun passRecipe(fragment: Fragment, recipe: Recipe){
        val bundle = Bundle()
        bundle.putInt("recipe_id", recipe.id)
        bundle.putString("recipe_name", recipe.name)
        bundle.putString("recipe_ingredient", recipe.ingredient)
        bundle.putString("recipe_step", recipe.step)
        bundle.putInt("recipe_time", recipe.time)
        bundle.putString("recipe_photo_path", recipe.photo)
        if(recipe.photo != null) {
            setPictureFile(recipe.photo)
            pictureTaken = BitmapFactory.decodeFile(picture.absolutePath)
        }
        fragment.setArguments(bundle)
        loadFragment(fragment, true)
    }

    fun passTransaction(fragment: Fragment, transaction: Transaction){
        val bundle = Bundle()
        bundle.putInt("transaction_id", transaction.id)
        bundle.putInt("transaction_chef_id", transaction.chef_id)
        bundle.putString("transaction_chef_name", transaction.chef_name)
        bundle.putInt("transaction_meal_id", transaction.meal_id)
        bundle.putString("transaction_meal_name", transaction.meal_name)
        bundle.putString("transaction_time", transaction.time)
        fragment.setArguments(bundle)
        loadFragment(fragment, true)
    }

    fun login(user: User?, action: String){
        if(user == null) {
            Toast.makeText(
                this@MainActivity, "$action failed!",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            Toast.makeText(
                this@MainActivity, "$action successful!",
                Toast.LENGTH_SHORT
            ).show()
            userLoggedIn = user
            loadFragment(RecipeListFragment.newInstance(), false)
        }
    }

    fun changeLocale(lang: String){
        Log.i("lang", lang)
        setLocale(lang)
        recreate()
    }

    fun setLocale(lang: String){
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
        language = lang
    }

    private fun loadLocale(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if(language != null) {
            setLocale(language)
        }
    }

    fun takePhoto(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        picture = getPicture(photoFile)

        val fileProvider = FileProvider.getUriForFile(this, "id.ac.ui.cs.mobileprogramming.baguspribadi.fileprovider", picture)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        startActivityForResult(takePictureIntent, requestCode)
    }

    private fun getPicture(filename: String): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename, ".jpg", storageDir)
    }

    fun getPictureFile(): File {
        return picture
    }

    fun setPictureFile(picturePath: String) {
        picture = File(picturePath)
    }

    fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finishAffinity()
    }

    fun removeAlarm(context: Context){
        val intent = Intent(context, TimerExpiredReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        PrefUtil.setAlarmSetTime(0, context)
    }

    fun isOnline(): Boolean {
        var isOnline = false
        val connectivityManager = this@MainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null && connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null) {
            isOnline = true
        }
        else{
            Toast.makeText(this@MainActivity, "No Network Detected!", Toast.LENGTH_SHORT).show()
        }
        return isOnline
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == requestCode && resultCode == Activity.RESULT_OK){
            val takenImage = BitmapFactory.decodeFile(picture.absolutePath)
            pictureTaken = takenImage
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty() ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
                    loadFragment(PermissionFragment.newInstance(), false)
                } else {
                    Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                    loadFragment(LoginFragment.newInstance(), false)
                }
            }
        }
    }

    companion object {
        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

        fun setAlarm(context: Context, seconds: Long, remaining: Long):Long {
            val wakeUpTime = (seconds + remaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0,intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }
    }
}