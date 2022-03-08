package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductAttributeCateDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;


    //    商品分类接口
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        Page page = pmsProductCategoryService.list(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    //    是否显示导航栏接口
    @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
    public CommonResult updateNavStatus(@RequestParam(value = "ids") List<Long> ids, @RequestParam(value = "navStatus") Integer navStatus) {
        boolean result = pmsProductCategoryService.updateNavStatus(ids, navStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    //    是否显示
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    public CommonResult updateShowStatus(@RequestParam(value = "ids") List<Long> ids, @RequestParam(value = "showStatus") Integer showStatus) {
        boolean result = pmsProductCategoryService.updateShowStatus(ids, showStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    //    删除商品分类操作url:'/productCategory/delete/'+id,
//    method:'post'
    @RequestMapping("/delete/{id}")
    public CommonResult deleteCategory(@PathVariable Long id) {
        boolean result = pmsProductCategoryService.removeById(id);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    //    分类添加
//    url:'/productCategory/create',
//    method:'post',
//    data:data
    @RequestMapping("/create")
    public CommonResult create(@RequestBody PmsProductCategoryDTO productCategoryDTO) {
        boolean result = pmsProductCategoryService.CustomSave(productCategoryDTO);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    //根据商品id获取商品分类
//    url:'/productCategory/'+id,
//    method:'get',
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<PmsProductCategory> update(@PathVariable Long id) {
        return CommonResult.success(pmsProductCategoryService.getById(id));
    }

    //     url:'/productCategory/update/'+id,
//    method:'post',
//    data:data
//    更新数据
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateData(@PathVariable Long id,@RequestBody PmsProductCategoryDTO pmsProductCategory ) {
        boolean result = pmsProductCategoryService.update(pmsProductCategory);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

//     url:'/productCategory/list/withChildren',
//    method:'get'
    @RequestMapping("/list/withChildren")
    public CommonResult getWithChildren(){
       List<ProductCateChildrenDTO> list = pmsProductCategoryService.getWithChildren();
       return CommonResult.success(list);
    }




}

