<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>统计系统</title>
</head>
<body>
	<!-- 头部 -->
	<header class="main-header">
		<!-- Logo -->
		<a href="#" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini"><b>A</b>LT</span> <!-- logo for regular state and mobile devices -->
			<span class="logo-lg"><b>YCM</b>统计平台</span>
		</a>
		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top" role="navigation">
			<!-- Sidebar toggle button-->
			<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
				role="button"> <span class="sr-only">Toggle navigation</span>
			</a>
			<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
					<!-- Messages: style can be found in dropdown.less-->
					<li class="dropdown messages-menu"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="fa fa-envelope-o"></i> <span class="label label-success"></span>
					</a></li>
					<!-- Notifications: style can be found in dropdown.less -->
					<li class="dropdown notifications-menu"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="fa fa-bell-o"></i> <span class="label label-warning"></span>
					</a></li>
					<!-- Tasks: style can be found in dropdown.less -->
					<li class="dropdown tasks-menu"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="fa fa-flag-o"></i> <span class="label label-danger"></span>
					</a></li>
					<!-- User Account: style can be found in dropdown.less -->

					<!-- Control Sidebar Toggle Button -->
					<li><a href="#" data-toggle="control-sidebar"><i
							class="fa fa-gears"></i></a></li>
				</ul>
			</div>
		</nav>
	</header>
	
	<!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
      <!-- sidebar: style can be found in sidebar.less -->
      <section class="sidebar">
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <jsp:include page="/WEB-INF/views/common/menu.jsp" />
      </section>
      <!-- /.sidebar -->
    </aside>

</body>

</html>
