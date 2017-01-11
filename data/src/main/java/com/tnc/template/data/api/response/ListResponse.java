package com.tnc.template.data.api.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListResponse<T> {
	@SerializedName("data")
	private List<T> data;
	@SerializedName("success")
	private boolean success;
	@SerializedName("message")
	private String message;

	public List<T> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage() {
		return message;
	}
}