package org.jeongmo.practice.global.error.exception

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class ItemOptionException(code: BaseErrorCode): ServerApplicationException(code) {
}