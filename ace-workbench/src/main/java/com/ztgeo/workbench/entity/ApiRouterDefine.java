package com.ztgeo.workbench.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 明文报文转发表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-10-09 10:31:53
 */
@Table(name = "api_router_define")
public class ApiRouterDefine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "path")
    private String path;
	
	    //
    @Column(name = "service_id")
    private String serviceId;
	
	    //
    @Column(name = "url")
    private String url;
	
	    //
    @Column(name = "retryable")
    private Boolean retryable;
	
	    //0表示不可用，1表示可用
    @Column(name = "enabled")
    private Boolean enabled;
	
	    //
    @Column(name = "strip_prefix")
    private Boolean stripPrefix;
	
	    //
    @Column(name = "crt_user_name")
    private String crtUserName;
	
	    //
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //
    @Column(name = "upd_user_name")
    private String updUserName;
	
	    //
    @Column(name = "upd_user_id")
    private String updUserId;
	
	    //
    @Column(name = "upd_time")
    private Date updTime;
	

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	/**
	 * 获取：
	 */
	public String getServiceId() {
		return serviceId;
	}
	/**
	 * 设置：
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：
	 */
	public void setRetryable(Boolean retryable) {
		this.retryable = retryable;
	}
	/**
	 * 获取：
	 */
	public Boolean getRetryable() {
		return retryable;
	}
	/**
	 * 设置：0表示不可用，1表示可用
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * 获取：0表示不可用，1表示可用
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * 设置：
	 */
	public void setStripPrefix(Boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}
	/**
	 * 获取：
	 */
	public Boolean getStripPrefix() {
		return stripPrefix;
	}
	/**
	 * 设置：
	 */
	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}
	/**
	 * 获取：
	 */
	public String getCrtUserName() {
		return crtUserName;
	}
	/**
	 * 设置：
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：
	 */
	public String getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：
	 */
	public Date getCrtTime() {
		return crtTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}
	/**
	 * 获取：
	 */
	public String getUpdUserName() {
		return updUserName;
	}
	/**
	 * 设置：
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	/**
	 * 设置：
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdTime() {
		return updTime;
	}

	@Override
	public String toString() {
		return "ApiRouterDefine{" +
				"id='" + id + '\'' +
				", path='" + path + '\'' +
				", serviceId='" + serviceId + '\'' +
				", url='" + url + '\'' +
				", retryable=" + retryable +
				", enabled=" + enabled +
				", stripPrefix=" + stripPrefix +
				", crtUserName='" + crtUserName + '\'' +
				", crtUserId='" + crtUserId + '\'' +
				", crtTime=" + crtTime +
				", updUserName='" + updUserName + '\'' +
				", updUserId='" + updUserId + '\'' +
				", updTime=" + updTime +
				'}';
	}
}
