package org.jeongmo.migration.common.token.application.constants

enum class TokenType {
    ACCESS,
    REFRESH,
    /**
     * For blacklist, Not a token type
     */
    BLACK_LIST,
}