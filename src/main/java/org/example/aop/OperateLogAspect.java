package org.example.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.anno.Log;
import org.example.mapper.OperateLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.pojo.OperateLog;
import org.example.utils.CurrentHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面类：拦截增/删/改接口，记录操作日志
 */
@Slf4j
@Aspect // 标记为切面类
@Component // 交给Spring容器管理
@RequiredArgsConstructor // 构造器注入依赖（替代@Autowired）
public class OperateLogAspect {

    // 注入日志Mapper（已提供）
    private final OperateLogMapper operateLogMapper;
    // JSON序列化工具（处理参数/返回值转字符串）
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(org.example.anno.Log) && within(org.example.controller..*)")
    public void operateLogPointcut() {}

    @Around("operateLogPointcut()")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 初始化日志对象
        OperateLog logEntity = new OperateLog();
        long startTime = System.currentTimeMillis(); // 记录方法开始执行时间

        try {
            // 2. 执行目标方法，获取返回值
            Object result = joinPoint.proceed();

            // 3. 组装日志信息（成功场景）
            this.fillLogInfo(joinPoint, logEntity, result, null);
            return result; // 正常返回方法执行结果
        } catch (Exception e) {
            // 4. 组装日志信息（异常场景：返回值记录异常信息）
            this.fillLogInfo(joinPoint, logEntity, null, e);
            throw e; // 抛出异常，不影响原有业务逻辑
        } finally {
            // 5. 计算方法执行耗时（无论成功/失败都记录）
            logEntity.setCostTime(System.currentTimeMillis() - startTime);
            // 6. 保存日志到数据库（建议异步执行，避免阻塞接口，此处先同步示例）
            try {
                log.info("保存操作日志：{}", logEntity);
                operateLogMapper.insert(logEntity);
            } catch (Exception e) {
                log.error("保存操作日志失败", e); // 日志保存失败不影响主业务
            }
        }
    }

    /**
     * 填充日志核心信息
     * @param joinPoint 切入点对象（获取类/方法/参数信息）
     * @param logEntity 日志实体
     * @param result 方法返回值（正常为返回结果，异常为null）
     * @param e 异常对象（异常场景不为null）
     */
    private void fillLogInfo(ProceedingJoinPoint joinPoint, OperateLog logEntity, Object result, Exception e) {
        // 1. 操作人ID（需替换为实际获取逻辑：如从ThreadLocal/Token/Session中获取登录用户ID）
        logEntity.setOperateEmpId(getCurrentEmpId());

        // 2. 操作时间（当前时间）
        logEntity.setOperateTime(LocalDateTime.now());

        // 3. 目标类全类名
        logEntity.setClassName(joinPoint.getTarget().getClass().getName());

        // 4. 目标方法名
        logEntity.setMethodName(joinPoint.getSignature().getName());

        // 5. 方法参数（序列化为JSON字符串，处理参数过长/特殊字符）
        try {
            String params = objectMapper.writeValueAsString(joinPoint.getArgs());
            // 限制参数长度（避免超出数据库字段2000的限制）
            logEntity.setMethodParams(params.length() > 2000 ? params.substring(0, 1997) + "..." : params);
        } catch (JsonProcessingException ex) {
            log.error("序列化方法参数失败", ex);
            logEntity.setMethodParams("参数序列化失败：" + ex.getMessage());
        }

        // 6. 返回值（正常返回结果/异常信息）
        try {
            String returnVal;
            if (e != null) {
                returnVal = "方法执行异常：" + e.getClass().getName() + " - " + e.getMessage();
            } else {
                returnVal = objectMapper.writeValueAsString(result);
            }
            // 限制返回值长度（避免超出数据库字段2000的限制）
            logEntity.setReturnValue(returnVal.length() > 2000 ? returnVal.substring(0, 1997) + "..." : returnVal);
        } catch (JsonProcessingException ex) {
            log.error("序列化返回值失败", ex);
            logEntity.setReturnValue("返回值序列化失败：" + ex.getMessage());
        }
    }

    private Integer getCurrentEmpId() {
        Integer empId= CurrentHolder.getCurrentId();
        return empId==null?0:empId;
    }
}