package com.finalexam.skills.model.dto.response;


import java.time.LocalDate;

public abstract class BaseDto {

  private LocalDate createdAt;
  private LocalDate updatedAt;

  public BaseDto(LocalDate createdAt, LocalDate updatedAt) {
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public BaseDto() {
  }

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
}
