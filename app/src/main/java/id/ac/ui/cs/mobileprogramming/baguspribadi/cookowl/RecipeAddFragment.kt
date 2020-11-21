package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.Recipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.RecipeViewModel
import kotlinx.android.synthetic.main.recipe_add_fragment.*
import kotlinx.android.synthetic.main.recipe_add_fragment.view.*
import kotlinx.android.synthetic.main.recipe_detail_fragment.view.*
import kotlinx.android.synthetic.main.recipe_detail_fragment.view.edt_ingredient
import kotlinx.android.synthetic.main.recipe_detail_fragment.view.edt_name
import kotlinx.android.synthetic.main.recipe_detail_fragment.view.edt_step
import kotlinx.android.synthetic.main.recipe_detail_fragment.view.edt_time
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.edt_name

class RecipeAddFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private var recipe_id: Int = 0
    private lateinit var recipe_name: String
    private var recipe_time: Int = 0
    private lateinit var recipe_ingredient: String
    private lateinit var recipe_step: String
    private var picture: Bitmap? = null
    private var picturePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_add_fragment, container, false)

        if(this.arguments != null) {
            recipe_id = this.requireArguments().getInt("recipe_id")
            recipe_time = this.requireArguments().getInt("recipe_time")
            recipe_name = this.requireArguments().getString("recipe_name").toString()
            recipe_ingredient = this.requireArguments().getString("recipe_ingredient").toString()
            recipe_step = this.requireArguments().getString("recipe_step").toString()

            view.edt_name.text = recipe_name
            view.edt_time.text = recipe_time.toString()
            view.edt_ingredient.text = recipe_ingredient
            view.edt_step.text = recipe_step
            picture = (context as MainActivity).pictureTaken
            if(picture != null){
                view.recipe_photo_taken.setImageBitmap(picture)
            }
        }

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        val btnPhoto = view.findViewById<Button>(R.id.take_photo)
        btnPhoto.setOnClickListener() {
            (context as MainActivity).takePhoto()
        }

        val btnRefreshPhoto = view.findViewById<Button>(R.id.refresh_photo)
        btnRefreshPhoto.setOnClickListener() {
            picture = (context as MainActivity).pictureTaken
            picturePath = (context as MainActivity).getPictureFile().absolutePath
            if(picture != null){
                view.recipe_photo_taken.setImageBitmap(picture)
            }
            Log.i("path", picture.toString())
        }

        val btnRegister = view.findViewById<Button>(R.id.submit)
        btnRegister.setOnClickListener() {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val name = edt_name.text.toString()
        val time = edt_time.text.toString()
        val ingredient = edt_ingredient.text.toString()
        val step = edt_step.text.toString()

        var valid = inputCheck(name, time, ingredient, step)
        if(valid){
            val recipe =
                Recipe(
                    recipe_id,
                    name,
                    picturePath,
                    time.toInt(),
                    ingredient,
                    step
                )
            if(recipe_id == 0) {
                recipeViewModel.addRecipe(recipe)
            }
            else{
                recipeViewModel.updateRecipe(recipe)
            }
        }
        (context as MainActivity).loadFragment(RecipeListFragment.newInstance(), false)
    }

    private fun inputCheck(name:String, time:String, ingredient: String, step:String): Boolean {
        return (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time) &&
                !TextUtils.isEmpty(ingredient) && !TextUtils.isEmpty(step))
    }

    companion object{
        @JvmStatic
        fun newInstance() =
            RecipeAddFragment()
    }
}