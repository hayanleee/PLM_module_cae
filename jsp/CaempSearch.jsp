<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/cmm/grid/dataTables-header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

<title>해석물성 조회</title>

<script type="text/javaScript">
jQuery(document).ready(function() {
    setDataGrid();              
});

// DataGrid 셋팅
function setDataGrid(){
	var table = jQuery('#caempGrid').on( 'processing.dt', function ( e, settings, processing ) {
		processing ? jLoding('show') : jLoding('hide');
    }).DataTable( { 
		ajax: {    
		    url: "<c:url value='/com/yura/cae/selectCaempSearch.do'/>",
		    dataSrc: function ( json ) {
                return json.data;
		    },
		    type: "POST",			/* 전송방식 */
            data: function ( d ) {	/* Ajax 요청시 서버에 추가로 파라미터 전달 */
            	var fields = {};
				jQuery('#listForm input, #listForm select').each(function() {
					var _str = jQuery(this).val() == null ? '' : jQuery(this).val();
			    	fields[this.name] = _str.toString();
				});
				jQuery.extend(d, fields);
            	return d;			
            }        
		},
        serverSide: true,			/* 서버단에서 처리 */
        deferRender: true,			/* 대용량 데이타 화면출력시 속도 개선 옵션 */
		columns: [
			{ data: 'oid'},
			{ data: 'field'},
			{ data: 'open'},
			{ data: 'title'},			
			{ data: 'reghumid'},			
			{ data: 'regdate'}
		],
		columnDefs: [    
		    {
  			    defaultContent: "",
		        targets: 6,
	            searchable: false,
	            orderable: false,
	            width: '10%',
	            className: 'dt-body-center',                 
	            render: function (data, type, full, meta){
	            		return '<button type="button" class="btn btn-info" onclick="caempDetail(\'update\', \''+full.oid+'\',)" >상세보기</button>';
	            }
	        },
	        {
	        	"targets": [ 0 ],
	        	"visible": false
	        }
		],            
		searching: false,			/* 필터검색 여부 */
		language: language_locale,             
		scrollX: true,
		scrollY: '40vh',
	    fnInitComplete : function() {
	    	jQuery("#caempGrid tbody tr").css('cursor', 'pointer');
	    	jLoding('hide');
		}
	});
	
	//버튼 셋팅
	_buttonSetting(table);
}

// DataGrid용 버튼 세팅
function _buttonSetting(_table){
	new jQuery.fn.dataTable.Buttons( _table, {
	    name: 'commands',
	    buttons: [
	    	{ 
        	 	 text: "<span class='glyphicon glyphicon-refresh'></span>&nbsp;초기화"
     		   , action: function ( e, dt, node, config ) {
     				jQuery("#listForm").each(function() {  
     					this.reset();
     				});
     				var dt = jQuery('#caempGrid').DataTable();
     				dt.draw(1);
               	}
     			, className : "btn btn-info"
	        },
	        { 
	        	  text: "<span class='glyphicon glyphicon-search'></span>&nbsp;검색"
        		, action: function ( e, dt, node, config ) {
        			var dt = jQuery('#caempGrid').DataTable();
        	    	dt.draw(1);
                }
                , className: "btn btn-info"
	        },
	        { 
	        	  text: "<span class='glyphicon glyphicon-plus'></span>&nbsp;등록"
	      		, action: function ( e, dt, node, config ) {
	      			caempDetail('insert');
	            }
	            , className: "btn btn-info chkRC"
	        }, 
	    	{ 
	        	extend: 'excel'
	        	, text: "<span class='glyphicon glyphicon-download-alt'></span>&nbsp;엑셀"
	        	, className: "btn btn-info chkRR"
	        }
        ]
	});
	_table.buttons().containers().appendTo('#btnArea');
	
	//버튼 권한 설정시 버튼에 chkRC(등록), chkRM(수정), chkRD(삭제),chkRR(엑셀다운) class를 추가하여 적용시킴.
	chkBtnAuthor();	
}

// Data 등록/수정
function caempDetail(etcKind, oid){
	jQuery.ajax({
		url :"<c:url value='/com/yura/cae/selectCaempSearchDetail.do'/>",
		data: {
			etcKind : etcKind,
			oid : oid
		},
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		success : function (data){
			if(data == 'fail'){
				resultMsg(data);
			} else {
				jQuery('div#contents').html(data);
			}
		},
		error : function(xhr, status, error){
			resultMsg("fail");
		}
	});
}

jQuery('input').keyup(function(e) {
    if (e.keyCode == 13) {
		var dt = jQuery('#caempGrid').DataTable();
		dt.draw(1);
    }
});

// 캘린더 (등록일)
jQuery("#listForm [name='regdate']").datetimepicker({
  	viewMode: 'days',
  	format: 'YYYY-MM-DD',
  	locale:'ko',
  	showTodayButton: true,
  	showClose: true,
  	showClear: true
});

</script>
</head>

<body class="nav-md">
<form:form commandName="caeVO" id="listForm" name="listForm" onsubmit="return false;" cssClass="form-horizontal form-label-left input_mask">
<div class="x_panel" style="min-height:calc(100vh - 100px);">
    <div class="x_title alert alert-success">
    	<h3>&nbsp;해석물성 리스트</h3>
 	</div>
 	<div class="clearfix"></div>   
  	<div class="x_content">
  		<br>
		<div class="form-group">
            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label">분야</label>
					<select id="field" name="field" class="form-control">
					    <option value=''></option>          
						<c:forEach items="${sessionScope.CAMF}" var="camf">
							<option value='${camf.code}'>${camf.codeNm}</option>
						</c:forEach>      
					</select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label">공개여부</label>
                    <select id="open" name="open" class="form-control">
                        <option value=''></option>
						<c:forEach items="${sessionScope.CAMO}" var="camo">
							<option value='${camo.code}'>${camo.codeNm}</option>
						</c:forEach>      
					</select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label">제목</label>
					<input id="title" name="title" class="form-control"/>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label">담당자</label>
					<input id="reghumid" name="reghumid" class="form-control"/>
                </div>
            </div>
            <div class="col-md-2">
		        <div class="form-group has-feedback">
		          <label class="control-label">등록일</label>
		          <input id="regdate" name="regdate" class="form-control" />
			      <span class="glyphicon glyphicon-calendar form-control-feedback right" aria-hidden="true"></span>
				</div>
            </div>
        </div>
        
	  	<div class="row"></div>
	  	<div class="ln_solid"></div>
	  	
       	<!-- DataGrid 버튼 영역 -->
		<span id="btnArea"  class="flow-right"></span>		
		<div class="row"></div>
		
	  	<div>
			<!-- DataGrid 리스트 영역 -->
		  	<table id="caempGrid" class="table table-striped table-bordered table-hover" style="width:100%">
			  <thead>
			    <tr class="center aligned">
					<th>oid</th>
					<th>분야</th>
					<th>공개여부</th>
					<th>제목</th>
					<th>담당자</th>				
					<th>등록일</th>
					<th><spring:message code='info.detailView'/></th> <!-- 상세보기 -->
			  	</tr>
			  </thead>
			</table>
		</div>	
	</div>
</div>
</form:form>
</body>
</html>