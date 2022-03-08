package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2022-03-03
 */
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {

    Page list(Long cid, Integer type, Integer pageNum, Integer pageSize);

    boolean create(PmsProductAttribute productAttribute);

    boolean delete(List<Long> ids);

    List<RelationAttrInfoDTO> getRelationAttrInfoByCid(Long cId);
}
