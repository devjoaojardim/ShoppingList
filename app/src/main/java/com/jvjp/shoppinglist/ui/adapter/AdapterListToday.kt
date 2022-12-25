package com.jvjp.shoppinglist.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jvjp.shoppinglist.R
import com.jvjp.shoppinglist.helpe.RecyclerItemClickListener
import com.jvjp.shoppinglist.model.Shopping

class AdapterListToday(private  var  list: List<Shopping>,
                       private  var onClickListener: RecyclerItemClickListener,
                       private  var context: Context
):  RecyclerView.Adapter<AdapterListToday.ViewHolder>()
{
    companion object {

        const val DELETEANIMALS = 0
        const val LIKE = 100
        const val COMMENT = 200
        const val SHARE = 300
        const val OPTIONS = 400
        const val PROFILE = 500
        const val LOCATION = 600
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryTb: TextView = itemView.findViewById(R.id.categoryListAdapter)
        val nameTv: TextView = itemView.findViewById(R.id.nameAdapterList)
        val priceTv: TextView = itemView.findViewById(R.id.priceAdapterList)
        val update: ImageView = itemView.findViewById(R.id.deleterItem)

//        val descTv: TextView = itemView.findViewById(R.id.desc_tv)

//        val dateTv: TextView = itemView.findViewById(R.id.date_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_today, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            this.nameTv.text = item.nameProduct
            this.categoryTb.text = item.category
            this.priceTv.text = item.price
        }
        holder.update.setOnClickListener { onClickListener.onClickListenerItem(item) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}