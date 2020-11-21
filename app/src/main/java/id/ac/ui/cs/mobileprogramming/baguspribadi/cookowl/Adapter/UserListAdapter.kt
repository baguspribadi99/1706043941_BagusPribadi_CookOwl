package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.User.User
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.R
import kotlinx.android.synthetic.main.recipe_row_layout.view.*

class UserListAdapter:RecyclerView.Adapter<UserListAdapter.MyViewHolder>(){

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.txt_row_name.text = currentItem.name
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

    fun loginCheck(email: String, password: String):User?{
        for (user in this.userList) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return user
            }
        }
        return null
    }
}