package com.blank.ecommerce.service.async;

import com.blank.ecommerce.constant.AsyncTaskStatusEnum;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.vo.AsyncTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class AsyncTaskManager {
    private final Map<String, AsyncTaskInfo> taskContainer = new HashMap<>(16);

    @Autowired
    private IAsyncService asyncService;

    public AsyncTaskInfo initTask(){
        AsyncTaskInfo taskInfo = new AsyncTaskInfo();
        taskInfo.setTaskId(UUID.randomUUID().toString());
        taskInfo.setStatus(AsyncTaskStatusEnum.STARTED);
        taskInfo.setStartTime(new Date());

        taskContainer.put(taskInfo.getTaskId(), taskInfo);
        return taskInfo;
    }

    public AsyncTaskInfo commitTask(List<GoodsInfo> goodsInfos){
        AsyncTaskInfo taskInfo = initTask();
        asyncService.asyncImportGoods(goodsInfos, taskInfo.getTaskId());
        return taskInfo;
    }

    public void setTaskInfo(AsyncTaskInfo taskInfo) {
        taskContainer.put(taskInfo.getTaskId(), taskInfo);
    }

    public AsyncTaskInfo getTaskInfo(String taskId) {
        return taskContainer.get(taskId);
    }
}
