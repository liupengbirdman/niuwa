
package com.niuwa.excellentComposition
import com.niuwa.compositionList.CompositionBean
import java.io.Serializable
data class CompositionDetailBean(var Composition:CompositionBean,var CompositionResources:List<CompositionResourcesBean>):Serializable
