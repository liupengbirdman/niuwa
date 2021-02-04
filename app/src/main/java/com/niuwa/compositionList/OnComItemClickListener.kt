package com.niuwa.compositionList

import android.view.View

interface OnComItemClickListener {
    //列表点击
    fun onClick(view: View?, position: Int,compositionBean: CompositionBean)
    //列表收藏
    fun collect(status:Boolean)
}