package com.niuwa.excellentComposition

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.niuwa.R
import kotlinx.android.synthetic.main.activity_composition_detail.*

class CompositionDetail : WearableActivity() {

    private var detail: CompositionDetailBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composition_detail)

        // Enables Always-on
        setAmbientEnabled()
        val id = intent.getIntExtra("id", 0)
        val type = intent.getIntExtra("type", 0)
        val list = ArrayList<CompositionResourcesBean>()
        for (index in 0..2) {
            val bean = CompositionResourcesBean(
                index.toString(),
                "foekof/feeg/fef.mp3",
                index + 1,
                index.toString() +"在vue项目中，父组件通过prop给子组件传值时，如果prop值是从服务器端获取，则父组件可能会传给子组件一个默认值(服务端数据还未及时获取)，那么，我们就需要实时watch这个prop值，一旦prop值有更新，将立即通知子组件更新。"
            )
            list.add(bean)
        }
        val bean = CompositionDetailBean(id.toString(), "第一期 20210210", "有一种甜", "true", "马佩奇", list)

        index_detail.text = "2-"
        title_detail.text = bean.title
        var detail: CompositionResourcesBean? =null
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
    }
}