package com.ikmr.banbara23.yaeyama_liner_checker.entity.top;

/**
 * トップ画面に使う情報
 */
public class TopInfo {
    private PortStatusInfo portStatusInfo;
    private CompanyStatusInfo companyStatusInfo;

    public TopInfo() {
    }

    public PortStatusInfo getPortStatusInfo() {
        return portStatusInfo;
    }

    public void setPortStatusInfo(PortStatusInfo portStatusInfo) {
        this.portStatusInfo = portStatusInfo;
    }

    public CompanyStatusInfo getCompanyStatusInfo() {
        return companyStatusInfo;
    }

    public void setCompanyStatusInfo(CompanyStatusInfo companyStatusInfo) {
        this.companyStatusInfo = companyStatusInfo;
    }

    @Override
    public String toString() {
        return "TopInfo{" +
                "portStatusInfo=" + portStatusInfo +
                ", companyStatusInfo=" + companyStatusInfo +
                '}';
    }
}
