<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../include/header.jsp" %>
    
	<h1>/board/modify.jsp</h1>
	${resultVO }
<div class="box box-primary">
	<div class="box-header with-border">
		<h3 class="box-title">/board/read.jsp</h3>
	</div>
	
		
		<form role="form" action="" method="post">
			<%-- <input type="text" name="bno" value="${resultVO.bno }"> --%>
			<input type="hidden" name="bno" value="${param.bno }">
			<input type="hidden" name="page" value="${cri.page }">
			<input type="hidden" name="pageSize" value="${cri.pageSize }">
			
		</form>
	
	
	
		<div class="box-body">
			<div class="form-group">
				<label for="exampleInputEmail1">번 호</label> 
				<input type="text" name="title" class="form-control" 
						id="exampleInputEmail1" value="${resultVO.bno }" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">제 목</label> 
				<input type="text" name="title" class="form-control" 
						id="exampleInputEmail1" value="${resultVO.title }" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">작성자</label> 
				<input type="text" name="writer" class="form-control" 
						id="exampleInputEmail1" value="${resultVO.writer }" readonly="readonly">
			</div>
			<div class="form-group">
				<label>내 용</label>
				<textarea class="form-control" name="content" rows="3" 
							 readonly="readonly">${resultVO.content }</textarea>
			</div>
		</div>
		<div class="box-footer">
			<button type="submit" class="btn btn-danger">수정</button>
			<button type="submit" class="btn btn-warning">삭제</button>
			<button type="submit" class="btn btn-primary">리스트</button>
										<!-- 선택자 -->
		</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		
			
			// '수정' 버튼 클릭
			$(".btn-danger").click(function(){
				//alert("수정 버튼");
				// 수정페이지로 이동(+bno)
				$("form[role='form']").attr("action","/web/board/modify");
				$("form[role='form']").attr("method","GET");
				$("form[role='form']").submit();
				
			});
			
			// '삭제' 버튼 클릭
			$(".btn-warning").click(function(){
				$("form[role='form']").attr("action","/web/board/remove");
				$("form[role='form']").submit();
			});
			
			// '리스트' 버튼 클릭 시 리스트 페이지로 이동
			// '.' : 선택자 사용 시 선택자 앞에 .
			// alert("클릭!");
			$(".btn-primary").click(function(){
			//location.href='/web/board/listALL';
			//location.href='/web/board/listPage?page=${cri.page}&pageSize=${cri.pageSize}';
			location.href='/web/board/listPage?page=${cri.page}';
			
		});
		
	});
</script>

<%-- ${cri } --%>

<%@ include file="../include/footer.jsp" %>