
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

/**
 * ライナー情報
 */
public class LinerInfo {
    Company company;
    String updateDateTime;
    String comment;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "LinerInfo{" +
                "company=" + company +
                ", updateDateTime='" + updateDateTime + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
