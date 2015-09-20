
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * Created by banbara23 on 15/09/21.
 */
public enum Port {
    HATERUMA("波照間"), UEHARA("西表(上原)"), OOHARA("西表(大原)"), HATOMA("鳩間"), KUROSHIMA("黒島"), KOHAMA("小浜"), TAKETOMI(
            "竹富");

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    private String port;

    Port(String port) {
        this.port = port;
    }
}
