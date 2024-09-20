package com.yongdd.presentation.main.main.main

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
)  : BaseViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {
    override fun setInitialState() = MainContract.State()
    override fun handleEvents(event: MainContract.Event) {
        when(event) {
            is MainContract.Event.PageChanged -> {
                if(event.page == uiState.value.currentPage) return
                setState {
                    copy(currentPage = event.page)
                }
            }
        }
    }

}