package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.CompanyStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.CompanyStatusInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.TopInfo;
import com.nifty.cloud.mb.core.NCMBObject;

public class NcmbConverter {

    public TopInfo convertToTopInfo(NCMBObject ncmbObject) {
        if (ncmbObject == null) {
            return null;
        }
        TopInfo topInfo = new TopInfo();
        CompanyStatusInfo companyStatusInfo = new CompanyStatusInfo();

        //安栄
        CompanyStatus aneiStatus = new CompanyStatus();
        aneiStatus.setCompany(Company.ANNEI);
        aneiStatus.setStatus(getStatus(ncmbObject.getString(Const.TopInfo.company_anei_status_type)));
        companyStatusInfo.setAneiStatus(aneiStatus);

        //YKF
        CompanyStatus ykfStatus = new CompanyStatus();
        ykfStatus.setCompany(Company.YKF);
        ykfStatus.setStatus(getStatus(ncmbObject.getString(Const.TopInfo.company_ykf_status_type)));
        companyStatusInfo.setYkfStatus(ykfStatus);

        //Dream
        CompanyStatus dreamStatus = new CompanyStatus();
        dreamStatus.setCompany(Company.DREAM);
        dreamStatus.setStatus(getStatus(ncmbObject.getString(Const.TopInfo.company_dream_status_type)));
        companyStatusInfo.setDreamStatus(dreamStatus);

        topInfo.setCompanyStatusInfo(companyStatusInfo);
        return topInfo;
    }

    private Status getStatus(String statusType) {
        if (statusType.equals(Status.NORMAL.getStatus())) {
            return Status.NORMAL;
        } else if (statusType.equals(Status.CANCEL.getStatus())) {
            return Status.CANCEL;
        } else if (statusType.equals(Status.CAUTION.getStatus())) {
            return Status.CAUTION;
        } else if (statusType.equals(Status.SUSPEND.getStatus())) {
            return Status.SUSPEND;
        } else {
            return null;
        }
    }
}
