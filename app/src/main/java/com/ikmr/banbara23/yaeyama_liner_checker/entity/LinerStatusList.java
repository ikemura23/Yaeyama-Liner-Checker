package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import java.util.List;

/**
 * ステータス一覧
 */
public class LinerStatusList extends LinerInfo {
    List<LinerStatus> linerStatusList;

    public List<LinerStatus> getLinerStatusList() {
        return linerStatusList;
    }

    public void setLinerStatusList(List<LinerStatus> linerStatusList) {
        this.linerStatusList = linerStatusList;
    }
}
