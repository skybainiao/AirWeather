package com.example.instest.Model;

public class Weather {

    private String maxTem;
    private String minTem;
    private String nowTem;
    private String cloud;
    private String date;

    public Weather(String date,String cloud,String minTem,String maxTem){
        this.date = date;
        this.cloud = cloud;
        this.minTem = minTem;
        this.maxTem = maxTem;
    }

    public Weather(String date,String cloud,String maxTem,String minTem,String nowTem){
        this.date = date;
        this.cloud = cloud;
        this.maxTem = maxTem;
        this.minTem = minTem;
        this.nowTem = nowTem;
    }


    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMaxTem(String maxTem) {
        this.maxTem = maxTem;
    }

    public void setMinTem(String minTem) {
        this.minTem = minTem;
    }

    public void setNowTem(String nowTem) {
        this.nowTem = nowTem;
    }

    public String getCloud() {
        return cloud;
    }

    public String getDate() {
        return date;
    }

    public String getMaxTem() {
        return maxTem;
    }

    public String getMinTem() {
        return minTem;
    }

    public String getNowTem() {
        return nowTem;
    }

    @Override
    public String toString() {
        return date+"   "+cloud+"   "+minTem+"/"+maxTem;
    }
}
