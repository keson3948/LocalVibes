package com.example.localvibes.viewmodels

sealed class MainScreenEvent {
    data class SetIsResumed(val isResumed: Boolean) : MainScreenEvent()
    object ResumeReload : MainScreenEvent()
}