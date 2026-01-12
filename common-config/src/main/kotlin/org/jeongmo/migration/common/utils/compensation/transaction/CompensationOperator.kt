package org.jeongmo.migration.common.utils.compensation.transaction

import java.lang.Exception

data class CompensationOperator<T>(
    /**
     * 로깅 등에 사용되는 보상 트랜잭션이 실행되는 위치, 시점을 구분하기 위한 보상 트랜잭션의 제목
     */
    val title: String,

    /**
     * 보상 트랜잭션을 처리하기 위해 발생한 시점의 예외
     */
    val exception: Exception,

    /**
     * 여러 보상 트랜잭션을 담아 놓은 변수 이 변수를 통해 구현체에서 실행이 가능하고 변수에 람다 형식으로 넣을 수 있음
     */
    val compensations: List<() -> T>,
)
