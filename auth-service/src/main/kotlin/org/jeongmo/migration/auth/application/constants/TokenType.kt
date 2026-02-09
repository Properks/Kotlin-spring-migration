package org.jeongmo.migration.auth.application.constants

enum class TokenType {
    ACCESS,
    REFRESH,
    /**
     * For blacklist, Not a token type
     */
    BLACK_LIST,
}