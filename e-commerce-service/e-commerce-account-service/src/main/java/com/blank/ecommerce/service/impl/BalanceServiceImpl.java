package com.blank.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.account.BalanceInfo;
import com.blank.ecommerce.dao.EcommerceBalanceDao;
import com.blank.ecommerce.entity.EcommerceBalance;
import com.blank.ecommerce.filter.AccessContext;
import com.blank.ecommerce.service.IBalanceService;
import com.blank.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {
    @Autowired private EcommerceBalanceDao ecommerceBalanceDao;

    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        final LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo(loginUserInfo.getId(), 0L);
        final EcommerceBalance balance = ecommerceBalanceDao.findByUserId(loginUserInfo.getId());
        if (null != balance) {
            balanceInfo.setBalance(balance.getBalance());
        } else {
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            log.info("insert new balance record: [{}]", JSON.toJSONString(ecommerceBalanceDao.save(newBalance)));
        }
        return balanceInfo;
    }

    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
        final LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        final EcommerceBalance balance = ecommerceBalanceDao.findByUserId(loginUserInfo.getId());
        if (null == balance || balance.getBalance() - balanceInfo.getBalance() < 0) {
            throw new RuntimeException("user balance is not enough");
        }
        Long originBalance = balance.getBalance();
        balance.setBalance(originBalance - balanceInfo.getBalance());
        log.info("deduct balance: [{}, {}, {}]", ecommerceBalanceDao.save(balance).getId(),
                originBalance, balance.getBalance());

        return new BalanceInfo(loginUserInfo.getId(), balance.getBalance() );
    }
}
