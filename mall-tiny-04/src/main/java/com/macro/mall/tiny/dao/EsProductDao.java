package com.macro.mall.tiny.dao;

import java.util.List;
import com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;



public interface EsProductDao {

    List<EsProduct> getAllProductList();
}
