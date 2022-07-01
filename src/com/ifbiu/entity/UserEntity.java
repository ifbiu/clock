package com.ifbiu.entity;

public class UserEntity {
    private String studentName;
    private String classesName;
    private String studentNum;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "studentName='" + studentName + '\'' +
                ", classesName='" + classesName + '\'' +
                ", studentNum='" + studentNum + '\'' +
                '}';
    }
}
