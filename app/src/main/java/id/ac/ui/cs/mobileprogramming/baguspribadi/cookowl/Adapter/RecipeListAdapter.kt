package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.Recipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.R
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.RecipeDetailFragment
import kotlinx.android.synthetic.main.recipe_row_layout.view.*

class RecipeListAdapter:RecyclerView.Adapter<RecipeListAdapter.MyViewHolder>(){

    private var recipeList = emptyList<Recipe>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recipeList[position]
        holder.itemView.txt_row_name.text = currentItem.name

        holder.itemView.setOnClickListener() {
            (holder.itemView.context as MainActivity).passRecipe(RecipeDetailFragment.newInstance(), currentItem)
        }
    }

    fun setData(recipe: List<Recipe>){
        this.recipeList = recipe
        notifyDataSetChanged()
    }

    fun getRecipe(id: Int): Recipe?{
        for (recipe in this.recipeList) {
            if (recipe.id == id) {
                return recipe
            }
        }
        return null
    }
}