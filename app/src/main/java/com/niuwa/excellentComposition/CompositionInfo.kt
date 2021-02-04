package com.niuwa.excellentComposition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.niuwa.Constant
import com.niuwa.R
import com.niuwa.compositionList.CompositionBean
import kotlinx.android.synthetic.main.activity_composition_info.*
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.button3

class CompositionInfo : WearableActivity(), View.OnClickListener {
    private var composition: CompositionBean? =null

    private var type = Constant.APPRECIATION
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composition_info)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)

        // Enables Always-on
        setAmbientEnabled()

        type = intent.getIntExtra(Constant.LISTTYPE, Constant.APPRECIATION)
        composition= intent.getSerializableExtra("composition") as CompositionBean
        val position =intent.getIntExtra("position",0)
        index_detail.text=(position+1).toString()
        title_detail.text= composition!!.title
        if(type==Constant.COMPETITION)
            button3.visibility=View.GONE
    }

    override fun onClick(v: View?) {
        if (v != null) {
            val intent =Intent(this@CompositionInfo, CompositionDetail::class.java)
            when (v.id) {

                R.id.button1 -> {
                    intent.putExtra("type",1)
                }
                R.id.button2 -> {
                    intent.putExtra("type",2)
                }
                R.id.button3 -> {
                    intent.putExtra("type",3)
                }
            }
            intent.putExtra("id", composition?.id)
            startActivity(intent)
        }
    }
}