package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.RecipeListAdapter
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.RecipeViewModel
import kotlinx.android.synthetic.main.recipe_list_fragment.view.*

class RecipeListFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_list_fragment, container, false)

        view.greet.text = "Welcome " + (context as MainActivity).userLoggedIn!!.name.toString() + "!"

        val adapter = RecipeListAdapter()
        val recyclerView = view.list_all_recipe
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.readAllData.observe(viewLifecycleOwner, Observer { recipe ->
            adapter.setData(recipe)
        })

        val btnNewRecipe = view.findViewById<FloatingActionButton>(R.id.add_recipe)
        btnNewRecipe.setOnClickListener() {
            (context as MainActivity).loadFragment(RecipeAddFragment.newInstance(), true)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_transaction, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.history) {
            (context as MainActivity).loadFragment(TransactionListFragment.newInstance(), true)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        @JvmStatic
        fun newInstance() = RecipeListFragment()
    }
}