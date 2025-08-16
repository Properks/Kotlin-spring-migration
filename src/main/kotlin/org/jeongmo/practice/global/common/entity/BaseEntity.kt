package org.jeongmo.practice.global.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity (

    @Column(name = "created_at")
    @CreatedDate
    var createdAt : LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt : LocalDateTime = LocalDateTime.now(),
) {
}