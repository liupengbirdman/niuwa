package com.niuwa.api

import android.util.Log
import android.util.Log.VERBOSE
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.Logger
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {

    private var BASE_URL = "https://www.fastmock.site/mock/faef15fefa89e695577b169212d7136e/"
    public fun create(): RequestService {
        val builder = OkHttpClient.Builder()
            .addInterceptor(
                LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(VERBOSE)

              .logger(object : Logger {
                  override fun log(level: Int, tag: String?, msg: String?) {
                      Log.e("$tag - $level", "$msg")
                  }
              })
//              .enableMock(BuildConfig.MOCK, 1000L, object : BufferListener {
//                  override fun getJsonResponse(request: Request?): String? {
//                      val segment = request?.url?.pathSegments?.getOrNull(0)
//                      return mAssetManager.open(String.format("mock/%s.json", segment)).source().buffer().readUtf8()
//                  }
//              })
               .build())
            .readTimeout(100000, TimeUnit.SECONDS)
            .connectTimeout(100000, TimeUnit.SECONDS)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RequestService::class.java)
    }
}