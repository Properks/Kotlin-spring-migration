package org.jeongmo.migration.bought.item.application.error.exception

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class BoughtItemException(code: BaseErrorCode, th: Throwable?): ServerApplicationException(code, th) {
    constructor(code: BaseErrorCode) : this(code, null)
}