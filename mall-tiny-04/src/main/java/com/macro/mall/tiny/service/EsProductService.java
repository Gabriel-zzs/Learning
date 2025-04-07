package com.macro.mall.tiny.service;

import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EsProductService {

    int importAll();

    /**
     * 根据关键字搜索名称或副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

    void delete(Long id);

    void delete(List<Long> ids);


}
