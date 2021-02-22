package com.niuwa.api

import com.kuka.agvpda.bean.ApiResponse
import com.niuwa.compositionList.CompositionBean
import com.niuwa.excellentComposition.CompositionDetailBean
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestService {
    @GET("Api/ExcellentComposition/GetAll")
    fun getCompositionList():
            Call<ApiResponse<ArrayList<CompositionBean>>>
    @GET("Api/MyComposition/GetAll")
    fun getMyWorkList(@Query("Userid") Userid : String,):
            Call<ApiResponse<ArrayList<CompositionBean>>>
    @GET("Api/MyFavorites/GetAll")
    fun getMyFavoritesList(@Query("Userid") Userid : String,):
            Call<ApiResponse<ArrayList<CompositionBean>>>
    @GET("Api/ExcellentComposition/Get")
    fun getExcellentComposition(@Query("id") id : String,):
            Call<ApiResponse<CompositionDetailBean>>
//    fun getDatasAsync(@Query("positionId") positionId : String, @Query("type") type: Int): Deferred<ApiResponse<ArrayList<CompositionBean>>>
//    fun getDatasAsync(): Deferred<Message>

}