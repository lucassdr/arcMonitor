package br.unigranrio.arcd.resources.exceptions;

import java.io.Serializable;

public class StandartError implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String msg;
	private long TimeStamp;

	public StandartError(Integer status, String msg, long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		TimeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		TimeStamp = timeStamp;
	}
} 