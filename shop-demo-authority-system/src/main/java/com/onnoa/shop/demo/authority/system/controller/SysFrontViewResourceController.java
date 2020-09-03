package com.onnoa.shop.demo.authority.system.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.authority.system.dto.AddOrUpdateResourceInfoDto;
import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.dto.ButtonListDto;
import com.onnoa.shop.demo.authority.system.dto.FrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.service.SysFrontViewResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 前端资源控制层
 * @Author: onnoA
 * @Date: 2020/7/18 17:16
 */
@RestController
@RequestMapping(value = "sys/frontViewResource")
@Api(tags = "获取资源相关接口", description = "提供获取资源相关的 Rest API")
public class SysFrontViewResourceController {

    @Autowired
    private SysFrontViewResourceService frontViewResourceService;

    /**
     * 功能描述: 根据用户名获取菜单树形结构
     *
     * @param username 用户名
     * @return 用户权限列表
     * @date 2020/7/20 19:58
     */
    @GetMapping(value = "/menuTree")
    @ApiOperation(value = "获取前端资源树", notes = "获取前端资源树")
    public ResultBean getFrontViewResource(@RequestParam String username) {
        List<BaseSysFrontViewResourceDto> frontResourceList = frontViewResourceService.getFrontViewResource(username);
        return ResultBean.success(frontResourceList);
    }

    /**
     * 功能描述: 根据父id和用户名获取父id下的所有按钮列表(获取菜单下的所有按钮)
     *
     * @param requestDto
     * @return
     * @date 2020/7/20 19:58
     */
    @GetMapping(value = "/getButtonList")
    @ApiOperation(value = "获取按钮列表", notes = "获取按钮列表")
    public ResultBean getButtonList(@RequestBody @Valid ButtonListDto requestDto) {
        List<BaseSysFrontViewResourceDto> buttonList = frontViewResourceService.getButtonList(requestDto);
        return ResultBean.success(buttonList);
    }

    /**
     * 功能描述: 获取所有的菜单列表(包括文件夹、文件以及按钮)
     *
     * @param
     * @return
     * @date 2020/7/20 20:01
     */
    @GetMapping(value = "/getAllResourcesTree")
    @ApiOperation(value = "获取所有的菜单资源(包括文件夹、文件以及按钮)", notes = "获取所有的菜单资源")
    public ResultBean getAllFrontViewResourceList() {
        List<BaseSysFrontViewResourceDto> allFrontResourceList = frontViewResourceService.getAllFrontResourceList();
        return ResultBean.success(allFrontResourceList);
    }

    /**
     * 功能描述: 根据id获取前端资源信息
     *
     * @param id
     * @return
     * @date 2020/7/22 23:40
     */
    @GetMapping(value = "/getViewResourceDetailById")
    @ApiOperation(value = "根据id获取前端资源信息", notes = "根据id获取前端资源信息")
    public ResultBean getViewResourcesById(@RequestParam(value = "id") String id) {
        FrontViewResourceDto resultDto = frontViewResourceService.getViewResourceById(id);
        return ResultBean.success(resultDto);
    }

    /**
     * 功能描述: 根据id修改资源
     *
     * @param requestDto
     * @date 2020/7/23 11:10
     */
    @GetMapping(value = "/modifyViewResourceById")
    @ApiOperation(value = "根据id修改资源信息", notes = "根据id修改资源信息")
    public ResultBean modifyResource(@RequestBody @Valid FrontViewResourceDto requestDto) {
        Boolean isSuccess = frontViewResourceService.modifyResource(requestDto);
        if (Boolean.TRUE == isSuccess) {
            return ResultBean.success(Boolean.TRUE);
        }
        else {
            return ResultBean.error(Boolean.FALSE);
        }
    }

    /**
     * 根据id删除资源信息
     * 
     * @param viewId 前端资源id
     */
    @PostMapping(value = "/deleteResourceByViewId")
    @ApiOperation(value = "根据id删除资源信息", notes = "根据id删除资源信息")
    public ResultBean deleteResourceByViewId(@RequestParam(value = "viewId") String viewId) {
        frontViewResourceService.deleteByViewId(viewId);
        return ResultBean.success();

    }

    /**
     * 新增菜单资源文件(包括文件夹、文件或按钮)
     * 
     * @param addInfoDto 请求参数
     */
    @PostMapping(value = "/addResourceInfo")
    @ApiOperation(value = "新增资源文件", notes = "新增资源文件")
    public ResultBean addResourceInfo(@RequestBody @Valid AddOrUpdateResourceInfoDto addInfoDto) {
        frontViewResourceService.addResourceInfo(addInfoDto);
        return ResultBean.success();
    }

    /**
     * 修改资源文件(包括文件夹、文件或按钮)
     * @param addInfoDto
     * @return
     */
    @PostMapping(value = "/updateResourceInfo")
    @ApiOperation(value = "修改资源文件", notes = "修改资源文件")
    public ResultBean updateResourceInfo(@RequestBody @Valid AddOrUpdateResourceInfoDto addInfoDto) {
        frontViewResourceService.updateResourceInfo(addInfoDto);
        return ResultBean.success();
    }







}
