package org.jeongmo.migration.member.application.error.exception

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException

class MemberException(code: BaseErrorCode): ServerApplicationException(code) {
}