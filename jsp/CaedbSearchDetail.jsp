<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="sec" 	  uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ include file="/WEB-INF/jsp/cmm/grid/dataTables-header.jsp"%>        
<%@ include file="/WEB-INF/jsp/cmm/fileUpload-header.jsp" %>         
<!DOCTYPE html>
<html>      
<head>  
<meta charset="utf-8">
<title>해석DB 상세조회</title> 
<script type="text/javaScript">

jQuery('#caeVO').parsley();

jQuery(document).ready(function() {
	fn_attch_file('<c:url value='/com/yura/cae/selectCaedbSearchDetail.do'/>',fn_callback);
	fn_init();         
});

function fn_init(){
	 if($('#etcKind').val() == 'update'){
		 fn_fileList();
	 }
}

function fn_callback(msg){
	if(msg.result === 'success'){
		resultMsg(msg.result);
	}
	
	$('#atchFileId').val($('#atchfileid').val()); 
	//첨부파일 목록조회 재호출
	fn_fileList(); 
}
    
//파일목록 조회 Div 리로드
function fn_fileList(){
	var atchFileId = $('#atchfileid').val(); 
	var url = '/cmm/fms/selectFileInfsForUpdate.do';
	$("#divFileList").load('<c:url value="/cmm/fms/selectFileInfsForUpdate.do"/>?param_atchFileId='+atchFileId+'&returnUrl='+url);
}

function fnBack(){
	jLoding('show');
	jQuery.ajax({
		type:'post',
		url : "<c:url value='/com/yura/cae/caedbSearch.do'/>",
		dataType: "html",
		success : function (data){
			if(data == 'fail'){
				resultMsg(data);
			} else {    
				jQuery('div#contents').html(data);
			}					
			jLoding('hide');
		},
		error : function(xhr, status, error){
			resultMsg("fail");
		}
	});		
}

//수정/등록
jQuery('#caeVO').on('submit',function(e) {
	 e.preventDefault();
	 if ( jQuery(this).parsley().isValid() ) {
	    swal({
            title: "<spring:message code='button.confirm'/>",
            text: "<spring:message code='common.save.msg'/>",
            type: "info",
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true,
            confirmButtonText: "<spring:message code='button.confirm'/>",
            cancelButtonText: "<spring:message code='button.reset'/>",
        },
        function(){
			jQuery('#caeVO').ajaxSubmit({
				url :"<c:url value='/com/yura/cae/modifyCaedbInfo.do'/>",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function (data){
					resultMsg(data);
					fnBack();
				},
				error : function(xhr, status, error){
					resultMsg("fail");
				}
			});
            var close = window.swal.close;
            window.swal.close = function() {
                close();
                window.onkeydown = null;
            };			     
        });		 
	}
});

// 삭제
function deleteCaedb() {
	swal({
        title: "<spring:message code='button.confirm'/>",
        text: "<spring:message code='common.delete.msg'/>",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
        confirmButtonText: "<spring:message code='button.confirm'/>",
        cancelButtonText: "<spring:message code='button.reset'/>",            
    },
    function(){
 		jQuery('#caeVO').ajaxSubmit({       
 			url :"<c:url value='/com/yura/cae/deleteCaedbInfo.do'/>",
 			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
 			success : function (data){
 				resultMsg(data);
 				fnBack(); 
 			},
 			error : function(xhr, status, error){
 				resultMsg("fail");
 			}
 		}); 
        var close = window.swal.close;
        window.swal.close = function() {
            close();
            window.onkeydown = null;
        };    	 		
    });
}

</script>
</head>
<body class="nav-md"> 
<form:form commandName="caeVO" id="caeVO" name="caeVO" method="post" data-parsley-validate="validate" class="form-horizontal form-bordered">
<form:hidden path="etcKind"/>
<c:if test="${caeVO.etcKind == 'update'}">
	<input type="hidden" id="oid" name="oid" value="${caeVO.oid}"/>
	<input type="hidden" id="atchfileid" name="atchfileid" value="${caeVO.atchfileid}"/>
