package com.cl.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 通知发送记录
 * 数据库通用操作实体类（普通增删改查）
 * @author
 * @email
 * @date 2025-03-27 15:44:15
 */
@TableName("tongzhi_record")
public class TongzhiRecordEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public TongzhiRecordEntity() {

	}

	public TongzhiRecordEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 通知ID
	 */
	private Long tongzhiId;

	/**
	 * 通知编号
	 */
	private String tongzhibianhao;

	/**
	 * 账号
	 */
	private String zhanghao;

	/**
	 * 手机
	 */
	private String shouji;

	/**
	 * 通知内容
	 */
	private String tongzhineirong;

	/**
	 * 发送时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date fasongshijian;

	/**
	 * 发送状态
	 */
	private String fasongzhuangtai;

	/**
	 * 失败原因
	 */
	private String shibaiyuanyin;

	/**
	 * 重试次数
	 */
	private Integer chongcishu;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTongzhiId() {
		return tongzhiId;
	}

	public void setTongzhiId(Long tongzhiId) {
		this.tongzhiId = tongzhiId;
	}

	public String getTongzhibianhao() {
		return tongzhibianhao;
	}

	public void setTongzhibianhao(String tongzhibianhao) {
		this.tongzhibianhao = tongzhibianhao;
	}

	public String getZhanghao() {
		return zhanghao;
	}

	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public String getShouji() {
		return shouji;
	}

	public void setShouji(String shouji) {
		this.shouji = shouji;
	}

	public String getTongzhineirong() {
		return tongzhineirong;
	}

	public void setTongzhineirong(String tongzhineirong) {
		this.tongzhineirong = tongzhineirong;
	}

	public Date getFasongshijian() {
		return fasongshijian;
	}

	public void setFasongshijian(Date fasongshijian) {
		this.fasongshijian = fasongshijian;
	}

	public String getFasongzhuangtai() {
		return fasongzhuangtai;
	}

	public void setFasongzhuangtai(String fasongzhuangtai) {
		this.fasongzhuangtai = fasongzhuangtai;
	}

	public String getShibaiyuanyin() {
		return shibaiyuanyin;
	}

	public void setShibaiyuanyin(String shibaiyuanyin) {
		this.shibaiyuanyin = shibaiyuanyin;
	}

	public Integer getChongcishu() {
		return chongcishu;
	}

	public void setChongcishu(Integer chongcishu) {
		this.chongcishu = chongcishu;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
