package com.blank.ecommerce.controller;

import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.service.async.AsyncTaskManager;
import com.blank.ecommerce.vo.AsyncTaskInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/async-goods")
@Tag(name = "商品异步入库服务")
public class AsyncGoodsController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @PostMapping("/import-goods")
    @Operation(method = "POST", description = "商品异步入库")
    public AsyncTaskInfo importGoods(@RequestBody List<GoodsInfo> goodsInfoList) {
        return asyncTaskManager.commitTask(goodsInfoList);
    }

    @GetMapping("/task-info")
    @Operation(method = "GET", description = "商品入库异步任务查询")
    public AsyncTaskInfo getTaskInfo(@RequestParam String taskId) {
        return asyncTaskManager.getTaskInfo(taskId);
    }
}
