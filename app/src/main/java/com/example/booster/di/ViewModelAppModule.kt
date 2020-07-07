package com.earlyBuddy.earlybuddy_android.di

import com.example.booster.ui.storeDetail.StoreDetailViewModel
import com.example.booster.ui.storeList.StoreListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelAppModule = module {
    viewModel { StoreDetailViewModel() }
    viewModel { StoreListViewModel() }
}