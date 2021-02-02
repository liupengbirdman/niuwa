package com.niuwa.appreciation

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Toast
import androidx.wear.widget.WearableLinearLayoutManager
import com.niuwa.R
import com.niuwa.compositionDetail.CompositionDetail
import kotlinx.android.synthetic.main.activity_appreciation.*
import kotlinx.android.synthetic.main.activity_main.*

class AppreciationActivity : WearableActivity(),OnRecyItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appreciation)

        // Enables Always-on
        setAmbientEnabled()

        //创建伪数据
        val list = mutableListOf<CompositionBean>()
        for (index in 0..20) {
          val bean = CompositionBean(index.toString(),"第${index}期","作文_${index}",if(index==0) "true" else "false")
            list.add(bean)
        }
        //如果想要自适应手表表盘请使用WearableLinearLayoutManager，如果不需要适配表盘可以使用LinearLayoutManager
        recycler_view.layoutManager = WearableLinearLayoutManager(this)
        //第一个列表项和最后一个列表项在屏幕上垂直居中对齐
        recycler_view.isEdgeItemsCenteringEnabled = false
        //是否可以使用圆形滚动手势
        recycler_view.isCircularScrollingGestureEnabled = true
        //靠近屏幕边缘的虚拟“屏幕边框”（在此区域内能够识别出手势）的宽度
        recycler_view.bezelFraction = 0.5f
        //用户的手指必须旋转多少度才能滚过一个屏幕高度
        recycler_view.scrollDegreesPerScreen = 90f
        //设置List适配器
        recycler_view.adapter = CompositionListAdapter(list,this)
    }

    override fun onClick(view: View?, position: Int,compositionBean:CompositionBean) {
        var intent =Intent(this@AppreciationActivity, CompositionDetail::class.java)
        intent.putExtra("composition",compositionBean)
        intent.putExtra("position",position)
        startActivity(intent)
    }
}