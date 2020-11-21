package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.app.AlertDialog
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.Recipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.RecipeViewModel
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.Transaction
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.TransactionViewModel
import kotlinx.android.synthetic.main.recipe_detail_fragment.view.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class RecipeDetailFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private var recipe_id: Int = 0
    private lateinit var recipe_name: String
    private var recipe_time: Int = 0
    private lateinit var recipe_ingredient: String
    private lateinit var recipe_step: String
    private var recipe_photo: Bitmap? = null
    private var recipe_photo_path: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.recipe_detail_fragment, container, false)
        } else {
            view = inflater.inflate(R.layout.recipe_detail_fragment, container, false)
        }

        recipe_id = this.requireArguments().getInt("recipe_id")
        recipe_time = this.requireArguments().getInt("recipe_time")
        recipe_name = this.requireArguments().getString("recipe_name").toString()
        recipe_ingredient = this.requireArguments().getString("recipe_ingredient").toString()
        recipe_step = this.requireArguments().getString("recipe_step").toString()
        recipe_photo_path = this.requireArguments().getString("recipe_photo_path").toString()
        recipe_photo = (context as MainActivity).pictureTaken

        view.edt_name.text = recipe_name
        view.edt_time.text = recipe_time.toString()
        view.edt_ingredient.text = recipe_ingredient
        view.edt_step.text = recipe_step
        if(recipe_photo != null){
            view.recipe_photo.setImageBitmap(recipe_photo)
        }

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val btnEditRecipe = view.findViewById<Button>(R.id.edit)
        btnEditRecipe.setOnClickListener() {
            (context as MainActivity).passRecipe(RecipeAddFragment.newInstance(),
                Recipe(recipe_id, recipe_name, recipe_photo_path, recipe_time, recipe_ingredient, recipe_step))
        }

        val btnCreateRecipe = view.findViewById<Button>(R.id.create)
        btnCreateRecipe.setOnClickListener() {
            cook_meal()
        }

        val btnDeleteRecipe = view.findViewById<Button>(R.id.delete)
        btnDeleteRecipe.setOnClickListener() {
            delete_recipe()
        }

        return view
    }

    private fun cook_meal() {
        val chef_id = (context as MainActivity).userLoggedIn!!.id
        val chef_name = (context as MainActivity).userLoggedIn!!.name

        val transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        val transactionCreated =
            Transaction(
                0,
                chef_id,
                chef_name,
                recipe_id,
                recipe_name,
                DateTimeFormatter
                    .ofPattern("HH:mm:ss dd-MM-yyyy ")
                    .withZone(ZoneOffset.UTC)
                    .format(Instant.now()).toString(),
                false
            )
        transactionViewModel.addTransaction(transactionCreated)
        (context as MainActivity).passTransaction(TimerFragment.newInstance(), transactionCreated)
    }

    private fun delete_recipe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            recipeViewModel.deleteRecipe(
                Recipe(recipe_id, recipe_name, recipe_photo_path, recipe_time, recipe_ingredient, recipe_step))
            (context as MainActivity).loadFragment(RecipeListFragment.newInstance(), false)
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete Recipe?")
        builder.create().show()
    }

    companion object{
        @JvmStatic
        fun newInstance() =
            RecipeDetailFragment()
    }
}