package com.niuwa

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.niuwa.compositionList.CompositionListActivity
import com.niuwa.competition.TipsActivity
import com.niuwa.my.MyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WearableActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)

        // Enables Always-on
        setAmbientEnabled()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.button1 -> {
                    var intent=Intent(this@MainActivity, CompositionListActivity::class.java)
                    intent.putExtra(Constant.LISTTYPE,Constant.APPRECIATION)
                    startActivity(intent)
                }
                R.id.button2 -> {
                    startActivity(Intent(this@MainActivity, TipsActivity::class.java))

                }
                R.id.button3 -> {
                    startActivity(Intent(this@MainActivity, MyActivity::class.java))
                }
            }
        }
    }
}