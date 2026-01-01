package org.jeongmo.migration.auth.application.error.exception

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class AuthException(code: BaseErrorCode, th: Throwable?): ServerApplicationException(code, th) {
    constructor(code: BaseErrorCode): this(code, null)
}