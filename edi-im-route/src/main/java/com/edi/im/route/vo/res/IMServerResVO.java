package com.edi.im.route.vo.res;

import java.io.Serializable;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/12/23 00:43
 * @since JDK 1.8
 */
public class IMServerResVO implements Serializable {

    private String ip ;
    private Integer imServerPort;
    private Integer httpPort;

    public IMServerResVO(String ip, Integer imServerPort, Integer httpPort) {
        this.ip = ip;
        this.imServerPort = imServerPort;
        this.httpPort = httpPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public Integer getImServerPort() {
        return imServerPort;
    }

    public void setImServerPort(Integer imServerPort) {
        this.imServerPort = imServerPort;
    }
}
