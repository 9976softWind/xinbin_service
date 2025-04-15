package com.wims.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.CategoryField;

import java.util.List;

/**
 * <p>
 * 预案类别字段关系映射表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
public interface ICategoryFieldService extends IService<CategoryField> {

    Boolean addCategoryField(CategoryField categoryField);

    List<Integer> getFieldsByCategoryId(Integer CategoryId);

}
