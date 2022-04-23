package com.blank.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blank.common.utils.PageUtils;
import com.blank.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 17:27:29
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

