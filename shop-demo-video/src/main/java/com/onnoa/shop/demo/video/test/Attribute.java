package com.onnoa.shop.demo.video.test;

/**
 * @className: Attribute
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class Attribute implements Element {
    private String name;

    private String value;

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getFormattedContent(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("=\"");
        sb.append(value);
        sb.append('\"');
        return sb.toString();
    }
}