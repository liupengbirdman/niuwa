package com.niuwa.competition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_current.*

class CurrentActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current)

        // Enables Always-on
        setAmbientEnabled()
        text_join.setOnClickListener {

            startActivity(Intent(this@CurrentActivity, NoticeActivity::class.java))
        }

    }
}