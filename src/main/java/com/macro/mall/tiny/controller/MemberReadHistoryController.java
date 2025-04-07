package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;
import com.macro.mall.tiny.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "MemberReadHistoryController")
@RequestMapping(value = "/member/readHisory")
public class MemberReadHistoryController {

    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation(value = "创建浏览记录")
    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count =memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "批量删除浏览记录")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam List<String> ids) {
        int count=memberReadHistoryService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "展示浏览记录")
    @GetMapping(value = "/list")
    public CommonResult<List<MemberReadHistory>> getMemberReadHistoryList(Long memberId) {
        List<MemberReadHistory> memberReadHistories=memberReadHistoryService.list(memberId);
        return CommonResult.success(memberReadHistories);
    }
}
