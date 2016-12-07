package com.ikmr.banbara23.yaeyama_liner_checker.entity.top;


import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

public class CompanyStatus {
    private Company company;
    private Status status;

    public CompanyStatus() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CompanyStatus{" +
                "company=" + company +
                ", status=" + status +
                '}';
    }
}
