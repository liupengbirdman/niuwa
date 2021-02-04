package com.niuwa.competition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.niuwa.R
import com.niuwa.competition.TipsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        // Enables Always-on
        setAmbientEnabled()


        text_upload.setOnClickListener{

        }

    }
}