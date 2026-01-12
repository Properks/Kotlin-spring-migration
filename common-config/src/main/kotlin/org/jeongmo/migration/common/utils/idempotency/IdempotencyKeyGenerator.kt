package org.jeongmo.migration.common.utils.idempotency

import org.springframework.http.HttpMethod

fun interface IdempotencyKeyGenerator {
    /**
     * 멱등성 키를 생성하는 메서드
     * @param prefix 멱등성 키의 prefix, 주로 요청을 보낸 위치를 구별하기 위한 요소. 예를 들면 로그인이면 prefix를 `login`으로 설정 가능
     * @param method 요청에 사용되는 HttpMethod
     * @param endpoint 요청을 보내는 uri의 endpoint
     * @param additional 요청을 보낼 때 사용되는 정보들
     * @return parameter들로 생성된 멱등성 키
     */
    fun generateKey(prefix: String, method: HttpMethod, endpoint: String, vararg additional: Any): String
}