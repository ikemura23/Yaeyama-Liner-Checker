
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * Created by banbara23 on 15/09/21.
 */
public enum Port {
    HATERUMA("波照間", "波照間"), UEHARA("西表(上原)", "上原"), OOHARA("西表(大原)", "大原"), HATOMA("鳩間", "鳩間"), KUROSHIMA(
            "黒島", "黒島"), KOHAMA("小浜", "小浜"), TAKETOMI(
            "竹富", "竹富");

    //表示名
    private String port;
    //検索用の名前
    private String portSimple;

    /**
     * コンストラクタ
     *
     * @param port
     * @param portSimple
     */
    Port(String port, String portSimple) {
        this.port = port;
        this.portSimple = portSimple;
    }


    public String getValue() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public String getPortSimple() {
        return portSimple;
    }

    public void setPortSimple(String portSimple) {
        this.portSimple = portSimple;
    }

}
