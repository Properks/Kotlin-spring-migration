package org.jeongmo.practice.global.annotation

import io.swagger.v3.oas.annotations.Parameter

@Parameter(hidden = true)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class AuthenticatedMember()
