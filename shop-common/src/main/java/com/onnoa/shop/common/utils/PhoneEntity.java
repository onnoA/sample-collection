package com.onnoa.shop.common.utils;

import java.math.BigDecimal;

/**
 * @className: PhoneEntity
 * @description:
 * @author: onnoA
 * @date: 2021/10/9
 **/
public class PhoneEntity {

    private static final long serialVersionUID = 1L;

    public PhoneEntity() {
    }

    /**
     * 构造器
     * 注意: 实体类中必须有无参数构造器，有参数构造器，且构造器参数的顺序和数据的顺序必须一致。如果数据格式有变化，需要重新编写一个实体类的构造器
     */
    public PhoneEntity(String plate, BigDecimal number, double memory, double size) {
        super();
        this.plate = plate;
        this.number = String.valueOf(number);
        this.memory = String.valueOf(memory);
        this.size = String.valueOf(size);
    }

    private String plate;
    private String number;
    private String memory;
    private String size;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "PhoneEntity{" +
                "plate='" + plate + '\'' +
                ", number='" + number + '\'' +
                ", memory='" + memory + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
