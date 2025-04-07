package com.macro.mall.tiny.service;

import com.macro.mall.tiny.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

public interface MemberReadHistoryService {

//  生成浏览记录
    int create(MemberReadHistory memberReadHistory);

//  获取浏览记录
    List<MemberReadHistory> list(Long memberId);

    int delete(List<String> ids);
}
