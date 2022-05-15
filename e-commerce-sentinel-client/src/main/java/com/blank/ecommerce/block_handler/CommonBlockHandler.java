package com.blank.ecommerce.block_handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonBlockHandler {
    public static CommonResponse<String> blockHandler(BlockException exception) {
      log.error("trigger block handler: [{}, {}]",
              JSON.toJSONString(exception.getRule()),
              JSON.toJSONString(exception.getRuleLimitApp()));
        return new CommonResponse<>(-1, "trigger block exception", null);
    }
}
