package com.niuwa.competition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.R
import com.niuwa.my.JoinUsActivity
import kotlinx.android.synthetic.main.activity_member.*

class MemberActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
        // Enables Always-on
        setAmbientEnabled()

        text_join.setOnClickListener {
            startActivity(Intent(this@MemberActivity, JoinUsActivity::class.java))
            finish()
        }
        text_back.setOnClickListener {
            finish()
        }
    }
}