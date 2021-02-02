package com.niuwa.splashActivity

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.MainActivity
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_main.*


class SplashActivity : WearableActivity() {
    private val sleepTime = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Enables Always-on
        setAmbientEnabled()
    }

    override fun onStart() {
        super.onStart()
        Thread {
            val start = System.currentTimeMillis()
            val costTime = System.currentTimeMillis() - start
            //等待sleeptime时长
            if (sleepTime - costTime > 0) {
                try {
                    Thread.sleep(sleepTime - costTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            //进入主页面
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }.start()
    }
}