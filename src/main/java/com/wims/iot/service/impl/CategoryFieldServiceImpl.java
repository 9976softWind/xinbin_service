package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.mapper.CategoryFieldMapper;
import com.wims.iot.model.entity.CategoryField;
import com.wims.iot.service.ICategoryFieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预案类别字段关系映射表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Service
public class CategoryFieldServiceImpl extends ServiceImpl<CategoryFieldMapper, CategoryField> implements ICategoryFieldService {

    @Override
    public Boolean addCategoryField(CategoryField categoryField) {
        return this.baseMapper.insert(categoryField) == 1;
    }

    @Override
    public List<Integer> getFieldsByCategoryId(Integer CategoryId) {
        List<CategoryField> categoryFields = this.baseMapper.selectList(new QueryWrapper<CategoryField>().eq("category_id", CategoryId));
        List<Integer> result = categoryFields.stream()
                .map(CategoryField::getFieldId)
                .collect(Collectors.toList());
        return result;
    }
}
