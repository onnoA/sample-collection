package com.onnoa.shop.common.pattern.builder.demo3;

/**
 * @author onnoA
 * @Description 具体建造类
 * @date 2021年06月01日 20:22
 */
public class CourseBuilder {

    private Course course = new Course();

    public CourseBuilder subjectName(String subjectName) {
        this.course.setSubjectName(subjectName);
        return this;
    }

    public CourseBuilder subjectData(String subjectData) {
        this.course.setSubjectData(subjectData);
        return this;
    }

    public CourseBuilder note(String note) {
        this.course.setNote(note);
        return this;
    }

    public CourseBuilder work(String work) {
        this.course.setWork(work);
        return this;
    }

    public Course build() {
        return this.course;
    }
}
