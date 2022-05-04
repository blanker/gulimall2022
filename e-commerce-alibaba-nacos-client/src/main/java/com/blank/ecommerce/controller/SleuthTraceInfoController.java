package com.blank.ecommerce.controller;

import com.blank.ecommerce.service.SleuthTraceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sleuth")
public class SleuthTraceInfoController {
    @Autowired
    private SleuthTraceInfoService sleuthTraceInfoService;

    @GetMapping("/trace-info")
    public void traceInfo(){
        sleuthTraceInfoService.logCurrentTraceInfo();
    }

}
