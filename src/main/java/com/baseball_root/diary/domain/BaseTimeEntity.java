package com.baseball_root.diary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime modifiedAt;

    // Getter for createdAt as LocalDateTime
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Getter for modifiedAt as LocalDateTime
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    // Method to get formatted createdAt as String
    public String getFormattedCreatedAt() {
        return formatDateTime(createdAt);
    }

    // Method to get formatted modifiedAt as String
    public String getFormattedModifiedAt() {
        return formatDateTime(modifiedAt);
    }

    // Private helper method to format LocalDateTime as String
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}
