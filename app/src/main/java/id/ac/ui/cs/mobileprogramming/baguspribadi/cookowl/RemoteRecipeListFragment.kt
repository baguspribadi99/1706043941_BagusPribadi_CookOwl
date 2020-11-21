package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter.RemoteRecipeListAdapter
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.RemoteRecipe.RemoteRecipe
import kotlinx.android.synthetic.main.recipe_list_fragment.view.*
import kotlinx.android.synthetic.main.remote_recipe_list_fragment.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

class RemoteRecipeListFragment : Fragment() {
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.remote_recipe_list_fragment, container, false)

        view.greet.text = "Ideas"

        val url = "https://raw.githubusercontent.com/raywenderlich/recipes/master/Recipes.json"

        AsyncTaskHandler().execute(url)

        return view
    }

    inner class AsyncTaskHandler:AsyncTask<String,String, String>(){
        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog = ProgressDialog(context as MainActivity)
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg url: String?): String {
            val res:String
            val connection= URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                res = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }
            finally {
                    connection.disconnect()
            }
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(progressDialog.isShowing()){
                progressDialog.dismiss()
            }
            jsonResult(result)
        }

        private fun jsonResult(jsonString:String?){
            val jsonArray = JSONArray(jsonString)

            val list = ArrayList<RemoteRecipe>()
            var i = 0
            while(i < jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(RemoteRecipe(
                    jsonObject.getString("name")
                ))
                i++
            }
            val adapter = RemoteRecipeListAdapter(context as MainActivity, list)
            list_all_remote_recipe.adapter = adapter

        }
    }

    companion object{
        @JvmStatic
        fun newInstance() = RemoteRecipeListFragment()
    }
}