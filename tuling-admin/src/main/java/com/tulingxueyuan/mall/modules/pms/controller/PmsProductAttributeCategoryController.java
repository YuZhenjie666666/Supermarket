package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductAttributeCateDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    PmsProductAttributeCategoryService productAttributeCategoryService;

    //    查询商品分类列表
    //    url:'/productAttribute/category/list',
    //    method:'get',
    //    params:params
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        Page page = productAttributeCategoryService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    //    url:'/productAttribute/category/create',
//    method:'post',
//    data:data
//    创建商品分类列表
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(PmsProductAttributeCategory pmsProductAttributeCategory) {
        boolean result = productAttributeCategoryService.add(pmsProductAttributeCategory);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    //     url:'/productAttribute/category/update/'+id,
//    method:'post',
//    data:data
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(PmsProductAttributeCategory pmsProductAttributeCategory) {
        boolean result = productAttributeCategoryService.updateById(pmsProductAttributeCategory);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    //    url:'/productAttribute/category/delete/'+id,
//    method:'get'
    @RequestMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean result = productAttributeCategoryService.removeById(id);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

//    url:'/productAttribute/category/list/withAttr',
//    method:'get'
//    筛选属性的级联查询操作
    @RequestMapping("/list/withAttr")
    public CommonResult getListWithAttr(){
        List<ProductAttributeCateDTO> list = productAttributeCategoryService.getListWithAttr();
        return CommonResult.success(list);
    }



}

