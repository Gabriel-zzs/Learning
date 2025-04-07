package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "OmsPotalOrderController")
@RequestMapping(value = "/order")
public class OmsPotalOrderController {
    
    @Autowired
    private OmsPortalOrderService portalOrderService;

    @ApiOperation(value = "根据购物车信息生成订单")
    @PostMapping(value = "/generateOrder")
    public void generateOrder() {

    }
}
