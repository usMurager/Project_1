package com.example.project1.additonal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchSafe(
    onError: (Throwable) -> Unit = {},
    onSuccess: suspend () -> Unit
) {
    launch {
        try {
            onSuccess()
        } catch (e: Throwable) {
            onError(e)
        }
    }
}