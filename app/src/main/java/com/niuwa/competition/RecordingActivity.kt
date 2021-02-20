package com.niuwa.competition

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.niuwa.R
import com.zlw.main.recorderlib.RecordManager
import com.zlw.main.recorderlib.recorder.RecordConfig
import com.zlw.main.recorderlib.recorder.RecordHelper
import com.zlw.main.recorderlib.recorder.RecordHelper.RecordState
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener
import kotlinx.android.synthetic.main.activity_recording.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RecordingActivity : WearableActivity() {
    private var count = 3
    private var time = 0
    private var timer: Timer? = null
    var format: SimpleDateFormat? = null
    var mediaPlayer = MediaPlayer()
    var firstRecord = true

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)
        // Enables Always-on
        setAmbientEnabled()
        checkAndRequestPermission()
        format = SimpleDateFormat("mm:ss");
        text_start.setOnClickListener {
//            initMediaPlayer()
            if (count == 3) {
                text_dong.visibility = View.INVISIBLE
                initCount()
            } else if (count == 0) {
                firstRecord = false
                count = 3
                text_start.text = "继续"
                text_title.text = "暂停"
                text_dong.visibility = View.VISIBLE
                pauseRecording()
            }
        }
        text_dong.setOnClickListener {
            stopRecording()
        }
    }

    private fun checkAndRequestPermission() {
        var lackedPermission= ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                this@RecordingActivity,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            lackedPermission.add(Manifest.permission.RECORD_AUDIO)
        }
        if (ContextCompat.checkSelfPermission(
                this@RecordingActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
                lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        Log.i("lackedPermission===", lackedPermission.size.toString())
        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.isEmpty()) {

            //操作你授权后的逻辑
            initRecordManager()

        } else {
            ActivityCompat.requestPermissions(
                this@RecordingActivity,
                lackedPermission.toTypedArray(),
                1
            )
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initRecordManager()
                } else {
                    Toast.makeText(this, "申请权限失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecordManager() {
        /**
         * 参数1： Application 实例
         * 参数2： 是否打印日志
         */
        RecordManager.getInstance().init(application, false)
        RecordManager.getInstance().changeFormat(RecordConfig.RecordFormat.MP3)
        val recordDir = String.format(
            Locale.getDefault(), "%s/Record/com.niuwa/",
            Environment.getExternalStorageDirectory().absolutePath
        )
        RecordManager.getInstance().changeRecordDir(recordDir)
        RecordManager.getInstance().setRecordStateListener(object : RecordStateListener {
            override fun onStateChange(state: RecordHelper.RecordState) {
                when (state) {
                    RecordState.PAUSE -> {
                        text_start.text = "继续"
                        text_title.text = "暂停"
                    }
//                    RecordState.IDLE -> tvState.setText("空闲中")
                    RecordState.RECORDING -> {
                        text_start.text = "暂停"
                        text_title.text = "录音中"
                    }
                    RecordState.STOP -> {
                    }
                    RecordState.FINISH -> {
//                        tvState.setText("录音结束")
                    }
                    else -> {
                    }
                }
            }

            override fun onError(error: String?) {
            }
        })
        RecordManager.getInstance().setRecordResultListener {
            Toast.makeText(
                this@RecordingActivity,
                "录音文件： " + it.absoluteFile,
                Toast.LENGTH_SHORT
            ).show()
            // 可以用putExtra()的方法，也可以用setXXX()的方法
            // 可以用putExtra()的方法，也可以用setXXX()的方法
            val intent = Intent()
            intent.putExtra("absoluteFile", it.absoluteFile)
            // 设置返回码和返回携带的数据
            // 设置返回码和返回携带的数据
            setResult(RESULT_OK, intent)
            // RESULT_OK就是一个默认值，=-1，它说OK就OK吧
            // RESULT_OK就是一个默认值，=-1，它说OK就OK吧
            finish()
        }
        RecordManager.getInstance().setRecordFftDataListener(RecordFftDataListener { data ->
           audioView.setWaveData(data)
        })
    }

    private fun startRecording() {
        TimingX.builder().add(text_time).start()
        RecordManager.getInstance().start()

    }

    private fun stopRecording() {
        TimingX.builder().stop()
        RecordManager.getInstance().stop()

    }

    private fun resumeRecording() {
        TimingX.builder().add(text_time).start()
        RecordManager.getInstance().resume()

    }

    private fun pauseRecording() {
        TimingX.builder().stop()
        RecordManager.getInstance().pause()

    }

    private fun initCount() {
        //监听播放时回调函数
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            var updateUI =
                Runnable {
                    if (count == 0) {
                        text_start.text = "暂停"
                        text_title.text = "录音中"
                        timer!!.cancel()
                        if (firstRecord)
                            startRecording()
                        else
                            resumeRecording()
                    } else {
                        text_start.text = count.toString()
                        count--
                    }
                }

            override fun run() {
                runOnUiThread(updateUI)
            }
        }, 1000, 1000)
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer.setDataSource("http://m701.music.126.net/20210218160316/f82a7ecb993479547593a32698c34cb7/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/4959745806/482a/1a84/ca27/02c0f32c8c1b78a97988cebbee2ede1b.mp3") //指定音频文件的路径
            mediaPlayer.prepare() //让mediaplayer进入准备状态
            mediaPlayer.isLooping = false
            mediaPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start() //开始播放
                }
            })
            mediaPlayer.setOnCompletionListener {
                initCount()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TimingX.builder().destroy();
        mediaPlayer.stop()
        mediaPlayer.release()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

}