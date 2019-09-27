package com.smallrain.wechat.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

/**
 * 请求 响应数据封装
 * @author wangying.dz3
 *
 */
@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Response {
  int status;
  String message;
  Object data;

  public static Response success(Object obj) {
    Response response = new Response();
    response.setStatus(0);
    response.setMessage("success");
    response.setData(obj);
    return response;
  }

  public static Response fail(int i, String msg) {
    Response response = new Response();
    response.setStatus(i);
    response.setMessage(msg);
    return response;
  }
  
  public static Response fail(int i, String msg, Object data) {
    Response response = new Response();
    response.setStatus(i);
    response.setMessage(msg);
    response.setData(data);
    return response;
  }
  
  public static Response exception(int exceptionCode,String message) {
    Response response = new Response();
    response.setStatus(exceptionCode);
    response.setMessage(message);
    return response;
  }
  
}
