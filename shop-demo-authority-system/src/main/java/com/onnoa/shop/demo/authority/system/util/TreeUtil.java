package com.onnoa.shop.demo.authority.system.util;

import cn.hutool.core.bean.BeanUtil;
import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/18 17:47
 */
@Slf4j
public class TreeUtil {

    public static List<BaseSysFrontViewResourceDto> findTreeStructList(List<BaseSysFrontViewResourceDto> list) {
        // 树根节点
        List<BaseSysFrontViewResourceDto> treeRootStructList = list.parallelStream().filter(viewResource -> {
            return viewResource.getParentId().equals("1");
        }).collect(Collectors.toList());

        return getChildTree(treeRootStructList, list);
    }

    private static List<BaseSysFrontViewResourceDto> getChildTree(List<BaseSysFrontViewResourceDto> treeRootStructList, List<BaseSysFrontViewResourceDto> list) {
        for (BaseSysFrontViewResourceDto treeRoot : treeRootStructList) {
            for (BaseSysFrontViewResourceDto childTree : list) {
                if (treeRoot.getId().equals(childTree.getParentId())) {
                    treeRoot.getChildren().add(childTree);
                }
            }
        }
        treeRootStructList.stream().forEach(treeRoot -> {
            if (CollectionUtils.isNotEmpty(treeRoot.getChildren())) {
                getChildTree(treeRoot.getChildren(), list);
            }
        });
        return treeRootStructList;
    }

    /**
     * 递归，获取父节点下的所有子节点
     *
     * @param dto  父节点对象
     * @param list 要递归的集合
     */
    public static BaseSysFrontViewResourceDto treeListForJava8(BaseSysFrontViewResourceDto dto, List<BaseSysFrontViewResourceDto> list) {
        BaseSysFrontViewResourceDto resourceDto = new BaseSysFrontViewResourceDto();
        BeanUtil.copyProperties(dto, resourceDto);
        List<BaseSysFrontViewResourceDto> child = list.stream()
                .filter(resource -> resource.getParentId().equals(dto.getId()))
                .map(resource -> treeListForJava8(resource, list))
                .collect(Collectors.toList());
        resourceDto.setChildren(child);
        return resourceDto;
    }

    /**
     * 过滤出父id为1的根节点
     *
     * @param list
     * @return
     */
    public static List<BaseSysFrontViewResourceDto> filterRootNode(List<BaseSysFrontViewResourceDto> list) {
        List<BaseSysFrontViewResourceDto> collect = list.stream()
                .filter(resource -> resource.getParentId().equals("1"))
                .map(resource -> treeListForJava8(resource, list)
                ).collect(Collectors.toList());
        return collect;
    }

}
