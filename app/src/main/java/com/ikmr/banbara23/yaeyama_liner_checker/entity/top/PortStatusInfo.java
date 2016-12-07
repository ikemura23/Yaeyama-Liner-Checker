package com.ikmr.banbara23.yaeyama_liner_checker.entity.top;


public class PortStatusInfo {

    private PortStatus taketomiStatus;
    private PortStatus kohamaStatus;
    private PortStatus kuroshimaStatus;
    private PortStatus ueharaStatus;
    private PortStatus ooharaStatus;
    private PortStatus hatomaStatus;
    private PortStatus haterumaStatus;

    public PortStatus getTaketomiStatus() {
        return taketomiStatus;
    }

    public void setTaketomiStatus(PortStatus taketomiStatus) {
        this.taketomiStatus = taketomiStatus;
    }

    public PortStatus getKohamaStatus() {
        return kohamaStatus;
    }

    public void setKohamaStatus(PortStatus kohamaStatus) {
        this.kohamaStatus = kohamaStatus;
    }

    public PortStatus getKuroshimaStatus() {
        return kuroshimaStatus;
    }

    public void setKuroshimaStatus(PortStatus kuroshimaStatus) {
        this.kuroshimaStatus = kuroshimaStatus;
    }

    public PortStatus getUeharaStatus() {
        return ueharaStatus;
    }

    public void setUeharaStatus(PortStatus ueharaStatus) {
        this.ueharaStatus = ueharaStatus;
    }

    public PortStatus getOoharaStatus() {
        return ooharaStatus;
    }

    public void setOoharaStatus(PortStatus ooharaStatus) {
        this.ooharaStatus = ooharaStatus;
    }

    public PortStatus getHatomaStatus() {
        return hatomaStatus;
    }

    public void setHatomaStatus(PortStatus hatomaStatus) {
        this.hatomaStatus = hatomaStatus;
    }

    public PortStatus getHaterumaStatus() {
        return haterumaStatus;
    }

    public void setHaterumaStatus(PortStatus haterumaStatus) {
        this.haterumaStatus = haterumaStatus;
    }

    @Override
    public String toString() {
        return "PortStatusInfo{" +
                "taketomiStatus=" + taketomiStatus +
                ", kohamaStatus=" + kohamaStatus +
                ", kuroshimaStatus=" + kuroshimaStatus +
                ", ueharaStatus=" + ueharaStatus +
                ", ooharaStatus=" + ooharaStatus +
                ", hatomaStatus=" + hatomaStatus +
                ", haterumaStatus=" + haterumaStatus +
                '}';
    }
}
