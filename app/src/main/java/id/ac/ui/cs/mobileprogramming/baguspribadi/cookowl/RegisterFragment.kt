package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.DBHelper.DBHelper
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.UserViewModel
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val btnRegister = view.findViewById<Button>(R.id.submit)
        btnRegister.setOnClickListener() {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        var userCreated: User? = null
        val email = edt_email.text.toString()
        val name = edt_name.text.toString()
        val password = edt_password.text.toString()

        var valid = inputCheck(email, name, password)
        if(valid){
            userCreated =
                User(
                    0,
                    email,
                    name,
                    password
                )
            userViewModel.addUser(userCreated)
        }
        (context as MainActivity).login(userCreated, "register")
    }

    private fun inputCheck(email: String, name:String, password: String): Boolean {
        return (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
    }

    companion object{
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}