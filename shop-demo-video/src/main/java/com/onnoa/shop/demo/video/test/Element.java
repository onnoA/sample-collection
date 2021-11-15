package com.onnoa.shop.demo.video.test;

/**
 * @className: Element
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public interface Element {

    /**
     * element content
     *
     * @param indentLevel the level is control out format, like <b>Tab</b>
     * @return the result String
     */
    String getFormattedContent(int indentLevel);

}
