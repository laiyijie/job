package me.laiyijie.job.admin.web.handler;

import me.laiyijie.job.admin.service.exception.BusinessException;
import me.laiyijie.job.swagger.model.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by laiyijie on 11/28/17.
 */
@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleBusinessException(BusinessException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(ex.getCode());
        errorInfo.setMessage(ex.getMsg());
        return ResponseEntity.status(417).body(errorInfo);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleOtherException(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(500);
        errorInfo.setMessage(getTrace(ex));
        return ResponseEntity.status(417).body(errorInfo);

    }

    private String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
