<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bList</title>
</head>
<body>
	<div align="center" >
		<h1>전체 게시글 리스트</h1>
		<table style="width:700px" border="1">
			<tr>
				<th width="40px">번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach var="boardDto" items="${boardList }">
			<tr>
				<td>${boardDto.num }</td>
				<td><a href="${pageContext.request.contextPath }/board/boardInfo?num=${boardDto.num }">${boardDto.subject }</a></td>
				<td>${boardDto.writer}</td>
				<td><fmt:formatDate value="${boardDto.regDate}" pattern="yyyy-MM-dd"/></td>
				<td>${boardDto.readCount}</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right">
					<input type="button" onclick="location.href='boardWrite'" value="글쓰기">
				</td>
			</tr>
		</table>
	</div>
</body>
</html>





