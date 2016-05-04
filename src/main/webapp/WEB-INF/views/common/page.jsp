<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<div class="row-fluid">
	<div class="span6">
		<div class="dataTables_paginate paging_bootstrap pagination">
			<ul>
				<li class="<c:if test="${page.currentPage eq page.firstPage }">disabled</c:if>">
					<a href="javascript:void(0);" data="${page.firstPage }" class="page" ><span class="hidden-480" >第一页</span></a>
				</li>
				<li class="prev <c:if test="${page.currentPage eq page.firstPage }">disabled</c:if>">
					<a href="javascript:void(0);" data="${page.prevPage }" class="page">← <span class="hidden-480" >上一页</span></a>
				</li>
				<li class="active"><a href="javascript:void(0);" data="${page.currentPage }" class="page">${page.currentPage }</a></li>
				<li class="next <c:if test="${page.currentPage eq page.lastPage }">disabled</c:if>">
					<a href="javascript:void(0);" data="${page.nextPage }" class="page" ><span class="hidden-480">下一页</span> → </a>
				</li>
				<li class="next <c:if test="${page.currentPage eq page.lastPage }">disabled</c:if>">
					<a href="javascript:void(0);" data="${page.lastPage }" class="page"><span class="hidden-480" >最后一页</span> </a>
				</li>
			</ul>
		</div>
	</div>
</div>