</c:if> 
<div class="x_panel" style="min-height:calc(100vh - 100px);">
    <div class="x_title alert alert-success">
   		<h3>&nbsp;해석DB 등록/수정</h3>
 	</div>
 	<div class="clearfix"></div>
  	<div class="x_content">
  		<div class="form-actions" align="right">
  			<button type="submit" id="passSave" class="btn btn-info chkRM"><span class='glyphicon glyphicon-floppy-save'></span>&nbsp;<spring:message code='button.save' /></button>
			<c:if test="${caeVO.etcKind == 'update'}"><button type="button" id="deleteCaedbBtn" class="btn btn-info chkRD" onclick="deleteCaedb(); return false;" ><span class='glyphicon glyphicon-trash'></span>&nbsp;<spring:message code='button.delete'/></button></c:if>
  			<button type="button" class="btn btn-info" id="closeBtn" onclick="fnBack(); return false;"><span class='glyphicon glyphicon-th-list'></span>&nbsp;목록</button>  			
  		</div>
  		<div class="form-group">    
  			<div class="col-md-12">        
  				<div class="form-group">        
  					<div class="x_title">
  						기본 정보
  					</div>
  				</div>   
  			</div>
  			<div class="row-fluid">
  				<div class="col-md-3"> 
 					<div class="short-div">
	                <div class="form-group">
	                    <label class="control-label">부품</label>
	                    <form:select path="part" class="form-control">
							<c:forEach items="${sessionScope.CADP}" var="cadp">
								<c:choose>
							 	<c:when test='${caeVO.part eq cadp.code}'>
							 		<option value='${cadp.code}' selected>${cadp.codeNm}</option>  
							 	</c:when>
								<c:otherwise>
									<option value='${cadp.code}'>${cadp.codeNm}</option>
								</c:otherwise>   
								</c:choose>                                                      
							</c:forEach>             
						</form:select>
	                </div>
	                </div>
	                <div class="short-div">
	                <div class="form-group">
	                    <label class="control-label">구분</label>
	                    <form:select path="division" class="form-control">
							<c:forEach items="${sessionScope.CADD}" var="cadd">
								<c:choose>
							 	<c:when test='${caeVO.division eq cadp.code}'>
							 		<option value='${cadd.code}' selected>${cadd.codeNm}</option>  
							 	</c:when>
								<c:otherwise>
									<option value='${cadd.code}'>${cadd.codeNm}</option>
								</c:otherwise>   
								</c:choose>                                                      
							</c:forEach>             
						</form:select>
	                </div>
	                </div>
	                <div class="short-div">
	                <div class="form-group">
	                    <label class="control-label">설계파라미터</label>
						<input id="param" name="param" class="form-control" value="${caeVO.param}"/>
	                </div>
	                </div>
	                <div class="short-div">
	                <div class="form-group">
	                    <label class="control-label">적용대상</label>
	                    <form:select path="apply" class="form-control">
							<c:forEach items="${sessionScope.CADA}" var="cada">
								<c:choose>
							 	<c:when test='${caeVO.apply eq cada.code}'>
							 		<option value='${cada.code}' selected>${cada.codeNm}</option>  
							 	</c:when>
								<c:otherwise>
									<option value='${cada.code}'>${cada.codeNm}</option>
								</c:otherwise>   
								</c:choose>                                                      
							</c:forEach>             
						</form:select>
	                </div>
	                </div>
	                <c:if test="${caeVO.etcKind == 'update'}">             
		            	<div class="short-div">
		                <div class="form-group">
		                    <label class="control-label">담당자</label>
							<input id="reghumid" name="reghumid" class="form-control" value="${caeVO.reghumid}" readOnly/>
		                </div>
		            	</div>
		            </c:if>
	            </div>
	            <div class="col-md-3">
		            <div class="short-div">
		            <div class="form-group">
			            <label class="control-label">차종</label>
						<input type="hidden" id="comCtgOid" name="comCtgOid" value="${caeVO.comCtgOid}"/>
						<div class="input-group">     
							<input type="text" class="form-control" id="comCtgTxt" name="comCtgTxt" value="${caeVO.comCtgTxt}" readOnly/>            
							<span class="input-group-btn">
							<button class="btn btn-default" type="button" id="comCtgBtn">    
							<span class="glyphicon glyphicon-search"></span>     
							</button>            
							</span>                 
						</div> 
				  	</div>
		            </div>
		            <div class="short-div">
		            <div class="form-group">
	                    <label class="control-label">분야</label>
	                    <form:select path="field" class="form-control">
							<c:forEach items="${sessionScope.CADF}" var="cadf">
								<c:choose>
							 	<c:when test='${caeVO.field eq cadf.code}'>
							 		<option value='${cadf.code}' selected>${cadf.codeNm}</option>  
							 	</c:when>
								<c:otherwise>
									<option value='${cadf.code}'>${cadf.codeNm}</option>
								</c:otherwise>   
								</c:choose>                                                      
							</c:forEach>             
						</form:select>
	                </div>
	                </div>
	                <div class="short-div">
	                <div class="form-group">
	                    <label class="control-label">제목</label>
						<input id="title" name="title" class="form-control" value="${caeVO.title}"/>
	                </div>
	                </div>
	                <div class="short-div">
	                <div class="form-group">
	                    <label class="control-label">도면번호(품번)</label>
						<input id="dno" name="dno" class="form-control" value="${caeVO.dno}"/>
	                </div>
	                </div>
	                <c:if test="${caeVO.etcKind == 'update'}">             
		            	<div class="short-div">
		                <div class="form-group">
		                    <label class="control-label">등록일</label>
							<input id="regdate" name="regdate" class="form-control" value="${caeVO.regdate}" readOnly/>
		                </div>
		            	</div>
		            </c:if>
	            </div>
      			<div class="col-md-8">                                
	 				<div class="form-group">    
						<label class="control-label">첨부파일</label>
						<div class="file-loading">
							<input id="input-file" type="file" name="input-file" multiple >		  
						</div>
						<div id="kartik-file-errors"></div>         
		       		</div>                      
		        </div>          
		        <c:if test="${caeVO.etcKind == 'update'}">           
	          	    <div class="col-md-4">                  
						<div class="form-group">    
							<label class="control-label">첨부파일</label> 
							<div id="divFileList"/>    
						</div>           
				    </div>
			    </c:if>
			</div>
    	</div>  
  	</div>    
</div>                  
</form:form>
</body>
</html>