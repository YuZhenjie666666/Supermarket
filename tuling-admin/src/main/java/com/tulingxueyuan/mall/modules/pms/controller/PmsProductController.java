package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    PmsProductService productService;

//    url:'/product/list',
//    method:'get',
//    params:params
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(ProductConditionDTO condition) {
        Page page = productService.list(condition);
    return CommonResult.success(CommonPage.restPage(page));
}
// url:'/product/update/deleteStatus',
//    method:'post',
//    params:params
    @RequestMapping(value="/update/deleteStatus",method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids") List<Long> ids){
        boolean result = productService.removeByIds(ids);
        if(result){
            return CommonResult.success(result);
        }
        else {
             return CommonResult.failed();
        }
    }

    //export function updatePublishStatus(params) {
    //  return request({
    //    url:'/product/update/publishStatus',
    //    method:'post',
    //    params:params
    //  })
    //}
    //
    //export function createProduct(data) {
    //  return request({
    //    url:'/product/create',
    //    method:'post',
    //    data:data
    //  })
    //}
    //
    //export function updateProduct(id,data) {
    //  return request({
    //    url:'/product/update/'+id,
    //    method:'post',
    //    data:data
    //  })
    //}
    @RequestMapping(value="/update/newStatus",method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        boolean result= productService.updateStatus(newStatus,ids, PmsProduct::getNewStatus);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value="/update/recommendStatus",method = RequestMethod.POST)
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {
        boolean result= productService.updateStatus(recommendStatus,ids,PmsProduct::getRecommandStatus);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value="/update/publishStatus",method = RequestMethod.POST)
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        boolean result= productService.updateStatus(publishStatus,ids,PmsProduct::getPublishStatus);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody ProductSaveParamsDTO productSaveParamsDTO){
        boolean result= productService.create(productSaveParamsDTO);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }

//     url:'/product/updateInfo/'+id,
//    method:'get',编辑商列表内容
    @RequestMapping(value="/updateInfo/{id}")
    public CommonResult getUpdateInfo(@PathVariable Long id){
        ProductUpdateInitDTO updateInitDTO= productService.getUpdateInfo(id);
    return CommonResult.success(updateInitDTO);

//    url:'/product/update/'+id,
//    method:'post',
//    data:data保存商品信息内容
    }
    @RequestMapping(value="/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@RequestBody @Valid ProductSaveParamsDTO productSaveParamsDTO){
        boolean result= productService.update(productSaveParamsDTO);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }






}

