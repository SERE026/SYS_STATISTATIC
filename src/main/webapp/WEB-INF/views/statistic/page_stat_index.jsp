<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = "https://"+request.getServerName()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>统计平台</title>
    <meta name="decorator" content="default" />
    <!-- Tell the browser to be responsive to screen width -->
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  </head>
  <body class="skin-blue sidebar-mini">
      
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Dashboard
            <small>Control panel</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li class="active">Dashboard</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Small boxes (Stat box) -->
          <div class="row">
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-aqua">
                <div class="inner">
                  <h3>${currStat.pv}</h3>
                  <p>浏览量(PV)</p>
                </div>
                <div class="icon">
                  <i class="ion ion-bag"></i>
                </div>
              </div>
            </div><!-- ./col -->
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-green">
                <div class="inner">
                  <h3>${currStat.uv}</h3>
                  <p>访客数(UV)</p>
                </div>
                <div class="icon">
                  <i class="ion ion-stats-bars"></i>
                </div>
              </div>
            </div><!-- ./col -->
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-yellow">
                <div class="inner">
                  <h3>${currStat.ip}</h3>
                  <p>IP数</p>
                </div>
                <div class="icon">
                  <i class="ion ion-person-add"></i>
                </div>
              </div>
            </div><!-- ./col -->
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-red">
                <div class="inner">
                  <h3>${currStat.new_uv}</h3>
                  <p>新访客数</p>
                </div>
                <div class="icon">
                  <i class="ion ion-pie-graph"></i>
                </div>
              </div>
            </div><!-- ./col -->
          </div><!-- /.row -->
          <!-- Main row -->
          <div class="row">
            <!-- Left col -->
            <section class="col-lg-7 connectedSortable" style="width: 100%">
            <div class="nav-tabs-custom">
              <div class="box-body">
                <!--  
               <button  class="btn bg-purple margin">统计日期：</button>
               <input input="text" class="stat-daterange"  name="timeRange" id="timeRange" size="30"/>&nbsp;&nbsp;
               <input type="button" onclick="ok()" name="" value="确定" class="btn btn-info btn-flat"/>
				-->
              </div>
            </div>
              

              
			<div class="box">
                <div class="box-header">
                  <h3 class="box-title">页面统计</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <table id="table1">

                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
              

            </section><!-- /.Left col -->
            
          </div><!-- /.row (main row) -->

        </section><!-- /.content -->

<script type="text/javascript">
	function ok() {
		var timeRange = $("#timeRange").val();
		//alert(timeRange);
		 var url = "/stat/pageStat?timeRange="+timeRange;
	
		 $('#table1').bootstrapTable('refresh', {
	         url: url
	     });
		  
	}
	
	
	
	$('.stat-daterange').daterangepicker(
	          {
	            ranges: {
	              '今日': [moment(), moment()],
	              '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	              '最近7天': [moment().subtract(6, 'days'), moment()],
	              '最近30天': [moment().subtract(29, 'days'), moment()]
	            },
	            startDate: moment().subtract(60, 'days'),
	            endDate: moment(),
	            minDate:moment().subtract(60, 'days'),
	            maxDate:moment(),
	             locale : {
				     applyLabel: '确定',
				     cancelLabel: '取消',
				     fromLabel: 'From',
				     toLabel: 'To',
				     weekLabel: 'W',
				     format: 'YYYY-MM-DD',
				     customRangeLabel: '自定义(可选60天范围)',
				     daysOfWeek: moment.weekdaysMin(),
				     monthNames: moment.monthsShort(),
				     firstDay: moment.localeData()._week.dow,
				        monthNames: [
				            "01/",
				            "02/",
				            "03/",
				            "04/",
				            "05/",
				            "06/ ",
				            "07/",
				            "08/",
				            "09/",
				            "10/",
				            "11/",
				            "12/"
				        ],
				        firstDay: 1
				 }
	          },
	          function (start, end) {
	    		//alert("You chose: " + start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	    	 }
	 );
	$("#timeRange").val(moment().format('MM/DD/YYYY') + " - " + moment().format('MM/DD/YYYY'));
	
	var timeRange = $("#timeRange").val();
	 var url = "/stat/pageStat?timeRange="+timeRange;
	$('#table1').bootstrapTable({
	    method: 'get',
	    url: url,
	    cache: false,
	    striped: true,
	    pagination: true,
	    sidePagination: 'server',
	    //search: true, //显示搜索框
	    pageSize: 20,
	    clickToSelect: true,
	    columns: [
	    {
	        field: 'pageUrl',
	        title: '页面',
	        align: 'left',
	        valign: 'middle'
	    }, {
	        field: 'pv',
	        title: '浏览量(PV)',
	        align: 'center',
	        valign: 'middle'
	    }, {
	        field: 'uv',
	        title: '访客数(UV)',
	        align: 'left',
	        valign: 'top'
	    }, {
	        field: 'ip',
	        title: 'IP数',
	        align: 'center',
	        valign: 'middle'
	    }]
	});
	</script>
  </body>
</html>


