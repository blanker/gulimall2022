package com.blank.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blank.common.utils.PageUtils;
import com.blank.gulimall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 17:27:29
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

