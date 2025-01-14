package com.llf.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.llf.utils.ResultPackage;

@RestControllerAdvice
public class BussExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(BussExceptionHandler.class);

    /**
              * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BussException.class)
    public ResultPackage<?> handleBussException(BussException e) {
        logger.info(e.getMessage());
        e.printStackTrace();
        return ResultPackage.failure(e.getCode(), e.getMessage());
    }

    /**
     * exception异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultPackage<?> handleException(Exception e) {
        logger.info(e.getMessage());
        e.printStackTrace();
        return ResultPackage.failure("接口异常，错误信息为" + e.getMessage());
    }
    
    
	 // 使用form data方式调用接口，校验异常抛出 BindException
	 // 使用 json 请求体调用接口，校验异常抛出 MethodArgumentNotValidException
	 // 单个参数校验异常抛出ConstraintViolationException
	 // 处理 json 请求体调用接口校验失败抛出的异常
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResultPackage<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
	     List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
	     List<String> collect = fieldErrors.stream()
	         .map(DefaultMessageSourceResolvable::getDefaultMessage)
	         .collect(Collectors.toList());
	     return ResultPackage.failure(collect);
	 }
	 
//	 // 使用form data方式调用接口，校验异常抛出 BindException
//	 @ExceptionHandler(BindException.class)
//	 public ResultVO<String> BindException(BindException e) {
//	     List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//	     List<String> collect = fieldErrors.stream()
//	         .map(DefaultMessageSourceResolvable::getDefaultMessage)
//	         .collect(Collectors.toList());
//	     return new ResultVO(ResultCode.VALIDATE_FAILED, collect);
//	 }

    /**
              * 权限异常
     *
     * @param e
     * @return
     */
//    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
//    public Result<?> handleAuthorizationException(AuthorizationException e) {
//        logger.info(e.getMessage());
//        e.printStackTrace();
//        return Result.error(CommonEnum.SC_NO_JURISDICTION.getCode(), CommonEnum.SC_NO_JURISDICTION.getMessage());
//    }
}
