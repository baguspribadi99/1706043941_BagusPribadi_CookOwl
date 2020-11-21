package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.UserListAdapter
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
            if((context as MainActivity).language.equals("in")) {
                (context as MainActivity).changeLocale("en")
            }
            else{
                (context as MainActivity).changeLocale("in")
            }
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

    companion object{
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}