package com.niuwa.excellentComposition

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.kuka.agvpda.bean.ApiResponse
import com.niuwa.Constant
import com.niuwa.R
import com.niuwa.api.RetrofitClient
import com.niuwa.compositionList.CompositionBean
import kotlinx.android.synthetic.main.activity_composition_info.*
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.button3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompositionInfo : WearableActivity(), View.OnClickListener {
    private var composition: CompositionBean? = null
    var audioPath1 = ""
    var audioPath2 = ""
    var audioPath3 = ""
    var CompContent1 = ""
    var CompContent2 = ""
    var CompContent3 = ""

    private var type = Constant.APPRECIATION
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composition_info)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)

        // Enables Always-on
        setAmbientEnabled()

        type = intent.getIntExtra(Constant.LISTTYPE, Constant.APPRECIATION)
        composition = intent.getSerializableExtra("composition") as CompositionBean
        val position = intent.getIntExtra("position", 0)
        index_detail.text = (position + 1).toString()
        title_detail.text = composition!!.title
        if (type == Constant.COMPETITION)
            button3.visibility = View.GONE
        val requestService = RetrofitClient().create()
        var call: Call<ApiResponse<CompositionDetailBean>>? = null
        when (type) {
            Constant.APPRECIATION -> call = requestService.getExcellentComposition(composition!!.id)
            Constant.COMPETITION -> call = requestService.getExcellentComposition(composition!!.id)
            Constant.MYWORK -> call = requestService.getExcellentComposition(composition!!.id)
            Constant.COLLECTION -> call = requestService.getExcellentComposition(composition!!.id)
        }
        call?.enqueue(object : Callback<ApiResponse<CompositionDetailBean>> {
            override fun onFailure(
                call: Call<ApiResponse<CompositionDetailBean>>,
                t: Throwable
            ) {

            }

            override fun onResponse(
                call: Call<ApiResponse<CompositionDetailBean>>,
                response: Response<ApiResponse<CompositionDetailBean>>
            ) {
                if (response.body() != null && response.body()!!.data.CompositionResources.size > 0) {
                    for (data in response.body()!!.data.CompositionResources) {
                        if (data.resourceType == 1) {//原文
                            audioPath1 = data.audioPath
                            CompContent1=data.CompContent
                            button1.visibility=View.VISIBLE
                        }
                        if (data.resourceType == 2) {//点评
                            audioPath2 = data.audioPath
                            CompContent2=data.CompContent
                            button2.visibility=View.VISIBLE

                        }
                        if (data.resourceType == 3) {//自评
                            audioPath3 = data.audioPath
                            CompContent3=data.CompContent
                            button3.visibility=View.VISIBLE

                        }
                    }
                }
            }

        })

    }

    override fun onClick(v: View?) {
        if (v != null) {
            val intent = Intent(this@CompositionInfo, CompositionDetail::class.java)
            when (v.id) {

                R.id.button1 -> {
                    intent.putExtra("type", 1)
                    intent.putExtra("audioPath", audioPath1)
                    intent.putExtra("CompContent", CompContent1)
                }
                R.id.button2 -> {
                    intent.putExtra("type", 2)
                    intent.putExtra("audioPath", audioPath2)
                    intent.putExtra("CompContent", CompContent2)
                }
                R.id.button3 -> {
                    intent.putExtra("type", 3)
                    intent.putExtra("audioPath", audioPath3)
                    intent.putExtra("CompContent", CompContent3)
                }
            }
            intent.putExtra("title", composition!!.title)
            startActivity(intent)
        }
    }
}