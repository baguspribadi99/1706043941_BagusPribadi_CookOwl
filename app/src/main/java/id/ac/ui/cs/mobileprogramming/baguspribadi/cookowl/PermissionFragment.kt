package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.Transaction
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.TransactionViewModel
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity.Companion.nowSeconds
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity.Companion.removeAlarm
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity.Companion.setAlarm
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util.PrefUtil
import kotlinx.android.synthetic.main.timer_fragment.*
import java.lang.NumberFormatException

class PermissionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.permission_needed_fragment, container, false)

        val btnOk = view.findViewById<Button>(R.id.ok)
        btnOk.setOnClickListener() {
            (context as MainActivity).restartApp()
        }

        return view
    }


    companion object{
        @JvmStatic
        fun newInstance() =
            PermissionFragment()
    }
}