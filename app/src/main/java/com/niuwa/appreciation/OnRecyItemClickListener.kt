package com.niuwa.appreciation

import android.view.View

interface OnRecyItemClickListener {

    fun onClick(view: View?, position: Int,compositionBean:CompositionBean)
}