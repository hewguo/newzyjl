package com.zyjl.ui;

public class BookVolume {
    private String id;
    private String cover_big;
    private String cover;
    private String volumes_name;
    private int has_content;
    private int need_jiexi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover_big() {
        return cover_big;
    }

    public void setCover_big(String cover_big) {
        this.cover_big = cover_big;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVolumes_name() {
        return volumes_name;
    }

    public void setVolumes_name(String volumes_name) {
        this.volumes_name = volumes_name;
    }

    public int getHas_content() {
        return has_content;
    }

    public void setHas_content(int has_content) {
        this.has_content = has_content;
    }

    public int getNeed_jiexi() {
        return need_jiexi;
    }

    public void setNeed_jiexi(int need_jiexi) {
        this.need_jiexi = need_jiexi;
    }

    @Override
    public String toString() {
        return "BookVolume{" +
                "id='" + id + '\'' +
                ", cover_big='" + cover_big + '\'' +
                ", cover='" + cover + '\'' +
                ", volumes_name='" + volumes_name + '\'' +
                ", has_content=" + has_content +
                '}';
    }
}
