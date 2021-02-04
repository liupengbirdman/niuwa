package com.niuwa.competition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        // Enables Always-on
        setAmbientEnabled()
        text_upload.setOnClickListener {

            startActivity(Intent(this@NoticeActivity, UploadActivity::class.java))
        }
        
    }
}