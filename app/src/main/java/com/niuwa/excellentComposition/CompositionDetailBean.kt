package com.niuwa.excellentComposition
import java.io.Serializable
data class CompositionDetailBean(var id: String, var CompeitionTitle:String, var title:String,
                                 var isFree:String,var author:String,var CompositionResources:ArrayList<CompositionResourcesBean>):Serializable
