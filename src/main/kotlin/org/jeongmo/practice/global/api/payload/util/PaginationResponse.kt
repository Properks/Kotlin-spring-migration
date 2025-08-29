package org.jeongmo.practice.global.api.payload.util

data class OffsetPaginationResponse<T>(val items: List<T>, val page: Int, val totalPage: Int, val numbersOfElement: Int)
data class CursorPaginationResponse<T>(val items: List<T>, val cursor: Any, val numbersOfElement: Int, val hasNext: Boolean)