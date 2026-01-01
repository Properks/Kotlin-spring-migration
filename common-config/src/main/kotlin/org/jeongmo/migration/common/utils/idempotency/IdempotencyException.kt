package org.jeongmo.migration.common.utils.idempotency

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class IdempotencyException(code: BaseErrorCode, th: Throwable?): ServerApplicationException(code, th) {
    constructor(code: BaseErrorCode): this(code, null)
}