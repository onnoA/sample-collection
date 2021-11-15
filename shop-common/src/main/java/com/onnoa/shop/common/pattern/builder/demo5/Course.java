package com.onnoa.shop.common.pattern.builder.demo5;

/**
 * @author onnoA
 * @Description 使用静态内部类实现建造者模式
 * @date 2021年06月02日 9:54
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

    @Override
    public String toString() {
        return "Course{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectData='" + subjectData + '\'' +
                ", note='" + note + '\'' +
                ", work='" + work + '\'' +
                '}';
    }

    public static class Builder {
        private Course course = new Course();

        public Builder subjectName(String subjectName) {
            this.course.setSubjectName(subjectName);
            return this;
        }

        public Builder subjectData(String subjectData) {
            this.course.setSubjectData(subjectData);
            return this;
        }

        public Builder note(String note) {
            this.course.setNote(note);
            return this;
        }

        public Builder work(String work) {
            this.course.setWork(work);
            return this;
        }

        public Course build() {
            return this.course;
        }
    }
}
