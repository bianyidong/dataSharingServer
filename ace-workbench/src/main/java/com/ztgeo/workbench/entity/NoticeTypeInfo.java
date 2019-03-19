package com.ztgeo.workbench.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 通知类型表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
@Table(name = "notice_type_info")
public class NoticeTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private  String Id;
	    //类型Id
    @Column(name = "type_id")
    private String typeId;
	
	    //类型描述
    @Column(name = "type_desc")
    private String typeDesc;
	
	    //
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //
    @Column(name = "upd_time")
    private Date updTime;
	
	    //
    @Column(name = "upd_user_id")
    private String updUserId;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * 设置：类型描述
	 */
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	/**
	 * 获取：类型描述
	 */
	public String getTypeDesc() {
		return typeDesc;
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
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdTime() {
		return updTime;
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
}
