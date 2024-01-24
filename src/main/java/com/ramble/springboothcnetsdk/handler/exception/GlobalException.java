package com.ramble.springboothcnetsdk.handler.exception;

import com.ramble.springboothcnetsdk.handler.GlobalResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;



@RestControllerAdvice("com.nghsmart")
@ResponseBody
@Slf4j
public class GlobalException {

    /**
     * 处理404异常
     *
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return new ResponseEntity<>(
                new GlobalResponseEntity(false, "404000",
                        e.getMessage() == null ? "请求的资源不存在" : e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * 捕获业务异常，捕获自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizServiceException.class)
    public ResponseEntity<Object> handleBizServiceException(BizServiceException e) {
        return new ResponseEntity<>(new GlobalResponseEntity(false, e.getCode().toString(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 捕获参数校验异常，javax.validation.constraints
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = "参数校验失败";
        List<FieldFailedValidate> fieldFailedValidates = this.extractFailedMessage(e.getBindingResult().getFieldErrors());
        if (null != fieldFailedValidates && fieldFailedValidates.size() > 0) {
            msg = fieldFailedValidates.get(0).getMessage();
        }
        return new ResponseEntity<>(
                new GlobalResponseEntity<>(false, "010200", msg, null),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * 组装validate错误信息
     *
     * @param fieldErrors
     * @return
     */
    private List<FieldFailedValidate> extractFailedMessage(List<FieldError> fieldErrors) {
        List<FieldFailedValidate> fieldFailedValidates = new ArrayList<>();
        if (null != fieldErrors && fieldErrors.size() > 0) {
            FieldFailedValidate fieldFailedValidate = null;
            for (FieldError fieldError : fieldErrors) {
                fieldFailedValidate = new FieldFailedValidate();
                fieldFailedValidate.setMessage(fieldError.getDefaultMessage());
                fieldFailedValidate.setName(fieldError.getField());
                fieldFailedValidates.add(fieldFailedValidate);
            }
        }
        return fieldFailedValidates;
    }

    /**
     * 捕获运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException:", e);
        return new ResponseEntity<>(
                new GlobalResponseEntity(false, "rt555",
                        e.getMessage() == null ? "运行时异常" : e.getMessage().replace("java.lang.RuntimeException: ", "")),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 捕获一般异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return new ResponseEntity<>(
                new GlobalResponseEntity<>(false, "555",
                        e.getMessage() == null ? "未知异常" : e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
