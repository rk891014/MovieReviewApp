package com.example.tweetapp.utils

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes

fun <T : Any> LazyGridScope.pagingItems(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

fun Int.hourMinutes(): String {
    return "${this.minutes.inWholeHours}h ${this % 60}m"
}

fun Int.genderInString(): String {
    return when(this){
        1 -> "Female"
        2 -> "Male"
        else -> ""
    }
}