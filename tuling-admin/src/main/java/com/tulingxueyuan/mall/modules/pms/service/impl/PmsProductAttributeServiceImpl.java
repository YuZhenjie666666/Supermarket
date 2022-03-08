package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2022-03-03
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {

    @Autowired
    PmsProductAttributeMapper pmsProductAttributeMapper;
    @Autowired
    PmsProductAttributeCategoryService pmsProductAttributeCategory;
    @Override
    public Page list(Long cid, Integer type, Integer pageNum, Integer pageSize) {
        Page page = new Page(pageNum,pageSize);
        QueryWrapper<PmsProductAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductAttribute::getProductAttributeCategoryId,cid)
                .eq(PmsProductAttribute::getType,type)
                .orderByAsc(PmsProductAttribute::getSort);
        return this.page(page,queryWrapper);
    }

    @Override
    public boolean create(PmsProductAttribute productAttribute) {
        Boolean save = this.save(productAttribute);
        if(save){
//            属性
            UpdateWrapper<PmsProductAttributeCategory> UpdateWrapper = new UpdateWrapper<>();
            if(productAttribute.getType()==0){
//                面向数据库语句直接进行+1操作
                UpdateWrapper.setSql("attribute_count=attribute_count+1");
            }
//            参数
            else if(productAttribute.getType() == 1){
                UpdateWrapper.setSql("param_count=param_count+1");
            }
//            根据属性的类型id进行更新操作
            UpdateWrapper.lambda().eq(PmsProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
            pmsProductAttributeCategory.update(UpdateWrapper);
        }
        return save;
    }

    @Override
    @Transactional
    public boolean delete(List<Long> ids) {

//        工具类可以用来判断是否为空或者为null
        if(CollectionUtils.isEmpty(ids)){
            return false;
        }
        PmsProductAttribute productAttribute = null;
        for (Long id:ids) {
            productAttribute = this.getById(id);
            if(productAttribute != null){
                break;
            }
        }
        //        1.删除属性,得到删除后的数量
        int batchIds = pmsProductAttributeMapper.deleteBatchIds(ids);
        if(batchIds > 0 && productAttribute != null){
            //            属性
            UpdateWrapper<PmsProductAttributeCategory> UpdateWrapper = new UpdateWrapper<>();
            if(productAttribute.getType()==0){
//                面向数据库语句直接进行+1操作
                UpdateWrapper.setSql("attribute_count=attribute_count-"+batchIds);
            }
//            参数
            else if(productAttribute.getType() == 1){
                UpdateWrapper.setSql("param_count=param_count-"+batchIds);
            }
//            根据属性的类型id进行更新操作
            UpdateWrapper.lambda().eq(PmsProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
            pmsProductAttributeCategory.update(UpdateWrapper);
        }
        return batchIds >0 ;
    }

    @Override
    public List<RelationAttrInfoDTO> getRelationAttrInfoByCid(Long cId) {
        return pmsProductAttributeMapper.getRelationAttrInfoByCid(cId);
    }
}
