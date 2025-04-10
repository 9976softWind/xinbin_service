package com.wims.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.common.model.Option;
import com.wims.iot.model.entity.Cata;
import com.wims.iot.model.form.CataForm;
import com.wims.iot.model.query.CataQuery;
import com.wims.iot.model.vo.CataVO;

import java.util.List;

/**
 * <p>
 * 目录表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
public interface ICataService extends IService<Cata> {
    /**
     * 目录列表
     *
     * @return
     */
    List<CataVO> listCatas(CataQuery queryParams);

    /**
     * 目录树形下拉选项
     *
     * @return
     */
    List<Option> listCataOptions();

    /**
     * 新增目录
     *
     * @param formData
     * @return
     */
    Long saveCata(CataForm formData);

    /**
     * 修改目录
     *
     * @param cataId
     * @param formData
     * @return
     */
    Long updateCata(Long cataId, CataForm formData);

    /**
     * 删除目录
     *
     * @param ids 目录ID，多个以英文逗号,拼接字符串
     * @return
     */
    boolean deleteByIds(String ids);

    /**
     * 获取目录详情
     *
     * @param cataId
     * @return
     */
    CataForm getCataForm(Long cataId);
}
