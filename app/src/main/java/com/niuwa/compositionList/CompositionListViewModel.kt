package com.niuwa.compositionList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompositionListViewModel : ViewModel() {

    private val _text = MutableLiveData<ArrayList<CompositionBean>>()
    private val _error = MutableLiveData<String>()
    private val repository = CompositionListRepository()
    fun sendMessage(url: String, positionId: String, type: Int) {
        /*viewModelScope是一个绑定到当前viewModel的作用域  当ViewModel被清除时会自动取消该作用域，所以不用担心内存泄漏为问题*/
        viewModelScope.launch {
            getData(url, positionId, type)
        }
    }

    private suspend fun getData(url: String, positionId: String, type: Int) {
        try {
            val result = withContext(Dispatchers.IO) {
                repository.getDatas(url, positionId, type)
            }
            _text.value = result.data
        } catch (e: Exception) {
            /*请求异常的话在这里处理*/
            e.printStackTrace()

            _error.value = e.message
            Log.i("请求失败", "${e.message}")
        }
    }

    val error: LiveData<String> = _error
    val text: LiveData<ArrayList<CompositionBean>> = _text
}