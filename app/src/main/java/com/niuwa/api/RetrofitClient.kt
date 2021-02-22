package com.niuwa.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

   var BASE_URL = "https://www.fastmock.site/mock/7275b688d79021d43f2befe9910e0d78/test/"
    val reqApi: RequestService by lazy {
        val builder = OkHttpClient.Builder()
//            .addInterceptor(LoggingInterceptor.Builder()
//                .setLevel(Level.BASIC)
//                .log(VERBOSE)

//              .logger(object : Logger {
//                  override fun log(level: Int, tag: String?, msg: String?) {
//                      Log.e("$tag - $level", "$msg")
//                  }
//              })
//              .enableMock(BuildConfig.MOCK, 1000L, object : BufferListener {
//                  override fun getJsonResponse(request: Request?): String? {
//                      val segment = request?.url?.pathSegments?.getOrNull(0)
//                      return mAssetManager.open(String.format("mock/%s.json", segment)).source().buffer().readUtf8()
//                  }
//              })
//                .build())
            .readTimeout(100000, TimeUnit.SECONDS)
            .connectTimeout(100000, TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return@lazy retrofit.create(RequestService::class.java)
    }
}