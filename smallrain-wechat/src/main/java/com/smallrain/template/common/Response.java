package com.smallrain.template.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * 请求 响应数据封装
 * @author wangying.dz3
 *
 */
@Data
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
  
  public static Response excute(String message, boolean  status) {
    Response response = new Response();
    response.setStatus(status?0:-1);
    response.setMessage(message.concat(status?"成功":"失败"));
    return response;
  }
  
}
