package com.niuwa

import android.app.Application

class MainApplication : Application() {
    private var myUserId: String? = null
    override fun onCreate() {
        super.onCreate()
    }
   public fun setUserId(value: String) {
        this.myUserId = value
    }

   public fun getUserId(): String? {
        return myUserId
    }

}