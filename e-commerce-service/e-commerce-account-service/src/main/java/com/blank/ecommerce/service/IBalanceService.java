package com.blank.ecommerce.service;

import com.blank.ecommerce.account.BalanceInfo;

public interface IBalanceService {
    BalanceInfo getCurrentUserBalanceInfo();

    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
