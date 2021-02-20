package com.niuwa.my

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : WearableActivity() {
    var isMember = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        // Enables Always-on
        setAmbientEnabled()
        if (isMember) {
            text_id.text="OJOIHUHUI7979"
            text_date.text="2021-12-31"
            text_user.text="牛娃会员"
            text_member.text="续费会员"
            text_user.setBackgroundResource(R.drawable.text_solid_bg)
            text_user.setTextColor(Color.parseColor("#ffffff"));

        }else{
            text_id.text="-"
            text_date.text="-"
            text_user.text="游客"
            text_user.setTextColor(Color.parseColor("#223b61"));
            text_member.text="开通会员"
            text_user.setBackgroundResource(R.drawable.text_border_selector)

        }
        text_member.setOnClickListener {
            val intent = Intent(this@AboutUsActivity, JoinUsActivity::class.java)
            startActivity(intent)
        }

    }
}