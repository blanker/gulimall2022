package com.blank.ecommerce.controller;

import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.service.IAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name="用户地址服务", description = "用户地址服务")
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private IAddressService addressService;

    @Operation(method="POST", description = "创建地址信息")
    @PostMapping("/create-address")
    public TableId createAddressInfo(@RequestBody AddressInfo addressInfo){
        return addressService.createAddressInfo(addressInfo);
    }

    @Operation(method="GET", description = "获取当前用户地址信息")
    @GetMapping("/addresses")
    public AddressInfo getCurrentAddressInfo(){
        return addressService.getCurrentAddressInfo();
    }

    @Operation(method="GET", description = "根据ID获取用户地址信息")
    @GetMapping("/address-info")
    public AddressInfo getAddressInfoById(@RequestParam Long id){
        return addressService.getAddressInfoById(id);
    }

    @Operation(method="POST", description = "根据Table ID获取用户地址信息")
    @PostMapping("/address-info-by-table-id")
    public AddressInfo getAddressInfoByTableId(@RequestBody TableId tableId){
        return addressService.getAddressInfoByTableId(tableId);
    }
}
