<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../include/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 

	<h1>/board/list.jsp</h1>

<%-- 	${boardList } --%>

전달정보 : ${msg } <hr>


	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">Bordered Table</h3>
		</div>

		<div class="box-body">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th style="width: 10px">BNO</th>
						<th>TITLE</th>
						<th>WRITER</th>
						<th>REGDATE</th>
						<th style="width: 40px">VIEWCNT</th>
					</tr>
					<c:forEach var="vo" items="${boardList }">
						<tr>
							<td>${vo.bno }</td>
							<td>${vo.title }</td>
							<td>${vo.writer }</td>
							<td>
							 <fmt:formatDate type="both" value="${vo.regdate }" pattern="yyyy-MM-dd (a)HH:mm:ss"/>
							</td>
							<td><span class="badge bg-blue">${vo.viewcnt }</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="box-footer clearfix">
			<ul class="pagination pagination-sm no-margin pull-right">
				<li><a href="#">«</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">»</a></li>
			</ul>
		</div>
	</div>

<!-- Jquery 사용 -->
<script>
	$(document).ready(function(){
		
		// * JSP페이지 구성요소의 실행 순서
		//  JAVA - HTML/CSS - JS - JQuery
		var result = "${msg}";
		
		if(result == "createOk"){
			alert(" 글쓰기 완료! 리스트 페이지로 이동! ")
		}
			
		
	});
</script>	
	
<%@ include file="../include/footer.jsp" %>