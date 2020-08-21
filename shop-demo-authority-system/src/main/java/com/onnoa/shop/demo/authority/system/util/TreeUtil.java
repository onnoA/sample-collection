package com.onnoa.shop.demo.authority.system.util;

import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/18 17:47
 */
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

}
