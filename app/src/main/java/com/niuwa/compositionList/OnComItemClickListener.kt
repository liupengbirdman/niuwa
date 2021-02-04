package com.niuwa.compositionList

import android.view.View

interface OnComItemClickListener {

    fun onClick(view: View?, position: Int,compositionBean: CompositionBean)
}