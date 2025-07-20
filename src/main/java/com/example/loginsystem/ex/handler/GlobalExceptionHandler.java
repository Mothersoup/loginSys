package com.example.loginsystem.ex.handler;


import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.web.JsonResult;
import com.example.loginsystem.web.ServerCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        log.error("統一處理ServiceException異常,將向客戶端響應:{}", e.getMessage());
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e){
        log.error("get undeclared error{}:{}",e.getClass().getName(),e.getMessage());
        String message = "sever error, please contact the administrator.";
        return JsonResult.fail(ServerCode.ERR_UNKNOWN,message);
    }

    @ExceptionHandler
    public JsonResult handleAccessDeniedException(AccessDeniedException e){
        log.error("AccessDeniedException:{}", e.getMessage());
        String message = "account has no permission to access this resource";
        return JsonResult.fail(ServerCode.ERR_PERMISSION_DENIED,message);
    }



}
