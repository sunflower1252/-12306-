package com.sanzuniao.controller;

import com.sanzuniao.exception.BusinessException;
import com.sanzuniao.response.Result;
import org.apache.ibatis.builder.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) throws Exception {
        // LOG.info("seata全局事务ID: {}", RootContext.getXID());
        // // 如果是在一次全局事务里出异常了，就不要包装返回值，将异常抛给调用方，让调用方回滚事务
        // if (StrUtil.isNotBlank(RootContext.getXID())) {
        //     throw e;
        // }
        Result result = new Result();
        LOG.error("系统异常：", e);
        result.setSuccess(false);
        result.setMessage("系统出现异常，请联系管理员");
        return result;
    }

    /**
     * 业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BuilderException.class)
    @ResponseBody
    public Result exceptionHandler(BusinessException e) {
        Result Result = new Result();
        LOG.error("业务异常：{}", e.getE().getDesc());
        Result.setSuccess(false);
        Result.setMessage(e.getE().getDesc());
        return Result;
    }

    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result exceptionHandler(BindException e) {
        Result Result = new Result();
        LOG.error("校验异常：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        Result.setSuccess(false);
        Result.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return Result;
    }

    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result exceptionHandler(RuntimeException e) {
        throw e;
    }

}
