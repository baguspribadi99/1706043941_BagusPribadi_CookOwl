package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

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