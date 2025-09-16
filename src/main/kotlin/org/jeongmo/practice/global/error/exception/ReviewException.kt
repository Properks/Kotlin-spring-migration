package org.jeongmo.practice.global.error.exception

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class ReviewException(code: BaseErrorCode): ServerApplicationException(code) {
}