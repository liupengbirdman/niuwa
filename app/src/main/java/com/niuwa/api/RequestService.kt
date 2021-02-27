package com.niuwa.api

import com.niuwa.compositionList.CompositionBean
import com.niuwa.excellentComposition.CompositionDetailBean
import com.niuwa.my.QRCodeBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestService {
    @GET("Api/ExcellentComposition/GetAll")
    fun getCompositionList():
            Call<ApiResponse<ArrayList<CompositionBean>>>
    @GET("Api/MyComposition/GetAll")
    fun getMyWorkList(@Query("Userid") Userid : String):
            Call<ApiResponse<ArrayList<CompositionBean>>>
    @GET("Api/MyFavorites/GetAll")
    fun getMyFavoritesList(@Query("Userid") Userid : String):
            Call<ApiResponse<ArrayList<CompositionBean>>>
    @GET("Api/MyFavorites/Get")
    fun getMyFavoritesComposition(@Query("id") id : String):
            Call<ApiResponse<CompositionDetailBean>>
    @GET("Api/ExcellentComposition/Get")
    fun getExcellentComposition(@Query("id") id : String):
            Call<ApiResponse<CompositionDetailBean>>
    @GET("Api/MyComposition/Get")
    fun getMyComposition(@Query("id") id : String):
            Call<ApiResponse<CompositionDetailBean>>
    @GET("Api/Shoping/Get")
    fun getQRCode(@Query("Userid") Userid: String?):
            Call<ApiResponse<QRCodeBean>>


}