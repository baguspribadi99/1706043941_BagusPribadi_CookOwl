package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.Transaction
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.R
import kotlinx.android.synthetic.main.history_row_layout.view.*
import kotlinx.android.synthetic.main.recipe_row_layout.view.*

class TransactionListAdapter:RecyclerView.Adapter<TransactionListAdapter.MyViewHolder>(){

    private var transactionList = emptyList<Transaction>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = transactionList[position]
        holder.itemView.txt_row_time.text = currentItem.time
        holder.itemView.txt_row_chef_name.text = currentItem.chef_name
        holder.itemView.txt_row_meal_name.text = currentItem.meal_name

        if(currentItem.status) {
            holder.itemView.txt_row_status.text = ":)"
        }
        else {
            holder.itemView.txt_row_status.text = ":("
        }
    }

    fun setData(transaction: List<Transaction>){
        this.transactionList = transaction
        notifyDataSetChanged()
    }
}