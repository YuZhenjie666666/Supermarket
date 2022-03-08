package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    PmsProductAttributeService pmsProductAttributeService;

    //     url:'/productAttribute/list/'+cid,
//    method:'get',
//    params:params
//    商品分类中的属性列表
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    public CommonResult<CommonPage> getList(@PathVariable Long cid,
                                            @RequestParam(value = "type") Integer type,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        Page page = pmsProductAttributeService.list(cid, type, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    //    url:'/productAttribute/create',
//    method:'post',
//    data:data
//    属性添加
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductAttribute productAttribute) {
        boolean result = pmsProductAttributeService.create(productAttribute);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    //    url:'/productAttribute/update/'+id,
//    method:'post',
//    data:data
//    属性修改
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@RequestBody PmsProductAttribute productAttribute) {
        boolean result = pmsProductAttributeService.updateById(productAttribute);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    //    url:'/productAttribute/'+id,
//    method:'get'
//    获取原始数据
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<PmsProductAttribute> list(@PathVariable Long id) {
        PmsProductAttribute result = pmsProductAttributeService.getById(id);
        return CommonResult.success(result);
    }

    //    url:'/productAttribute/delete',
//    method:'post',
//    data:data
    @RequestMapping("/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean result = pmsProductAttributeService.delete(ids);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }
//    根据商品id获取相应的属性
//    url:'/productAttribute/attrInfo/'+productCategoryId,
//    method:'get'
    @RequestMapping("/attrInfo/{cId}")
    public CommonResult getRelationAttrInfoByCid(@PathVariable Long cId){
        List<RelationAttrInfoDTO> list = pmsProductAttributeService.getRelationAttrInfoByCid(cId);
        return CommonResult.success(list);
    }



}

