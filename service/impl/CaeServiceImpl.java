package com.yura.cae.service.impl;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yura.bm.service.BmVO;
import com.yura.cae.service.CaeService;
import com.yura.cae.service.CaeVO;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.impl.FileManageDAO;
import egovframework.com.cmm.util.EgovFileTool;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

 /**
  * cae 서비스 구현클래스를 정의한다.
  */
@Service("CaeService")
public class CaeServiceImpl extends EgovAbstractServiceImpl implements CaeService {
    String storePath = EgovProperties.getProperty("Globals.fileStorePath");

    @Resource(name = "CaeDAO")
    private CaeDAO caeDAO;

	@Resource(name = "FileManageDAO")
	private FileManageDAO fileMngDAO;

	/** cae final IdGnrService
	@Resource(name="mboIdGnrService")
	private EgovIdGnrService mboIdGnrService;*/

	/**
     * caemp 리스트를 조회한다.
     * @param paramMap
     * @return List
     * @throws Exception
     */
    @Override
	public List<?> selectCaempSearch(HashMap<String, Object> paramMap) throws Exception {
 		return caeDAO.selectCaempSearch(paramMap);
    }

    /**
     * caemp 리스트 총 갯수를 조회한다.
     * @param parmVO
     * @return int
     * @throws Exception
     */
    @Override
	public int selectCaempSearchTotCnt(HashMap<String, Object> paramMap) throws Exception {
		return caeDAO.selectCaempSearchTotCnt(paramMap);
    }

    /**
     * caemp 상세항목을 조회한다.
     * @param parmMap
     * @return HashMap
     * @throws Exception
     */
    @Override
	public CaeVO selectCaempSearchDetail(CaeVO paramVO) throws Exception{
   	 return caeDAO.selectCaempSearchDetail(paramVO);
    }

    /**
     * caemp 항목을 삭제한다.
     * @param paramMap
     * @throws Exception
     */
    @Override
	public void deleteCaempInfo(HashMap<String,Object> paramMap) throws Exception{
    	try {
    		String atchfileid = (String)paramMap.get("atchfileid");
			if(atchfileid != null && !atchfileid.equals("")) {
				 FileVO fileVO = new FileVO();
				 fileVO.setAtchFileId(atchfileid);
				 List<FileVO> fileList = fileMngDAO.selectFileInfs(fileVO);
				 if(fileList != null && fileList.size() > 0) {
					 for(int i = 0; i < fileList.size(); i++) {
						 FileVO fvo = fileList.get(i);
						 String path = fvo.getFileStreCours()+fvo.getStreFileNm();
						 EgovFileTool.deleteFile(path);
					 }
					 fileMngDAO.deleteFileInfs(fileList);
				 }
			}
			caeDAO.deleteCaempInfo(paramMap);
     	}catch(Exception e) {
     		System.out.println("CaeDB delete error");
     	}
    }

    /**
     * caemp 데이터 중복체크를 한다.
     * @param parmVO
     * @return int
     * @throws Exception
     */
    @Override
	public int selectDupleChk(CaeVO parmVO) throws Exception {
 		return caeDAO.selectDupleChk(parmVO);
    }

    /**
     * caemp 항목을 등록한다.
     * @param paramMap
     * @throws Exception
     */
    @Override
	public void insertCaempInfo(CaeVO paramVO) throws Exception{
    	caeDAO.insertCaempInfo(paramVO);
    }

    /**
     * caemp 항목을 수정한다.
     * @param paramMap
     * @throws Exception
     */
    @Override
	public void updateCaempInfo(CaeVO paramVO) throws Exception{
    	caeDAO.updateCaempInfo(paramVO);
    }

    /**
     * caedb 리스트를 조회한다.
     * @param paramMap
     * @return List
     * @throws Exception
     */
    @Override
	public List<?> selectCaedbSearch(HashMap<String, Object> paramMap) throws Exception {
 		return caeDAO.selectCaedbSearch(paramMap);
    }

    /**
     * caedb 리스트 총 갯수를 조회한다.
     * @param parmVO
     * @return int
     * @throws Exception
     */
    @Override
	public int selectCaedbSearchTotCnt(HashMap<String, Object> paramMap) throws Exception {
		return caeDAO.selectCaedbSearchTotCnt(paramMap);
    }

    /**
     * caedb 상세항목을 조회한다.
     * @param parmMap
     * @return HashMap
     * @throws Exception
     */
    @Override
	public CaeVO selectCaedbSearchDetail(CaeVO paramVO) throws Exception{
   	 return caeDAO.selectCaedbSearchDetail(paramVO);
    }

    /**
     * caedb 항목을 삭제한다.
     * @param paramMap
     * @throws Exception
     */
    @Override
	public void deleteCaedbInfo(HashMap<String,Object> paramMap) throws Exception{
    	try {
    		String atchfileid = (String)paramMap.get("atchfileid");
			if(atchfileid != null && !atchfileid.equals("")) {
				 FileVO fileVO = new FileVO();
				 fileVO.setAtchFileId(atchfileid);
				 List<FileVO> fileList = fileMngDAO.selectFileInfs(fileVO);
				 if(fileList != null && fileList.size() > 0) {
					 for(int i = 0; i < fileList.size(); i++) {
						 FileVO fvo = fileList.get(i);
						 String path = fvo.getFileStreCours()+fvo.getStreFileNm();
						 EgovFileTool.deleteFile(path);
					 }
					 fileMngDAO.deleteFileInfs(fileList);
				 }
			}
			caeDAO.deleteCaedbInfo(paramMap);
     	}catch(Exception e) {
     		System.out.println("CaeDB delete error");
     	}
    }

    /**
     * caedb 항목을 등록한다.
     * @param paramMap
     * @throws Exception
     */
    @Override
	public void insertCaedbInfo(CaeVO paramVO) throws Exception{
    	caeDAO.insertCaedbInfo(paramVO);
    }

    /**
     * caedb 항목을 수정한다.
     * @param paramMap
     * @throws Exception
     */
    @Override
	public void updateCaedbInfo(CaeVO paramVO) throws Exception{
    	caeDAO.updateCaedbInfo(paramVO);
    }

}