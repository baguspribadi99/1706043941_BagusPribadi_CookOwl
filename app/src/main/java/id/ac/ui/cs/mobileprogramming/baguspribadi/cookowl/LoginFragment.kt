package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.UserListAdapter
//import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.DBHelper.DBHelper
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.UserViewModel
import kotlinx.android.synthetic.main.register_fragment.*

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val btnLogin = view.findViewById<Button>(R.id.login)
        btnLogin.setOnClickListener() {
            loginCheck()
        }

        val btnRegister = view.findViewById<Button>(R.id.register)
        btnRegister.setOnClickListener() {
            (context as MainActivity).loadFragment(RegisterFragment.newInstance(), true)
        }

        val btnLang = view.findViewById<Button>(R.id.edt_lang)
        btnLang.setOnClickListener() {
            if((context as MainActivity).language.equals("en")) {
                (context as MainActivity).changeLocale("in")
            }
            else{
                (context as MainActivity).changeLocale("en")
            }

//            val listLang = arrayOf("English", "Indonesia")
//
//            val mBuilder = AlertDialog.Builder(context as MainActivity)
//            mBuilder.setTitle("Choose Language")
//            mBuilder.setSingleChoiceItems(listLang, -1) {dialog, which ->
//                if(which == 0){
//            (context as MainActivity).setLocale("en")
//              }
//            else{
//                  setLocale("id-rID")
//              }
//        }
//            }

        }
        return view
    }

    private fun loginCheck() {
        var userLogin: User? = null
        val email = edt_email.text.toString()
        val password = edt_password.text.toString()

        val adapter = UserListAdapter()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
            userLogin = adapter.loginCheck(email, password)
        })
        (context as MainActivity).login(userLogin, "login")
    }

    private fun inputCheck(email: String, name:String, password: String): Boolean {
        return (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
    }

    companion object{
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}