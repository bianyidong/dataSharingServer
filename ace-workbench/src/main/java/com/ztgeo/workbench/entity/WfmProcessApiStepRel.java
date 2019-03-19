package com.ztgeo.workbench.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 流程接口服务关系表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Table(name = "wfm_process_api_step_rel")
public class WfmProcessApiStepRel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String relId;
	
	    //步骤ID
    @Column(name = "s_id")
    private String sId;
	
	    //
    @Column(name = "api_id")
    private String apiId;
	
	    //是否被访问过 0 --未被访问，1--已被访问
    @Column(name = "status")
    private Boolean status;

    // -----------api_base_info表-----------
	@Transient
	private String apiPubkey; // api标识
	@Transient
	private String apiName; // api名称

	/**
	 * 设置：
	 */
	public void setRelId(String relId) {
		this.relId = relId;
	}
	/**
	 * 获取：
	 */
	public String getRelId() {
		return relId;
	}
	/**
	 * 设置：步骤ID
	 */
	public void setSId(String sId) {
		this.sId = sId;
	}
	/**
	 * 获取：步骤ID
	 */
	public String getSId() {
		return sId;
	}
	/**
	 * 设置：
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * 获取：
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * 设置：是否被访问过 0 --未被访问，1--已被访问
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	/**
	 * 获取：是否被访问过 0 --未被访问，1--已被访问
	 */
	public Boolean getStatus() {
		return status;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiPubkey() {
		return apiPubkey;
	}

	public void setApiPubkey(String apiPubkey) {
		this.apiPubkey = apiPubkey;
	}

	@Override
	public String toString() {
		return "WfmProcessApiStepRel{" +
				"relId='" + relId + '\'' +
				", sId='" + sId + '\'' +
				", apiId='" + apiId + '\'' +
				", status=" + status +
				", apiPubkey='" + apiPubkey + '\'' +
				", apiName='" + apiName + '\'' +
				'}';
	}
}
