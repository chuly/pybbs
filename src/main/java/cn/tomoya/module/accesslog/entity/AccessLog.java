package cn.tomoya.module.accesslog.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.security.entity.Role;
import cn.tomoya.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya. Copyright (c) 2016, All Rights Reserved. http://tomoya.cn
 */
@Entity
@Table(name = "t_access_log")
public class AccessLog extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 100234098159096559L;

	@Id
	@GeneratedValue
	private int id;

	private String ip;
	private Date accessDate;
	private String resUri;
	private String param;
	private int costTime;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getResUri() {
		return resUri;
	}

	public void setResUri(String resUri) {
		this.resUri = resUri;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
