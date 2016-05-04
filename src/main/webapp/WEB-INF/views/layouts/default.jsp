<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%
   //设置无缓存
   //response.setHeader("pragma","no-cache");  
   //response.setHeader("Cache-Control","no-cache");  
   //response.setDateHeader("Expires",0);
   %>
<!doctype html>
<html>
<head>
<script type="text/javascript">

</script>
	<meta name="renderer" content="webkit">
	<link rel="shortcut icon" href="${ctx }/images/favicon.ico" type="/image/x-icon" />
	<link rel="icon" href="/images/favicon.ico" mce_href="/images/favicon.ico" type="/image/x-icon">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <title><decorator:title/></title>
    <decorator:head/>
    <jsp:include page="/WEB-INF/views/common/common.jsp" />
</head>
<body class="skin-blue sidebar-mini">
 <div class="wrapper">
	 <%@ include file="/WEB-INF/views/common/header.jsp"%>
  	<div class="content-wrapper">
     <decorator:body/>
  	</div><!-- /.content-wrapper -->
 </div><!-- /.content-wrapper -->
     <%@ include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>