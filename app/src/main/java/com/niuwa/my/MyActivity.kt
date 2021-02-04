package com.niuwa.my

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.niuwa.Constant
import com.niuwa.R
import com.niuwa.compositionList.CompositionListActivity
import com.niuwa.competition.TipsActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : WearableActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)

        // Enables Always-on
        setAmbientEnabled()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.button1 -> {
                    val intent=Intent(this@MyActivity, JoinUsActivity::class.java)
                    startActivity(intent)
                }
                R.id.button2 -> {
                    var intent=Intent(this@MyActivity, CompositionListActivity::class.java)
                    intent.putExtra(Constant.LISTTYPE,Constant.MYWORK)
                    startActivity(intent)
                }
                R.id.button3 -> {
                    var intent=Intent(this@MyActivity, CompositionListActivity::class.java)
                    intent.putExtra(Constant.LISTTYPE,Constant.COLLECTION)
                    startActivity(intent)
                }
                R.id.button4 -> {
                    val intent=Intent(this@MyActivity, AboutUsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}