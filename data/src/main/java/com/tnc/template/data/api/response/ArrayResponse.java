package com.tnc.template.data.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CUSDungVT on 1/11/2017.
 */

public class ArrayResponse<T> {
  @SerializedName("data")
  private T[] data;
  @SerializedName("success")
  private boolean success;
  @SerializedName("message")
  private String message;

  public T[] getData(){
    return data;
  }

  public boolean isSuccess(){
    return success;
  }

  public String getMessage() {
    return message;
  }
}
