package com.ztgeo.workbench.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 通知基础信息配置表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
@Table(name = "notice_base_info")
public class NoticeBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String noticeId;
	
	    //用户真实ID
    @Column(name = "user_real_id")
    private String userRealId;

	@Column(name = "name")
    private  String name;
	// 用户登录名
	@Column(name = "username")
	private String username;

	    //通知转发路径
    @Column(name = "notice_path")
    private String noticePath;
	
	    //http请求方法
    @Column(name = "method")
    private String method;
	//通知类型
	@Column(name = "type_id")
	private String typeID;
	    //通知说明
    @Column(name = "notice_note")
    private String noticeNote;
	
	    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //创建UserID
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //更行时间
    @Column(name = "upd_time")
    private Date updTime;
	
	    //更新UserID
    @Column(name = "upd_user_id")
    private String updUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	/**
	 * 获取：
	 */
	public String getNoticeId() {
		return noticeId;
	}

	/**
	 * 设置：
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * 获取：用户真实ID
	 */
	public String getUserRealId() {
		return userRealId;
	}

	/**
	 * 设置：用户真实ID
	 */
	public void setUserRealId(String userRealId) {
		this.userRealId = userRealId;
	}

	/**
	 * 获取：通知转发路径
	 */
	public String getNoticePath() {
		return noticePath;
	}

	/**
	 * 设置：通知转发路径
	 */
	public void setNoticePath(String noticePath) {
		this.noticePath = noticePath;
	}

	/**
	 * 获取：http请求方法
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置：http请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 获取：通知说明
	 */
	public String getNoticeNote() {
		return noticeNote;
	}

	/**
	 * 设置：通知说明
	 */
	public void setNoticeNote(String noticeNote) {
		this.noticeNote = noticeNote;
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
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
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
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
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
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
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
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：
	 */
	public String getTypeID() {
		return typeID;
	}
	/**
	 * 设置：
	 */
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	@Override
	public String toString() {
		return "NoticeBaseInfo{" +
				"noticeId='" + noticeId + '\'' +
				", userRealId='" + userRealId + '\'' +
				", name='" + name + '\'' +
				", username='" + username + '\'' +
				", noticePath='" + noticePath + '\'' +
				", method='" + method + '\'' +
				", typeID='" + typeID + '\'' +
				", noticeNote='" + noticeNote + '\'' +
				", crtTime=" + crtTime +
				", crtUserId='" + crtUserId + '\'' +
				", updTime=" + updTime +
				", updUserId='" + updUserId + '\'' +
				'}';
	}
}
