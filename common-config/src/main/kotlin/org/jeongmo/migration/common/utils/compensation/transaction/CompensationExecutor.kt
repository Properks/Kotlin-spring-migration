package org.jeongmo.migration.common.utils.compensation.transaction

interface CompensationExecutor {

    /**
     *  보상 트랜잭션 실행 메서드 구현체
     *  @param compensationOperator 보상 트랜잭션을 실행하기 위한 내용. 구현체에서 해당 정보를 통해 보상 트랜잭션 실행
     */
    fun <T> compensateTransaction(compensationOperator: CompensationOperator<T>)
}
