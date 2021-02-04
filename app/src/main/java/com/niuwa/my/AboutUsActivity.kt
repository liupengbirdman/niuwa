package com.niuwa.my

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        // Enables Always-on
        setAmbientEnabled()
        text_member.setOnClickListener {
            val intent=Intent(this@AboutUsActivity, JoinUsActivity::class.java)
            startActivity(intent)
        }


    }
}