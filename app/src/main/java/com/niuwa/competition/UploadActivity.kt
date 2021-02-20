package com.niuwa.competition

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.TextView
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_composition_detail.*
import kotlinx.android.synthetic.main.activity_dialogs.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.text.SimpleDateFormat


class UploadActivity : WearableActivity() {

    var mediaPlayer = MediaPlayer()
    var format: SimpleDateFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        // Enables Always-on
        setAmbientEnabled()

        linear_start.visibility = View.VISIBLE
        linear_upload.visibility = View.GONE
        format = SimpleDateFormat("mm:ss");
        text_upload.setOnClickListener {
            startActivityForResult(Intent(this@UploadActivity, RecordingActivity::class.java), 1)
        }
        text_re.setOnClickListener {
            linear_start.visibility = View.VISIBLE
            linear_upload.visibility = View.GONE

        }
        text_commit.setOnClickListener {
            val builder = AlertDialog.Builder(this@UploadActivity)
            val view = View
                .inflate(this@UploadActivity, R.layout.activity_dialogs, null)
            builder.setView(view)
            builder.setCancelable(true)
          val detail=  view.findViewById<View>(R.id.text_detail) as TextView //设置标题
            val left=  view.findViewById<View>(R.id.text_left) as TextView //取消按钮
            if (edt_name.length() == 0) {

                detail.text="请输入作者姓名哦！"
                left.text="返回"

           }else if(mediaPlayer.duration<60){
                detail.text="录音时间不足一分钟。无法提交哦！请重新录制"
                left.text="返回"
           }else {

                detail.text="提交成功！请下周过后在“命题作文周赛”-“查看往期”里查看本期优秀作文"
                left.text="确定"
           }

            val dialog = builder.create()
            left.setOnClickListener {
                dialog.cancel()
            }
            dialog.show()

        }
        img_play.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start() //开始播放
            } else {
                mediaPlayer.pause()
            }//暂停播放
        }
    }

    private fun initMediaPlayer(url: String) {
        try {
            mediaPlayer.setDataSource(url) //指定音频文件的路径
            mediaPlayer.prepare() //让mediaplayer进入准备状态
            mediaPlayer.isLooping = false
            mediaPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {
                text_time.text = format?.format(mediaPlayer.duration).toString() + ""
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            linear_start.visibility = View.GONE
            linear_upload.visibility = View.VISIBLE
            val url = Intent().getStringExtra("absoluteFile")
            if (url != null) {
                initMediaPlayer(url)
            }
        }

    }
}