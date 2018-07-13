package com.jasmine.jasmine_core.Models;


import com.jasmine.jasmine_core.Utils.JSONSerializable;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.ArrayList;
import java.util.List;

public class JNDetailedCrossroads extends JSONSerializable {
    private String id;
    private List<Tuple3<String, Double, Integer>> semaphoreList;

    public JNDetailedCrossroads() {
        this.semaphoreList = new ArrayList<>();
    }

    /*
        Getter and Setter
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public List<Tuple3<String, Double, Integer>> getSemaphoreList() {
        return semaphoreList;
    }

    public void setSemaphoreList(List<Tuple3<String, Double, Integer>> semaphoreList) {
        this.semaphoreList = semaphoreList;
    }
}
