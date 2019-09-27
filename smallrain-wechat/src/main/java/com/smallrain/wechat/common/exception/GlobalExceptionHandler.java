package com.smallrain.wechat.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smallrain.wechat.common.Response;
import com.smallrain.wechat.utils.ExceptionCodeUtil;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * 定义要捕获的异常 可以多个 @ExceptionHandler({})
   *
   * @param request  request
   * @param e        exception
   * @param response response
   * @return 响应结果
   */
  @ExceptionHandler(SmallrainException.class)
  public Response smallrainExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      SmallrainException exception = (SmallrainException) e;
      return Response.exception(exception.getCode(), ExceptionCodeUtil.getFailMessage("自定义异常", exception.getCode(), exception.getMessage()));
  }

  /**
   * 捕获  RuntimeException 异常
   * TODO  如果你觉得在一个 exceptionHandler 通过  if (e instanceof xxxException) 太麻烦
   * TODO  那么你还可以自己写多个不同的 exceptionHandler 处理不同异常
   *
   * @param request  request
   * @param e        exception
   * @param response response
   * @return 响应结果
   */
  @ExceptionHandler(RuntimeException.class)
  public Response runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      RuntimeException exception = (RuntimeException) e;
      return Response.exception(400, exception.getMessage());
  }

  /**
   * 通用的接口映射异常处理方
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                           HttpStatus status, WebRequest request) {
      if (ex instanceof MethodArgumentNotValidException) {
          MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
          return new ResponseEntity<>(Response.exception(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
      }
      if (ex instanceof MethodArgumentTypeMismatchException) {
          MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
          logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
                  + ",信息：" + exception.getLocalizedMessage());
          return new ResponseEntity<>(Response.exception(status.value(), "参数转换失败"), status);
      }
      return new ResponseEntity<>(Response.exception(status.value(), "参数转换失败"), status);
  }
}