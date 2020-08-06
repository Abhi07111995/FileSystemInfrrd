package com.example.reviewMovie.bean;


import java.util.List;


public class Response {
	
	public Response(String msg, boolean success, Object result) {
		super();
		this.msg = msg;
		this.success = success;
		this.result = result;
	}
	public Response() {
	}
	private String msg="";
	@Override
	public String toString() {
		return "Response [msg=" + msg + ", success=" + success + ", result=" + result + "]";
	}
	private boolean success=true;
	Object result;
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}	

}

