package com.example.wanandroid_2.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 张十八 on 2019/5/6.
 */

@Entity
public class DaoBean {
    @Id
    private Long id;
    private String author;
    private String title;
    private String charname;
    private String charSuperName;
    private String niceDate;
    private String link;

    @Generated(hash = 104160499)
    public DaoBean(Long id, String author, String title, String charname,
            String charSuperName, String niceDate, String link) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.charname = charname;
        this.charSuperName = charSuperName;
        this.niceDate = niceDate;
        this.link = link;
    }

    @Generated(hash = 405743142)
    public DaoBean() {
    }

    @Override
    public String toString() {
        return "DaoBean{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", charname='" + charname + '\'' +
                ", charSuperName='" + charSuperName + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCharname() {
        return this.charname;
    }

    public void setCharname(String charname) {
        this.charname = charname;
    }

    public String getCharSuperName() {
        return this.charSuperName;
    }

    public void setCharSuperName(String charSuperName) {
        this.charSuperName = charSuperName;
    }

    public String getNiceDate() {
        return this.niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
