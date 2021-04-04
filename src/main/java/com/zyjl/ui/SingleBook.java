package com.zyjl.ui;

import java.util.Arrays;
import java.util.List;

public class SingleBook {
    private int year;
    private List<BookVolume> volumes;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<BookVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<BookVolume> volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return "SingleBook{" +
                "year=" + year +
                ", volumes=" + volumes +
                '}';
    }
}
