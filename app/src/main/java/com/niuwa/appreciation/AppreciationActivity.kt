package com.niuwa.appreciation

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_main.*

class AppreciationActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appreciation)

        // Enables Always-on
        setAmbientEnabled()
    }
}