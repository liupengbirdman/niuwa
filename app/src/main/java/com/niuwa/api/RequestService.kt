package com.niuwa.api

import com.kuka.agvpda.bean.ApiResponse
import com.niuwa.compositionList.CompositionBean
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestService {
    @GET("MasterControl/dataExchange")
    fun getDatasAsync(@Query("positionId") positionId : String, @Query("type") type: Int): Deferred<ApiResponse<ArrayList<CompositionBean>>>
//    fun getDatasAsync(): Deferred<Message>
}