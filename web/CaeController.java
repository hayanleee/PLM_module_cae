package com.yura.cae.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yura.bm.service.BmVO;
import com.yura.cae.service.CaeService;
import com.yura.cae.service.CaeVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.let.utl.fcc.service.EgovDateUtil;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/**
 * mbom에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹
 * 화면으로 전달을 위한 Controller를 정의한다.
 */
@Controller
public class CaeController {
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Resource(name = "CaeService")
    private CaeService caeService;

    /** 해석물성 IdGnrService */
	@Resource(name="caempIdGnrService")
	private EgovIdGnrService caempIdGnrService;

	/** 해석DB IdGnrService */
	@Resource(name="caedbIdGnrService")
	private EgovIdGnrService caedbIdGnrService;

	//EgovFileMngUtil
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    //EgovFileMngService
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    /**
     * caemp 리스트를 조회한다.
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/caempSearch.do")
    public String caempSearch(HttpServletRequest request, ModelMap model) throws Exception {

        return "/com/yura/cae/CaempSearch";
    }

    /**
	 * caemp 리스트 Data를 조회한다.
	 * @param paramMap
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/yura/cae/selectCaempSearch.do")
	public String selectCaempSearch(HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {

		//paging 처리
		paramMap.put("iDisplayStart", paramMap.get("start"));
		if("-1".equals(paramMap.get("length")))
			paramMap.put("iDisplayLength", "ALL");
		else
			paramMap.put("iDisplayLength", paramMap.get("length"));

		List<?> resultList = caeService.selectCaempSearch(paramMap);
		model.addAttribute("resultList", resultList);

		int resultCnt = caeService.selectCaempSearchTotCnt(paramMap);
		model.addAttribute("resultCnt", resultCnt);
		model.addAttribute("checkHtmlView", false);

		return "cmm/grid/resultData";
	}

	/**
     * caemp 상세항목을 조회한다.
     * @param  paramMap
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/selectCaempSearchDetail.do")
    public String selectCaempSearchDetail(
    		@ModelAttribute("caeVO") CaeVO caeVO, ModelMap model) throws Exception {
    	HashMap<String,Object>resultMap = new HashMap<String,Object>();

    	if(caeVO.getEtcKind().equals("update")) {
    		caeVO = caeService.selectCaempSearchDetail(caeVO);
    		caeVO.setEtcKind("update");
            model.addAttribute("caeVO",caeVO);
	    }

		return "/com/yura/cae/CaempSearchDetail";
    }

    /**
     * caemp 등록 및 수정한다.
     * @param  paramMap
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/modifyCaempInfo.do")
    public String modifyCaempInfo(@ModelAttribute("caeVO") CaeVO caeVO,
    		final MultipartHttpServletRequest multiRequest, HttpServletRequest request, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	String atchFileId = caeVO.getAtchfileid();
    	List<MultipartFile> files = multiRequest.getFiles("input-file");
    	List<FileVO> fileResultList = null;

	    if (atchFileId == null || atchFileId.equals("")) {
	    	if (!files.isEmpty()) {
	 	    	fileResultList = fileUtil.parseFileInf(files, "CAM_", 0, "", "Globals.fileStorePath");
	 	        atchFileId = fileMngService.insertFileInfs(fileResultList);
	 	        if(atchFileId != null && !atchFileId.equals(""))
	 	        	caeVO.setAtchfileid(atchFileId);
	 	    }
		} else {
			if (!files.isEmpty()) {
				FileVO fileVO = new FileVO();
				fileVO.setAtchFileId(atchFileId);
			    int cnt = fileMngService.getMaxFileSN(fileVO);
			    fileResultList = fileUtil.parseFileInf(files, "CAM_", cnt, atchFileId, "");
				fileMngService.updateFileInfs(fileResultList);
	 	    }
		}

        if(caeVO.getEtcKind().equals("insert")) {
        	caeVO.setOid(caempIdGnrService.getNextStringId());
        	/*if(caeService.selectDupleChk(caeVO) > 0) {
        		return "/cmm/result/resultOverlap";
        	}*/
        	caeVO.setReghumid(user.getId());
        	caeVO.setRegdate(EgovDateUtil.getCurrentDate(""));
        	caeService.insertCaempInfo(caeVO);
        } else {
        	caeVO.setModuserid(user.getId());
        	caeVO.setModdate(EgovDateUtil.getCurrentDate(""));
        	caeService.updateCaempInfo(caeVO);
        }

		return "/cmm/result/resultSuccess";
    }

	/**
     * caemp를 삭제한다.
     * @param paramMap
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/deleteCaempInfo.do")
    public String deleteCaempInfo(@RequestParam HashMap<String, Object> paramMap,
    		HttpServletRequest request, ModelMap model) throws Exception {

    	caeService.deleteCaempInfo(paramMap);

		return "/cmm/result/resultSuccess";
    }

    /**
     * caedb 리스트를 조회한다.
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/caedbSearch.do")
    public String caedbSearch(HttpServletRequest request, ModelMap model) throws Exception {

        return "/com/yura/cae/CaedbSearch";
    }

    /**
	 * caedb 리스트 Data를 조회한다.
	 * @param paramMap
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/yura/cae/selectCaedbSearch.do")
	public String selectCaedbSearch(HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramMap, ModelMap model) throws Exception {

		//paging 처리
		paramMap.put("iDisplayStart", paramMap.get("start"));
		if("-1".equals(paramMap.get("length")))
			paramMap.put("iDisplayLength", "ALL");
		else
			paramMap.put("iDisplayLength", paramMap.get("length"));

		List<?> resultList = caeService.selectCaedbSearch(paramMap);
		model.addAttribute("resultList", resultList);

		int resultCnt = caeService.selectCaedbSearchTotCnt(paramMap);
		model.addAttribute("resultCnt", resultCnt);
		model.addAttribute("checkHtmlView", false);

		return "cmm/grid/resultData";
	}

	/**
     * caedb 상세항목을 조회한다.
     * @param  paramMap
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/selectCaedbSearchDetail.do")
    public String selectCaedbSearchDetail(
    		@ModelAttribute("caeVO") CaeVO caeVO, ModelMap model) throws Exception {
    	HashMap<String,Object>resultMap = new HashMap<String,Object>();

    	if(caeVO.getEtcKind().equals("update")) {
    		caeVO = caeService.selectCaedbSearchDetail(caeVO);
    		caeVO.setEtcKind("update");
            model.addAttribute("caeVO",caeVO);
	    }

		return "/com/yura/cae/CaedbSearchDetail";
    }

    /**
     * caedb를 삭제한다.
     * @param paramMap
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/deleteCaedbInfo.do")
    public String deleteCaedbInfo(@RequestParam HashMap<String, Object> paramMap,
    		HttpServletRequest request, ModelMap model) throws Exception {

    	caeService.deleteCaedbInfo(paramMap);

		return "/cmm/result/resultSuccess";
    }

    /**
     * caedb 등록 및 수정한다.
     * @param  paramMap
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/com/yura/cae/modifyCaedbInfo.do")
    public String modifyCaedbInfo(@ModelAttribute("caeVO") CaeVO caeVO,
    		final MultipartHttpServletRequest multiRequest, HttpServletRequest request, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	String atchFileId = caeVO.getAtchfileid();
		List<MultipartFile> files = multiRequest.getFiles("input-file");
    	List<FileVO> fileResultList = null;

	    if (atchFileId == null || atchFileId.equals("")) {
	    	if (!files.isEmpty()) {
	 	    	fileResultList = fileUtil.parseFileInf(files, "CAD_", 0, "", "Globals.fileStorePath");
	 	        atchFileId = fileMngService.insertFileInfs(fileResultList);
	 	        if(atchFileId != null && !atchFileId.equals(""))
	 	        	caeVO.setAtchfileid(atchFileId);
	 	    }
		} else {
			if (!files.isEmpty()) {
				FileVO fileVO = new FileVO();
				fileVO.setAtchFileId(atchFileId);
			    int cnt = fileMngService.getMaxFileSN(fileVO);
			    fileResultList = fileUtil.parseFileInf(files, "CAD_", cnt, atchFileId, "");
				fileMngService.updateFileInfs(fileResultList);
	 	    }
		}

        if(caeVO.getEtcKind().equals("insert")) {
        	caeVO.setOid(caedbIdGnrService.getNextStringId());
        	/*if(caeService.selectDupleChk(caeVO) > 0) {
        		return "/cmm/result/resultOverlap";
        	}*/
        	caeVO.setReghumid(user.getId());
        	caeVO.setRegdate(EgovDateUtil.getCurrentDate(""));
        	caeService.insertCaedbInfo(caeVO);
        } else {
        	caeVO.setModuserid(user.getId());
        	caeVO.setModdate(EgovDateUtil.getCurrentDate(""));
        	caeService.updateCaedbInfo(caeVO);
        }

		return "/cmm/result/resultSuccess";
    }

}