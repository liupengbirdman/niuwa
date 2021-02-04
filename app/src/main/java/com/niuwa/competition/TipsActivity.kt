package com.niuwa.competition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.Constant
import com.niuwa.R
import com.niuwa.compositionList.CompositionListActivity
import kotlinx.android.synthetic.main.activity_tips.*

class TipsActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)

        // Enables Always-on
        setAmbientEnabled()
        text_join.setOnClickListener {

            startActivity(Intent(this@TipsActivity, CurrentActivity::class.java))
        }
        text_see.setOnClickListener {
            var intent=Intent(this@TipsActivity, CompositionListActivity::class.java)
            intent.putExtra(Constant.LISTTYPE, Constant.COMPETITION)
            startActivity(intent)
        }
    }
}