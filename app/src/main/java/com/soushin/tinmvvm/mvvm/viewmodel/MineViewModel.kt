package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.navigation.Navigation
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.throttleClick
import com.soushin.tinmvvm.mvvm.repository.MineRepository
import com.soushin.tinmvvm.mvvm.ui.fragment.MineFragmentDirections
import me.soushin.tinmvvm.base.BaseViewModel

class MineViewModel(application: Application) :
    BaseViewModel<MineRepository>(application, MineRepository()) {

    fun onViewClick():View.OnClickListener{
        return throttleClick {
            val navController = Navigation.findNavController(it)
            if (navController.currentDestination?.id != R.id.mineFragment) return@throttleClick
            navController.navigate(MineFragmentDirections.actionMineFragmentToSplashActivity(),AppData.get().queryNavOptions())
        }
    }






}