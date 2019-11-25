package com.yaoqun.classroom.common;


import lombok.Data;


@Data
public class Result {

	//系统对应码
	private Integer code;

	//提示信息
	private String message;

	//具体的内容
	private Object data;
	
	//客户端类型(android/ios/web等)
	private String clientType;
	


}