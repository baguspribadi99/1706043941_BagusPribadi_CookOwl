package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.Recipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.RemoteRecipe.RemoteRecipe
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.R
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.RecipeDetailFragment
import kotlinx.android.synthetic.main.recipe_row_layout.view.*

class RemoteRecipeListAdapter(
    val context: Context,
    val list : ArrayList<RemoteRecipe>
): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = LayoutInflater.from(context).inflate(R.layout.recipe_row_layout, parent, false)

        val name = view.findViewById<TextView>(R.id.txt_row_name)

        name.text = list[position].name

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}