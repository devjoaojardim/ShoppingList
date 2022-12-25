package com.jvjp.shoppinglist.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.helpe.RecyclerItemClickListener
import com.jvjp.shoppinglist.model.Date
import com.jvjp.shoppinglist.model.Shopping

class AdapterListAll(private  var  list: List<String>,
                     private  var onClickListener: RecyclerItemClickListener,
                     private  var context: Context
):  RecyclerView.Adapter<AdapterListAll.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        val categoryTb: TextView = itemView.findViewById(R.id.categoryListAdapter)
        val dateList: TextView = itemView.findViewById(R.id.dateList)


//        val descTv: TextView = itemView.findViewById(R.id.desc_tv)

//        val dateTv: TextView = itemView.findViewById(R.id.date_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_lists, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.dateList.text = item
    }

    override fun getItemCount(): Int {
        return list.size
    }
}