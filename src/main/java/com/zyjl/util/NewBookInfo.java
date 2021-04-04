package com.zyjl.util;

public class NewBookInfo {
    private long id;
    private String bookname;
    private String onlyid;
    private String cover;
    private String sort_id;
    private long rating;
    private String version_year;//add
    private String grade_id;
    private String grade_name;
    private String subject_id;
    private String subject_name;
    private String version_id;
    private String version_name;
    private String city_name;//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getOnlyid() {
        return onlyid;
    }

    public void setOnlyid(String onlyid) {
        this.onlyid = onlyid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_year() {
        return version_year;
    }

    public void setVersion_year(String version_year) {
        this.version_year = version_year;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "NewBookInfo{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", onlyid='" + onlyid + '\'' +
                ", cover='" + cover + '\'' +
                ", sort_id='" + sort_id + '\'' +
                ", rating=" + rating +
                ", version_year='" + version_year + '\'' +
                ", grade_id='" + grade_id + '\'' +
                ", grade_name='" + grade_name + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", version_id='" + version_id + '\'' +
                ", version_name='" + version_name + '\'' +
                ", city_name='" + city_name + '\'' +
                '}';
    }
}
