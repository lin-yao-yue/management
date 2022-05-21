package com.company.schedules;

public class Course {
    private String cno;
    private String cname;
    private int stuCnts;
    private String studyTime;
    private String credit;

    public String getCno() {
        return cno;
    }

    public void setCno(String no) {
        this.cno = no;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String name) {
        this.cname = name;
    }

    public int getStuCnts() {
        return stuCnts;
    }

    public void setStuCnts(int stuCnts) {
        this.stuCnts = stuCnts;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
