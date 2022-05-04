package com.blank.ecommerce.service;

import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.common.TableId;

public interface IAddressService {

    TableId createAddressInfo(AddressInfo addressInfo);

    AddressInfo getCurrentAddressInfo();

    AddressInfo getAddressInfoById(Long id);

    AddressInfo getAddressInfoByTableId(TableId tableId);
}
