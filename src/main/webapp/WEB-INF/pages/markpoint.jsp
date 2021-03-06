<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>3D视觉</title>
    <spring:url value="/resources/css/bootstrap.min.css" var="bsCss"/>
    <link href="${bsCss}" rel="stylesheet">
    <spring:url value="/resources/css/base.css" var="baseCss"/>
    <link rel="stylesheet" href="${baseCss}">
    <spring:url value="/resources/css/device-globe.css" var="globeCss"/>
    <link rel="stylesheet" href="${globeCss}">
</head>
<body>
<%@include file="header.jsp" %>
<div id="markpoint_main">--%>
<div id="legend">
    <div class="item">
        <a href="api/getDevices4Globe/monitor/all/2" data-toggle="collapse" data-target="#monitor"
           id="monitorTitle">监控设备
            <i class="fa fa-caret-down monitor"></i>
        </a>
    </div>
    <div class="collapse" id="monitor" aria-labelledby="monitorTitle">
        <div>
            <ul>
                <li>
                    <span class=" monitor-no-control"></span>
                    <a href="api/getDevices4Globe/monitor/accessible/2"
                       class="subItem">无控制权限</a>
                </li>
                <li>
                    <span class="monitor-with-picture"></span>
                    <a href="api/getDevices4Globe/monitor/image_available/2"
                       class="subItem">可查看监控画面</a></li>
                <li>
                    <span class="monitor-control"></span>
                    <a href="api/getDevices4Globe/monitor/controllable/2"
                       class="subItem">可获取控制权限</a>
                </li>
                <li>
                    <span class="monitor-backdoor"></span>
                    <a href="api/getDevices4Globe/monitor/implantable/2"
                       class="subItem">可植入后门</a></li>
            </ul>
        </div>
    </div>
    <div class="item">
        <a href="api/getDevices4Globe/industry_control/all/2" data-toggle="collapse" data-target="#plc"
           id="plcTitle">工控设备
            <i class="fa fa-caret-down pull-right plc"></i>
        </a>
    </div>
    <div class="collapse" id="plc">
        <div>
            <ul>
                <li>
                    <span class="plc-no-control"></span>
                    <a href="api/getDevices4Globe/industry_control/accessible/2"
                       class="subItem">无控制权限</a>
                </li>
                <li>
                    <span class="plc-with-picture"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/industry_control/image_available/2">可查看监控画面</a></li>
                <li>
                    <span class="plc-control"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/industry_control/controllable/2">可获取控制权限</a>
                </li>
                <li>
                    <span class="plc-backdoor"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/industry_control/implantable/2">可植入后门</a></li>
            </ul>
        </div>
    </div>
    <div class=" item">
        <a href="api/getDevices4Globe/security_matter/all/1" data-toggle="collapse" data-target="#secret"
           id="secretTitle">涉密设备
            <i class="fa fa-caret-down pull-right secret"></i>
        </a>
    </div>
    <div class="collapse" id="secret">
        <div>
            <ul>
                <li>
                    <span class="secret-no-control"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/security_matter/accessible/2">无控制权限</a>
                </li>
                <li>
                    <span class="secret-with-picture"></span>
                    <a class="subItem" href="api/getDevices4Globe/security_matter/image_available/2">可查看监控画面</a>
                </li>
                <li>
                    <span class="secret-control"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/security_matter/controllable/2">可获取控制权限</a>
                </li>
                <li>
                    <span class="secret-backdoor"></span>
                    <a class="subItem" href="api/getDevices4Globe/security_matter/implantable/2">可植入后门</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="item">
        <a href="api/getDevices4Globe/network_device/all/2" data-toggle="collapse" data-target="#netDevice"
           id="netWorkTitle">网络设备
            <i class="fa fa-caret-down pull-right net-device"></i>
        </a>
    </div>
    <div class="collapse" id="netDevice">
        <div>
            <ul>
                <li>
                    <span class="net-device-no-control"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/network_device/accessible/2">无控制权限</a>
                </li>
                <li>
                    <span class="net-device-with-picture"></span>
                    <a class="subItem" href="api/getDevices4Globe/network_device/image_available/2">可查看监控画面</a>
                </li>
                <li>
                    <span class="net-device-control"></span>
                    <a class="subItem"
                       href="api/getDevices4Globe/network_device/controllable/2">可获取控制权限</a>
                </li>
                <li>
                    <span class="net-device-backdoor"></span>
                    <a href="api/getDevices4Globe/network_device/implantable/2" class="subItem">可植入后门</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="markpointHolder"></div>
<%--
</div>
<%@include file="footer.jsp" %>
<spring:url value="resources/plugins/echarts-2.2.7/build/dist/echarts.js" var="echarts"/>
<script src="${echarts}"></script>
<spring:url value="resources/plugins/echarts-x/build/dist/echarts-x.js" var="echartsX"/>
<script src="${echartsX}"></script>
<spring:url value="resources/js/device-globe.js" var="globeJs"/>
<script src="${globeJs}"></script>
</body>
</html>--%>
