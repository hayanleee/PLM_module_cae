package com.yura.cae.service;

import java.util.HashMap;

import egovframework.com.cmm.ComDefaultVO;

/**
 * CAE VO 클래스를 정의한다.
 */
@SuppressWarnings("serial")
public class CaeVO extends ComDefaultVO {

	/*
	 * oid
 	 */
	private String oid;

	/*
	 * 분야
 	 */
	private String field;

	/*
	 * 공개 여부
 	 */
	private String open;

	/*
	 * 제목
 	 */
	private String title;

	/*
	 * 파일첨부
 	 */
	private String atchfileid;

	/*
	 * 부품
 	 */
	private String part;

	/*
	 * 차종
 	 */
	private String comCtgOid;
	private String comCtgTxt;

	/*
	 * 구분
 	 */
	private String division;

	/*
	 * 설계파라미터
 	 */
	private String param;

	/*
	 * 적용대상
 	 */
	private String apply;

	/*
	 * 도면번호
 	 */
	private String dno;

	/*
	 * 등록자
 	 */
	private String reghumid;

	/*
	 * 등록일
 	 */
	private String regdate;

	/*
	 * 수정자
 	 */
	private String moduserid;

	/*
	 * 수정일
 	 */
	private String moddate;

	/*
	 * Map결과 정보
 	 */
	private HashMap<String,Object> resultMap;

	public String getOid() {
		return oid;
	}

	public String getField() {
		return field;
	}

	public String getOpen() {
		return open;
	}

	public String getTitle() {
		return title;
	}

	public String getAtchfileid() {
		return atchfileid;
	}

	public String getPart() {
		return part;
	}

	public String getComCtgOid() {
		return comCtgOid;
	}

	public String getComCtgTxt() {
		return comCtgTxt;
	}

	public String getDivision() {
		return division;
	}

	public String getParam() {
		return param;
	}

	public String getApply() {
		return apply;
	}

	public String getDno() {
		return dno;
	}

	public String getReghumid() {
		return reghumid;
	}

	public String getRegdate() {
		return regdate;
	}

	public String getModuserid() {
		return moduserid;
	}

	public String getModdate() {
		return moddate;
	}

	public HashMap<String,Object> getResultMap() {
		return resultMap;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAtchfileid(String atchfileid) {
		this.atchfileid = atchfileid;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public void setComCtgOid(String comCtgOid) {
		this.comCtgOid = comCtgOid;
	}

	public void setComCtgTxt(String comCtgTxt) {
		this.comCtgTxt = comCtgTxt;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public void setReghumid(String reghumid) {
		this.reghumid = reghumid;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public void setModuserid(String moduserid) {
		this.moduserid = moduserid;
	}

	public void setModdate(String moddate) {
		this.moddate = moddate;
	}

	public void setResultMap(HashMap<String,Object> resultMap) {
		this.resultMap = resultMap;
	}
}