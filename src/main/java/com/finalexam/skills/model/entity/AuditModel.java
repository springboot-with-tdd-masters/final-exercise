package com.finalexam.skills.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel implements Serializable {

  private static final long serialVersionUID = -2338626292552177485L;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private LocalDate createdAt;

  @Column(name = "updated_at", nullable = false)
  @LastModifiedDate
  private LocalDate updatedAt;


  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditModel that = (AuditModel) o;
    return Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt,
        that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, updatedAt);
  }
}
