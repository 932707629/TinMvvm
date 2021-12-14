package me.soushin.tinmvvm.custom

import androidx.lifecycle.*

/**
 * 自定义ViewModelStore用于实现vm页面间共享
 * @author SouShin
 * created at 2021/12/14 9:45
 */
open class SharedViewModelStore :ViewModelStoreOwner {

//    private val bindTargets = mutableListOf<LifecycleOwner>()
    private var vmStore: ViewModelStore? = null

    override fun getViewModelStore(): ViewModelStore {
        if (vmStore == null){
            vmStore = ViewModelStore()
        }
        return vmStore!!
    }

}


/**
 * 自定义ViewModelFactory

class SharedViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

    }

} */

