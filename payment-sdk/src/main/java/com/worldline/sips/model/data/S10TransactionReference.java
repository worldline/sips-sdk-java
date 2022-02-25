package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class S10TransactionReference {
    private int s10TransactionId;
    private LocalDate s10TransactionIdDate;

    public S10TransactionReference() {

    }

    public S10TransactionReference(int s10TransactionId) {
      this.s10TransactionId = s10TransactionId;
    }

  public int getS10TransactionId() {
    return s10TransactionId;
  }

  public void setS10TransactionId(int s10TransactionId) {
    this.s10TransactionId = s10TransactionId;
  }

  public LocalDate getS10TransactionIdDate() {
    return s10TransactionIdDate;
  }

  public void setS10TransactionIdDate(LocalDate s10TransactionIdDate) {
    this.s10TransactionIdDate = s10TransactionIdDate;
  }
}
