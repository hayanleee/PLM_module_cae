package com.yura.cae.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.yura.bm.service.BmVO;
import com.yura.cae.service.CaeVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

 /**
  * Cae 데이터 접근 클래스를 정의한다.
  */
@Repository("CaeDAO")
public class CaeDAO extends EgovComAbstractDAO {
	/**
     * caemp 리스트를 조회한다.
     * @param paramMap
     * @return List
     * @throws Exception
     */
    public List<?> selectCaempSearch(HashMap<String, Object> paramMap) throws Exception {
		return selectList("CaeDAO.selectCaempSearch", paramMap);
    }

    /**
     * caemp 리스트 총 갯수를 조회한다.
     * @param paramMap
     * @return int
     * @throws Exception
     */
    public int selectCaempSearchTotCnt(HashMap<String, Object> paramMap) throws Exception {
		return (Integer) selectOne("CaeDAO.selectCaempSearchTotCnt", paramMap);
    }

    /**
     * caemp 상세항목을 조회한다.
     * @param parmMap
     * @return CaeVO
     * @throws Exception
     */
    public CaeVO selectCaempSearchDetail(CaeVO parmVO) throws Exception{
   	 return (CaeVO) selectOne("CaeDAO.selectCaempSearchDetail", parmVO);
    }

    /**
     * caemp 항목을 삭제한다.
     * @param paramMap
     * @throws Exception
     */
    public void deleteCaempInfo(HashMap<String,Object> parmMap) throws Exception{
   	 	delete("CaeDAO.deleteCaempInfo", parmMap);
    }

    /**
     * 코드 제너레이션 샘플 데이터 중복체크를 한다.
     * @param parmVO
     * @return int
     * @throws Exception
     */
    public int selectDupleChk(CaeVO parmVO) throws Exception {
  		return (Integer) selectOne("CaeVO.selectDupleChk", parmVO);
    }

    /**
     * caemp 항목을 등록한다.
     * @param paramMap
     * @throws Exception
     */
    public void insertCaempInfo(CaeVO parmVO) throws Exception{
   	 	insert("CaeDAO.insertCaempInfo", parmVO);
    }

    /**
     * caemp 항목을 수정한다.
     * @param paramMap
     * @throws Exception
     */
    public void updateCaempInfo(CaeVO parmVO) throws Exception{
   	 	update("CaeDAO.updateCaempInfo", parmVO);
    }

    /**
     * caedb 리스트를 조회한다.
     * @param paramMap
     * @return List
     * @throws Exception
     */
    public List<?> selectCaedbSearch(HashMap<String, Object> paramMap) throws Exception {
		return selectList("CaeDAO.selectCaedbSearch", paramMap);
    }

    /**
     * caedb 리스트 총 갯수를 조회한다.
     * @param paramMap
     * @return int
     * @throws Exception
     */
    public int selectCaedbSearchTotCnt(HashMap<String, Object> paramMap) throws Exception {
		return (Integer) selectOne("CaeDAO.selectCaedbSearchTotCnt", paramMap);
    }

    /**
     * caedb 상세항목을 조회한다.
     * @param parmMap
     * @return HashMap
     * @throws Exception
     */
    public CaeVO selectCaedbSearchDetail(CaeVO parmVO) throws Exception{
   	 return (CaeVO) selectOne("CaeDAO.selectCaedbSearchDetail", parmVO);
    }

    /**
     * caedb 항목을 삭제한다.
     * @param paramMap
     * @throws Exception
     */
    public void deleteCaedbInfo(HashMap<String,Object> parmMap) throws Exception{
   	 	delete("CaeDAO.deleteCaedbInfo", parmMap);
    }

    /**
     * caedb 항목을 등록한다.
     * @param paramMap
     * @throws Exception
     */
    public void insertCaedbInfo(CaeVO parmVO) throws Exception{
   	 	insert("CaeDAO.insertCaedbInfo", parmVO);
    }

    /**
     * caedb 항목을 수정한다.
     * @param paramMap
     * @throws Exception
     */
    public void updateCaedbInfo(CaeVO parmVO) throws Exception{
   	 	update("CaeDAO.updateCaedbInfo", parmVO);
    }
}