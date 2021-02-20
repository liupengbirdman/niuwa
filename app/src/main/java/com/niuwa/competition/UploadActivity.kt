package com.niuwa.competition

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.TextView
import com.niuwa.R
import com.niuwa.my.JoinUsActivity
import kotlinx.android.synthetic.main.activity_composition_detail.*
import kotlinx.android.synthetic.main.activity_dialogs.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recording.*
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.activity_upload.text_time
import java.text.SimpleDateFormat
import java.util.*


class UploadActivity : WearableActivity() {

    var mediaPlayer = MediaPlayer()
    var format: SimpleDateFormat? = null
    var isMember = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        // Enables Always-on
        setAmbientEnabled()

        linear_start.visibility = View.VISIBLE
        linear_upload.visibility = View.GONE
//        linear_start.visibility = View.GONE
//        linear_upload.visibility = View.VISIBLE
        format = SimpleDateFormat("mm:ss");
        text_upload.setOnClickListener {
            startActivityForResult(Intent(this@UploadActivity, RecordingActivity::class.java), 1)
        }
        text_re.setOnClickListener {
            linear_start.visibility = View.VISIBLE
            linear_upload.visibility = View.GONE

        }
        text_commit.setOnClickListener {
            if (isMember) {
                val builder = AlertDialog.Builder(this@UploadActivity)
                val view = View
                    .inflate(this@UploadActivity, R.layout.activity_dialogs, null)
                builder.setView(view)
                builder.setCancelable(true)
                val detail = view.findViewById<View>(R.id.text_detail) as TextView //设置标题
                val left = view.findViewById<View>(R.id.text_left) as TextView //取消按钮
                if (edt_name.length() == 0) {

                    detail.text = "请输入作者姓名哦！"
                    left.text = "返回"
                    val dialog = builder.create()
                    left.setOnClickListener {
                        dialog.cancel()
                    }
                    dialog.show()

                } else if (mediaPlayer.duration < 60) {
                    detail.text = "录音时间不足一分钟。无法提交哦！请重新录制"
                    left.text = "返回"
                    val dialog = builder.create()
                    left.setOnClickListener {
                        dialog.cancel()
                    }
                    dialog.show()
                } else {
                    linear_loading.visibility=View.VISIBLE
                    var count =0
                   val timer = Timer()
                    timer.schedule(object : TimerTask() {
                        var updateUI =
                            Runnable {
                                count++
                                if(count==3){

                                    linear_loading.visibility=View.GONE
                                    detail.text = "提交成功！请下周过后在“命题作文周赛”-“查看往期”里查看本期优秀作文"
                                    left.text = "确定"
                                    val dialog = builder.create()
                                    left.setOnClickListener {
                                        dialog.cancel()
                                    }
                                    dialog.show()
                                    timer.cancel()
                                }
                            }

                        override fun run() {
                            runOnUiThread(updateUI)
                        }
                    }, 1000, 1000)

                }


            } else {
                startActivity(Intent(this@UploadActivity, MemberActivity::class.java))
            }

        }
        img_play.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start() //开始播放
                img_play.setImageResource(R.drawable.baseline_pause_red_24dp)
            } else {
                mediaPlayer.pause()
                img_play.setImageResource(R.drawable.baseline_play_arrow_red_a700_24dp)
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