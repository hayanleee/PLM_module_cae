package com.yura.cae.service;

import java.util.List;
import java.util.Map;

import com.yura.bm.service.BmVO;
import com.yura.cae.service.CaeVO;

import java.util.HashMap;

/**
 * cae VO 서비스 인터페이스 클래스를 정의한다.
 */
public interface CaeService {
	/**
     * caemp 리스트를 조회한다.
     * @param parmVO
     * @return List
     * @throws Exception
     */
    public List<?> selectCaempSearch(HashMap<String, Object> paramMap) throws Exception;

	/**
     * caemp 리스트 총 갯수를 조회한다.
     * @param parmVO
     * @return int
     */
    public int selectCaempSearchTotCnt(HashMap<String, Object> paramMap) throws Exception;

	/**
     * caemp 상세항목을 조회한다.
     * @param parmMap
     * @return CaeVO
     * @throws Exception
     */
    public CaeVO selectCaempSearchDetail(CaeVO paramVO) throws Exception;

    /**
     * caemp 항목을 삭제한다.
     * @param paramMap
     * @throws Exception
     */
    public void deleteCaempInfo(HashMap<String,Object> parmMap) throws Exception;

    /**
     * 코드 제너레이션 샘플 데이터 중복체크를 한다.
     * @param parmVO
     * @return int
     * @throws Exception
     */
    public int selectDupleChk(CaeVO parmVO) throws Exception;

    /**
     * caemp 항목을 등록한다.
     * @param paramMap
     * @throws Exception
     */
    public void insertCaempInfo(CaeVO parmVO) throws Exception;

    /**
     * caemp 항목을 수정한다.
     * @param paramMap
     * @throws Exception
     */
    public void updateCaempInfo(CaeVO parmVO) throws Exception;

    /**
     * caedb 리스트를 조회한다.
     * @param parmVO
     * @return List
     * @throws Exception
     */
    public List<?> selectCaedbSearch(HashMap<String, Object> paramMap) throws Exception;

	/**
     * caedb 리스트 총 갯수를 조회한다.
     * @param parmVO
     * @return int
     */
    public int selectCaedbSearchTotCnt(HashMap<String, Object> paramMap) throws Exception;

    /**
     * caedb 상세항목을 조회한다.
     * @param parmMap
     * @return HashMap
     * @throws Exception
     */
    public CaeVO selectCaedbSearchDetail(CaeVO paramVO) throws Exception;

    /**
     * caedb 항목을 삭제한다.
     * @param paramMap
     * @throws Exception
     */
    public void deleteCaedbInfo(HashMap<String,Object> parmMap) throws Exception;

    /**
     * caedb 항목을 등록한다.
     * @param paramMap
     * @throws Exception
     */
    public void insertCaedbInfo(CaeVO parmVO) throws Exception;

    /**
     * caedb 항목을 수정한다.
     * @param paramMap
     * @throws Exception
     */
    public void updateCaedbInfo(CaeVO parmVO) throws Exception;

}