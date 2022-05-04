package com.blank.ecommerce.service.async;

import com.blank.ecommerce.constant.AsyncTaskStatusEnum;
import com.blank.ecommerce.vo.AsyncTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Aspect
@Component
public class AsyncTaskMonitor {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Around("(execution(* com.blank.ecommerce.service.async.AsyncServiceImpl.*(..)))")
    public Object taskHandle(ProceedingJoinPoint joinPoint){
        String taskId = joinPoint.getArgs()[1].toString();
        AsyncTaskInfo taskInfo = asyncTaskManager.getTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task: [{}]", taskId);
        taskInfo.setStatus(AsyncTaskStatusEnum.RUNNING);
        asyncTaskManager.setTaskInfo(taskInfo);

        AsyncTaskStatusEnum status;
        Object result;
        try {
            result = joinPoint.proceed();
            status = AsyncTaskStatusEnum.SUCCESS;
        } catch (Throwable ex) {
            result = null;
            status = AsyncTaskStatusEnum.FAILED;
            log.info("AsyncTaskMonitor async task failed: [{}], error info: [{}]"
                    , taskId, ex.getMessage(), ex);
        }
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime(String.valueOf(
                taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime()));
        asyncTaskManager.setTaskInfo(taskInfo);
        return result;
    }
}
