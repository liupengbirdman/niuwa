package com.niuwa.appreciation

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niuwa.R


class CompositionListAdapter : RecyclerView.Adapter<CompositionListAdapter.ViewHolder> {
    var list:MutableList<CompositionBean>? = null
    private var mOnItemClickListener //声明接口
            : OnRecyItemClickListener? = null


    constructor(list: MutableList<CompositionBean>?,mOnItemClickListener:OnRecyItemClickListener) : super() {
        this.list = list
        this.mOnItemClickListener = mOnItemClickListener
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val index: TextView = itemView.findViewById(R.id.index)
        val title: TextView = itemView.findViewById(R.id.title)
        val isfree: TextView = itemView.findViewById(R.id.isfree)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.item_composition, null))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.index.text =( position+1).toString()+"- "
        holder.title.text = "《"+list!![position].title+"》"
        holder.isfree.text = if(list!![position].isFree=="true") "试听" else ""
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener{
                    mOnItemClickListener!!.onClick(holder.itemView, position,list!![position]);
                }
            }
    }
}