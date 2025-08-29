package org.jeongmo.practice.global.api.payload.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.Slice


fun <T> Page<T>.toOffsetPaginationResponse(): OffsetPaginationResponse<T> {
    return OffsetPaginationResponse(
        items = this.content,
        page = this.number + 1,
        totalPage = this.totalPages,
        numbersOfElement = this.numberOfElements
    )
}

fun <T> Slice<T>.toCursorPaginationResponse(cursorFunction: (T) -> Any): CursorPaginationResponse<T> {
    return CursorPaginationResponse(
        items = this.content,
        numbersOfElement = this.numberOfElements,
        hasNext = this.hasNext(),
        cursor = cursorFunction.invoke(this.content.last())
    )
}