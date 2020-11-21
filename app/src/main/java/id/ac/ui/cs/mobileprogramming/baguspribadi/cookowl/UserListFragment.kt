package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.UserListAdapter
//import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.(DBHelper.DBHelper
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.UserViewModel
import kotlinx.android.synthetic.main.user_list_fragment.view.*

class UserListFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_list_fragment, container, false)

        val adapter = UserListAdapter()
        val recyclerView = view.list_all_user
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        return view
    }

    companion object{
        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}