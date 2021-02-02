package com.niuwa.compositionDetail

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Toast
import com.niuwa.R
import com.niuwa.appreciation.AppreciationActivity
import com.niuwa.appreciation.CompositionBean
import kotlinx.android.synthetic.main.activity_composition_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.button3

class CompositionDetail : WearableActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composition_detail)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)

        // Enables Always-on
        setAmbientEnabled()
       val composition:CompositionBean= intent.getSerializableExtra("composition") as CompositionBean
        val position =intent.getIntExtra("position",0)
        index_detail.text=(position+1).toString()
        title_detail.text=composition.title
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.button1 -> {
//                    startActivity(Intent(this@CompositionDetail, AppreciationActivity::class.java))
                }
                R.id.button2 -> {

                }
                R.id.button3 -> {

                }
            }
        }
    }
}