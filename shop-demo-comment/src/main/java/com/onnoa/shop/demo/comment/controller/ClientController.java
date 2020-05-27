package com.onnoa.shop.demo.comment.controller;

import com.onnoa.shop.common.distributed.lock.zookeeper.generator.IncrementIdGenerator;
import com.onnoa.shop.common.distributed.lock.zookeeper.generator.UniqueIdGenerator;
import com.onnoa.shop.common.distributed.lock.zookeeper.properties.ZookeeperProperties;
import com.onnoa.shop.common.distributed.primarykey.serialnumber.SerialNumberUtils;
import com.onnoa.shop.common.exception.ServiceExceptionUtil;
import com.onnoa.shop.common.exception.SysErrorCodeEnum;
import com.onnoa.shop.common.properties.base.ShopProperties;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.comment.dto.CommentsInfoDTO;
import com.onnoa.shop.demo.comment.service.CommentsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:31
 */
@RestController
@Slf4j
@RequestMapping(value = "comment")
@Api(tags = "评论相关接口", description = "提供评论相关的 Rest API")
public class ClientController {

    @Autowired
    private CommentsInfoService commentsInfoService;

    @GetMapping(value = "id")
    @ApiOperation(value = "生成id", notes = "获取唯一id")
    public ResultBean getId() {
        ZookeeperProperties zk = new ShopProperties().getZk();
        System.out.println(zk.getZkAddressList());
        Long serviceId = IncrementIdGenerator.getServiceId();
        Long orderNo = UniqueIdGenerator.getInstance(serviceId).nextId();
        return ResultBean.success(orderNo);
    }

    @PostMapping(value = "add")
    @ApiOperation(value = "新增评论接口", notes = "新增评论")
    public ResultBean videoAdd(@RequestParam String videoId) {

        CommentsInfoDTO dto = new CommentsInfoDTO();
        dto.setContent("评论测试");
        dto.setOwnerId("zh");
        dto.setType(2);
        dto.setFromId("评论人id==》111111");
        dto.setFromName("评论人的名字");
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        dto.setContent("这个一个评论");
        long save = commentsInfoService.save(dto);

        return (1 == save) ? ResultBean.success(save) : ResultBean.error(ServiceExceptionUtil.error(SysErrorCodeEnum.SYSTEM_GATEWAY_ERROR));
    }


    /*@GetMapping("/get-avatar")
    public UserInfoForComments getAvatarByUserId(@RequestParam("userId") String userId) {
        UserInfo info = userService.findById(userId);
        if (info == null){
            return null;
        }
        return new UserInfoForComments(info.getId(), info.getAvatar());
    }*/

    @GetMapping("/list")
    @ApiOperation(value = "评论列表接口", notes = "评论列表")
    public ResultBean getAvatarByUserId(@RequestParam("ownerId") String ownerId) {
        List<CommentsInfoDTO> list = commentsInfoService.findByOwnerId(ownerId);
        /*if (info == null){
            return null;
        }
        return new UserInfoForComments(info.getId(), info.getAvatar());*/
        return ResultBean.success(list);
    }


}
