package com.onnoa.shop.demo.authority.system.controller;

import com.onnoa.shop.common.distributed.lock.zookeeper.generator.IncrementIdGenerator;
import com.onnoa.shop.common.distributed.lock.zookeeper.generator.UniqueIdGenerator;
import com.onnoa.shop.common.distributed.lock.zookeeper.properties.ZookeeperProperties;
import com.onnoa.shop.common.dto.PageDto;
import com.onnoa.shop.common.properties.base.ShopProperties;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.comment.dto.ContentCommentsAddDto;
import com.onnoa.shop.demo.comment.dto.ContentCommentsListDto;
import com.onnoa.shop.demo.comment.service.ContentCommentService;
import com.onnoa.shop.demo.comment.vo.ContentCommentsListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:31
 */
@RestController
@Slf4j
@RequestMapping(value = "comment")
@Api(tags = "评论相关接口", description = "提供评论相关的 Rest API")
public class CommentController {

    @Autowired
    private ContentCommentService commentsInfoService;

    @PostMapping(value = "add")
    @ApiOperation(value = "新增评论接口", notes = "新增评论")
    public ResultBean commentAdd(@RequestBody @Valid ContentCommentsAddDto addDto) {
        Boolean isSuccess = commentsInfoService.comment(addDto);
        return ResultBean.success(isSuccess);
    }


    @GetMapping("/list")
    @ApiOperation(value = "根据内容id查询评论列表接口", notes = "评论列表")
    public ResultBean commentList(@RequestBody @Valid ContentCommentsListDto listDto) {
        PageDto<ContentCommentsListVo> resultList = commentsInfoService.commentList(listDto);
        return ResultBean.success(resultList);
    }

    @PostMapping(value = "deleted")
    @ApiOperation(value = "删除评论接口", notes = "删除评论")
    public ResultBean deleted(@RequestParam int commentId) {
        Boolean isSuccess = commentsInfoService.deleted(commentId);
        return ResultBean.success(isSuccess);
    }

    @PostMapping(value = "shield")
    @ApiOperation(value = "屏蔽评论接口", notes = "屏蔽评论")
    public ResultBean shield(@RequestParam int commentId) {
        Boolean isSuccess = commentsInfoService.shield(commentId);
        return ResultBean.success(isSuccess);
    }


    @GetMapping(value = "id")
    @ApiOperation(value = "生成id", notes = "获取唯一id")
    public ResultBean getId() {
        ZookeeperProperties zk = new ShopProperties().getZk();
        System.out.println(zk.getZkAddressList());
        Long serviceId = IncrementIdGenerator.getServiceId();
        Long orderNo = UniqueIdGenerator.getInstance(serviceId).nextId();
        return ResultBean.success(orderNo);
    }

}
