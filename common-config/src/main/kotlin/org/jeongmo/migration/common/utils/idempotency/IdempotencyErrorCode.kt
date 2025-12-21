package org.jeongmo.migration.common.utils.idempotency

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class IdempotencyErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    ALREADY_COMPLETE(HttpStatus.CONFLICT, "IDEMPOTENCY_409_1", "이미 처리된 요청입니다."),
    ALREADY_PROCESSING(HttpStatus.CONFLICT, "IDEMPOTENCY_409_2", "이미 해당 요청을 처리하고 있습니다."),
    NOT_FOUND_KEY_IN_HEADER(HttpStatus.BAD_REQUEST, "IDEMPOTENCY_400_1", "멱등성 키 헤더가 누락되었습니다."),
    ;

    override fun getReason(): ErrorReasonDTO {
        return DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
    }
}