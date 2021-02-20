package com.niuwa.excellentComposition

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_composition_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CompositionDetail : WearableActivity(), View.OnClickListener {

    private var detail: CompositionDetailBean? = null

    var mediaPlayer = MediaPlayer()

//    private var audioManager: AudioManager? = null

    private var timer: Timer? = null

    var maxVolume = 0
    var currentVolume: Int = 0

    public var isSeekBarChanging //互斥变量，防止进度条与定时器冲突。
            = false
    private var currentPosition //当前音乐播放的进度
            = 0

    var format: SimpleDateFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composition_detail)

        // Enables Always-on
        setAmbientEnabled()
        val id = intent.getStringExtra("id")
        val type = intent.getIntExtra("type", 0)
        val list = ArrayList<CompositionResourcesBean>()
        for (index in 0..2) {
            val bean = CompositionResourcesBean(
                index.toString(),
                "foekof/feeg/fef.mp3",
                index + 1,
                index.toString() + "在vue项目中，父组件通过prop给子组件传值时，如果prop值是从服务器端获取，则父组件可能会传给子组件一个默认值(服务端数据还未及时获取)，那么，我们就需要实时watch这个prop值，一旦prop值有更新，将立即通知子组件更新。"
            )
            list.add(bean)
        }
        val bean = CompositionDetailBean(id.toString(), "第一期 20210210", "有一种甜", "true", "马佩奇", list)

        index_detail.text = "2-"
        title_detail.text = bean.title
        var detail: CompositionResourcesBean? = null
        for (index in 0 until bean.CompositionResources.size) {
            if (bean.CompositionResources[index].resourceType == type) {
                detail = bean.CompositionResources[index]
            }
        }
        if (detail != null) {
            when (detail.resourceType) {
                1 -> {
                    text_resource.text = "作文原文-精彩佳句"
                }
                2 -> {
                    text_resource.text = "名师点评-精彩点评"
                }
                3 -> {
                    text_resource.text = "作者感想-感想重点"
                }
            }
            text_compContent.text = detail.CompContent
        }

//        audioManager =getSystemService (Service.AUDIO_SERVICE) as AudioManager
        format = SimpleDateFormat("mm:ss");
        play.setOnClickListener(this)
        seekBar!!.setOnSeekBarChangeListener(MySeekBar())
//        if (ContextCompat.checkSelfPermission(
//                this@CompositionDetail,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) !=
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this@CompositionDetail,
//                 String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//
//            Log.i("checkSelfPermission","11111")
//        } else {
//            Log.i("checkSelfPermission","22222")
//            initMediaPlayer();//初始化mediaplayer
//        }
//        initMediaPlayer();//初始化mediaplayer
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer.setDataSource("http://m701.music.126.net/20210218160316/f82a7ecb993479547593a32698c34cb7/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/4959745806/482a/1a84/ca27/02c0f32c8c1b78a97988cebbee2ede1b.mp3") //指定音频文件的路径
            mediaPlayer.prepare() //让mediaplayer进入准备状态
            mediaPlayer.isLooping = true
            mediaPlayer.setOnPreparedListener(OnPreparedListener {
                seekBar.max = mediaPlayer.duration
                music_length.text = format?.format(mediaPlayer.duration).toString() + ""
                music_cur.text = "00:00"
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                initMediaPlayer()
            } else {
                Toast.makeText(this@CompositionDetail, "denied access", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.play -> if (!mediaPlayer.isPlaying) {
                mediaPlayer.start() //开始播放
                mediaPlayer.seekTo(currentPosition)

                //监听播放时回调函数
                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    var updateUI =
                        Runnable { music_cur.text=(format!!.format(mediaPlayer.currentPosition) + "") }

                    override fun run() {
                        if (!isSeekBarChanging) {
                            seekBar!!.progress = mediaPlayer.currentPosition
                            currentPosition = mediaPlayer.currentPosition
                            runOnUiThread(updateUI)
                        }
                    }
                }, 0, 50)
            }else
            { mediaPlayer.pause() }//暂停播放
//            R.id.pause -> if (mediaPlayer.isPlaying) {
//                mediaPlayer.pause() //暂停播放
//            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isSeekBarChanging = true
        mediaPlayer.stop()
        mediaPlayer.release()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    /*进度条处理*/
    inner  class MySeekBar : OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar, progress: Int,
            fromUser: Boolean
        ) {
        }

        /*滚动时,应当暂停后台定时器*/
        override fun onStartTrackingTouch(seekBar: SeekBar) {
            isSeekBarChanging = true
        }

        /*滑动结束后，重新设置值*/
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            isSeekBarChanging = false
            mediaPlayer.seekTo(seekBar.progress)
        }
    }
}