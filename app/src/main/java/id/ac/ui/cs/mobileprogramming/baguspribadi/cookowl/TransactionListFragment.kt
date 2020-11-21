package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.RecipeListAdapter
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.TransactionListAdapter
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.TransactionViewModel
import kotlinx.android.synthetic.main.recipe_list_fragment.view.*
import kotlinx.android.synthetic.main.transaction_list_fragment.view.*

class TransactionListFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.transaction_list_fragment, container, false)

        val adapter = TransactionListAdapter()
        val recyclerView = view.list_all_transaction
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        transactionViewModel.readAllData.observe(viewLifecycleOwner, Observer { transaction ->
            adapter.setData(transaction)
        })

        return view
    }

    companion object{
        @JvmStatic
        fun newInstance() = TransactionListFragment()
    }
}