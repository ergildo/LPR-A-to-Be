package com.atobe.lpr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ReviewResultDTO {
  private Long reviewId;
  private String licensePlateNumber;
}
