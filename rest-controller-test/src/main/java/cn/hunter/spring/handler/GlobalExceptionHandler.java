package cn.hunter.spring.handler;

import cn.hunter.spring.helper.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(annotations = ResponseBody.class)
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse defaultMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        log.error("GlobalExceptionHandler-defaultMethodArgumentNotValidExceptionHandler-error:{}", e.getMessage(), e);
        String alert = String.format("入参格式有误！%s", e.getMessage());
        return commonResponse.fail(alert);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse defaultExceptionHandler(Exception e) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        log.error("GlobalExceptionHandler-defaultExceptionHandler-error:{}", e.getMessage(), e);
        return commonResponse.fail("系统异常，请稍后再试！");
    }

}
