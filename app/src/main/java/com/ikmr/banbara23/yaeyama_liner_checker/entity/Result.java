
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import java.util.ArrayList;

public class Result {

    private Company mCompany;
    private String mUpdateTime;
    private String mTitle;
    ArrayList<Liner> mLiners = new ArrayList<>();

    public Result() {
    }

    public Company getCompany() {
        return mCompany;
    }

    public void setCompany(Company company) {
        mCompany = company;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<Liner> getLiners() {
        return mLiners;
    }

    public void setLiners(ArrayList<Liner> liners) {
        mLiners = liners;
    }

    @Override
    public String toString() {
        return "Result{" +
                "mCompany=" + mCompany +
                ", mUpdateTime='" + mUpdateTime + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mLiners=" + mLiners +
                '}';
    }
}
