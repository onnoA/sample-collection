package com.onnoa.shop.common.pattern.builder.demo3;

/**
 * @author onnoA
 * @Description 建造者模式实现链式赋值
 * @date 2021年06月01日 20:19
 */
public class Course {

    // 课程名称
    private String subjectName;

    // 课程资料
    private String subjectData;

    // 笔记
    private String note;

    // 作业
    private String work;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(String subjectData) {
        this.subjectData = subjectData;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }


}
