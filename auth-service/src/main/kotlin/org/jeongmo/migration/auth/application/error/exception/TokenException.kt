package org.jeongmo.migration.auth.application.error.exception

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class TokenException(code: BaseErrorCode): ServerApplicationException(code) {
}