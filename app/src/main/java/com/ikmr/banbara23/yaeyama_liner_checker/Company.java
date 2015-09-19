
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * 会社のenum
 */
public enum Company {
    ANNEI("annei"), YKF("ykf");

    /** フィールド変数 */
    private String company;

    Company(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }
}
