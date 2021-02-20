package com.niuwa.compositionList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niuwa.Constant
import com.niuwa.R


class CompositionListAdapter : RecyclerView.Adapter<CompositionListAdapter.ViewHolder> {
    var list:MutableList<CompositionBean>? = null
    private var type = Constant.APPRECIATION
    private var mOnItemClickListener //声明接口
            : OnComItemClickListener? = null


    constructor(list: MutableList<CompositionBean>?,mOnItemClickListener: OnComItemClickListener,type:Int) : super() {
        this.list = list
        this.mOnItemClickListener = mOnItemClickListener
        this.type =type
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val isfree: TextView = itemView.findViewById(R.id.isfree)
        var author:TextView = itemView.findViewById(R.id.author)
        var past_title:TextView = itemView.findViewById(R.id.past_title)
        var collect:ImageView = itemView.findViewById(R.id.collect)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_composition, parent, false))
//        return ViewHolder(View.inflate(parent.context, R.layout.item_composition, parent))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(type == Constant.APPRECIATION){
            holder.title.visibility=View.VISIBLE
            holder.isfree.visibility=View.VISIBLE
            holder.author.visibility=View.GONE
            holder.past_title.visibility=View.GONE
            holder.collect.visibility=View.GONE
            holder.title.text =( position+1).toString()+"- "+ "《"+list!![position].title+"》"
            holder.isfree.text = if(list!![position].isFree=="true") "试听" else ""

        }else if(type == Constant.COMPETITION){
            holder.title.visibility=View.VISIBLE
            holder.isfree.visibility=View.VISIBLE
            holder.author.visibility=View.VISIBLE
            holder.past_title.visibility=View.VISIBLE
            holder.collect.visibility=View.GONE
            holder.title.text = "优秀作品:《"+list!![position].title+"》"
            holder.isfree.text = if(list!![position].isFree=="true") "试听" else ""
            holder.author.text = "作者:"+list!![position].author
            holder.past_title.text = list!![position].CompeitionTitle

        }else  if(type == Constant.MYWORK){
            holder.title.visibility=View.VISIBLE
            holder.isfree.visibility=View.VISIBLE
            holder.author.visibility=View.GONE
            holder.past_title.visibility=View.GONE
            holder.collect.visibility=View.GONE
            holder.title.text =( position+1).toString()+"- "+ "《"+list!![position].title+"》"
            holder.isfree.text = if(list!![position].perfect=="true") "优秀作文" else ""

        }else  if(type == Constant.COLLECTION){
            holder.title.visibility=View.VISIBLE
            holder.isfree.visibility=View.GONE
            holder.author.visibility=View.GONE
            holder.past_title.visibility=View.GONE
            holder.collect.visibility=View.VISIBLE
            holder.title.text =( position+1).toString()+"- "+ "《"+list!![position].title+"》"
            holder.collect.setImageResource(if(list!![position].perfect=="true") R.mipmap.ic_launcher else R.mipmap.ic_launcher)
            holder.collect.setOnClickListener {
                mOnItemClickListener?.collect(list!![position].perfect.toBoolean())
            }
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener{
                mOnItemClickListener!!.onClick(holder.itemView, position,list!![position]);
            }
        }
    }
}