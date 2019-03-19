package com.ztgeo.workbench.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * @author robertshell
 * @version 2019-01-17 15:50:46
 * @email 18851165563@163.com
 */
@Table(name = "ip_mapping_info")
public class IpMappingInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    @Id
    private Integer id;

    //源主机
    @Column(name = "source_host")
    private String sourceHost;

    //目标主机
    @Column(name = "destination_host")
    private String destinationHost;

    //映射地址
    @Column(name = "mapping_ip")
    private String mappingIp;


    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：源主机
     */
    public void setSourceHost(String sourceHost) {
        this.sourceHost = sourceHost;
    }

    /**
     * 获取：源主机
     */
    public String getSourceHost() {
        return sourceHost;
    }

    /**
     * 设置：目标主机
     */
    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    /**
     * 获取：目标主机
     */
    public String getDestinationHost() {
        return destinationHost;
    }

    /**
     * 设置：映射地址
     */
    public void setMappingIp(String mappingIp) {
        this.mappingIp = mappingIp;
    }

    /**
     * 获取：映射地址
     */
    public String getMappingIp() {
        return mappingIp;
    }
}
