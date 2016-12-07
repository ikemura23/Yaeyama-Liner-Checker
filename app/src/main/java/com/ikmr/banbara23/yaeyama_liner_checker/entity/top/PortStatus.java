package com.ikmr.banbara23.yaeyama_liner_checker.entity.top;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

public class PortStatus {
    private Port port;
    private Status status;

    public PortStatus() {
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PortStatus{" +
                "port=" + port +
                ", status=" + status +
                '}';
    }
}
