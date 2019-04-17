package com.ztgeo.workbench.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


/**
 * API访问记录表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-10-09 14:54:00
 */
@Table(name = "api_access_record")
public class ApiAccessRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //api主键ID
    @Column(name = "api_id")
    private String apiId;
	
	    //
    @Column(name = "api_name")
    private String apiName;
	
	    //
    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "api_type")
    private int apiType;

    // 访问者IP
	@Column(name = "access_client_ip")
    private String accessClientIp;

	    //访问时间
    @Column(name = "access_year")
    private String accessYear;
	
	    //
    @Column(name = "access_month")
    private String accessMonth;
	
	    //
    @Column(name = "access_day")
    private String accessDay;
	
	    //访问时间
    @Column(name = "access_time")
    private Date accessTime;

    // 请求数据
	@Column(name = "request_data")
	private String requestData;

	// 响应数据
	@Column(name = "response_data")
	private String responseData;
	// 数据返回标识
	@Column(name = "response_type")
	private String responseType;
	// 发送方用户ID
	@Column(name = "user_real_id")
	private String userRealId;
	//发送是否成功0-成功1-失败
    @Column(name="status")
	private  int status;
    //发送次数
	@Column(name="count")
	private  int count;

	@Transient
	private String startTime;

	@Transient
	private String endTime;

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
	 * 设置：api主键ID
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * 获取：api主键ID
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * 设置：
	 */
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	/**
	 * 获取：
	 */
	public String getApiName() {
		return apiName;
	}
	/**
	 * 设置：
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	/**
	 * 获取：
	 */
	public String getApiUrl() {
		return apiUrl;
	}
	/**
	 * 设置：访问时间
	 */
	public void setAccessYear(String accessYear) {
		this.accessYear = accessYear;
	}
	/**
	 * 获取：访问时间
	 */
	public String getAccessYear() {
		return accessYear;
	}
	/**
	 * 设置：
	 */
	public void setAccessMonth(String accessMonth) {
		this.accessMonth = accessMonth;
	}
	/**
	 * 获取：
	 */
	public String getAccessMonth() {
		return accessMonth;
	}
	/**
	 * 设置：
	 */
	public void setAccessDay(String accessDay) {
		this.accessDay = accessDay;
	}
	/**
	 * 获取：
	 */
	public String getAccessDay() {
		return accessDay;
	}
	/**
	 * 设置：访问时间
	 */
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	/**
	 * 获取：访问时间
	 */
	public Date getAccessTime() {
		return accessTime;
	}

	public String getAccessClientIp() {
		return accessClientIp;
	}

	public void setAccessClientIp(String accessClientIp) {
		this.accessClientIp = accessClientIp;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public int getApiType() {
		return apiType;
	}

	public void setApiType(int apiType) {
		this.apiType = apiType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getUserRealId() {
		return userRealId;
	}

	public void setUserRealId(String userRealId) {
		this.userRealId = userRealId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
