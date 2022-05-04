package com.blank.ecommerce.vo;

import com.blank.ecommerce.constant.AsyncTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsyncTaskInfo {
    private String taskId;
    private AsyncTaskStatusEnum status;
    private Date startTime;
    private Date endTime;
    private String totalTime;
}
