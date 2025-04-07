package com.macro.mall.tiny.controller;


import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.macro.mall.tiny.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "EsProductController")
@RestController
@RequestMapping(value = "/esProduct")
public class EsProductController {

    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据库中的商品到ES")
    @PostMapping(value = "/imporAll")
    public CommonResult<Integer> improtAllList(){
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "简单搜索")
    @GetMapping(value = "/search/simple")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @ApiOperation(value = "根据id删除商品")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id){
        esProductService.delete(id);
    }

    @ApiOperation(value = "根据id批量删除商品")
    @PostMapping(value = "/delete/batch")
    public void delete(@RequestParam List<Long> ids){
        esProductService.delete(ids);
    }

}
