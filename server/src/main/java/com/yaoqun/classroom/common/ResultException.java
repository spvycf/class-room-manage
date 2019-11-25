package com.yaoqun.classroom.common;



public class ResultException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	//信息对应码
	private Integer code;

	private String message;




	


	public ResultException(Integer msgCode, String message) {
	    super(message);

        this.code = msgCode;
        this.message = message;

    }

	public Integer getMsgCode() {
		return code;
	}

	public void setMsgCode(Integer msgCode) {
		this.code = msgCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}