package org.jeongmo.migration.common.utils.idempotency

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class IdempotencyException(code: BaseErrorCode): ServerApplicationException(code) {
}