package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.ClazzHasStudentException;
import org.example.pojo.DeptHasEmpExcption;
import org.example.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("程序出错",e);
        return Result.error("出错了");
    }

    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错",e);
        String message=e.getMessage();
        int i=message.indexOf("Duplicate entry");
        String errMsg=message.substring(i);
        String[] arr=errMsg.split(" ");
        return Result.error(arr[2]+"已存在");
    }

    @ExceptionHandler(ClazzHasStudentException.class)
    public Result handleClazzHasStudentException(ClazzHasStudentException e){
        log.error("删除失败：{}",e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(DeptHasEmpExcption.class)
    public Result handleDeptHasEmpExcption(DeptHasEmpExcption e){
        return Result.error(e.getMessage());
    }
}
