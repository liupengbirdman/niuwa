package com.niuwa.compositionList

import com.kuka.agvpda.bean.ApiResponse
import com.niuwa.api.RetrofitClient

class CompositionListRepository {
    suspend fun getDatas(url:String,positionId:String,type:Int ): ApiResponse<ArrayList<CompositionBean>> {
        RetrofitClient.BASE_URL=url
        return RetrofitClient.reqApi.getDatasAsync(positionId,type).await()
//        return RetrofitClient.reqApi.getDatasAsync().await()
    }
}
