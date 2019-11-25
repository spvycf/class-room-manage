package com.yaoqun.classroom.common;



public class ResultUtil {

	/**
	 * 成功结果
	 * @param object 传参
	 * @param resultEnum 错误代码
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 *
	 */
	@SuppressWarnings("all")
	public static Result Success(String message,Object object) {
		Result result=new Result();
		result.setCode(ResultCode.SUCCESS);
		result.setMessage(message);
		result.setData(object);
		result.setClientType("web");
		return result;
	}

	/**
	 * 错误结果-不正常，需清空token
	 * @param resultEnum 错误代码
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 *
	 */
	@SuppressWarnings("all")
	public static Result Fail(String message,Object object) {
		Result result=new Result();
		result.setCode(ResultCode.FAIL);
		result.setMessage(message);
		result.setData(object);
		result.setClientType("web");
		return result;
	}

}


