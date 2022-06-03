package com.finalexam.skills.model.dto.request;

import java.util.Objects;

public class SkillRequestDto {

  private long id;
  private String description;
  private int duration;
  private String lastUsed;

  public SkillRequestDto() {
  }

  public SkillRequestDto(String description, int duration, String lastUsed) {
    this.description = description;
    this.duration = duration;
    this.lastUsed = lastUsed;
  }

  public SkillRequestDto(long id, String description, int duration, String lastUsed) {
    this.id = id;
    this.description = description;
    this.duration = duration;
    this.lastUsed = lastUsed;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(String lastUsed) {
    this.lastUsed = lastUsed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SkillRequestDto that = (SkillRequestDto) o;
    return id == that.id && duration == that.duration && Objects.equals(description,
        that.description) && Objects.equals(lastUsed, that.lastUsed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, duration, lastUsed);
  }
}
