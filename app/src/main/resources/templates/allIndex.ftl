<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset="utf-8">
        <title>学科资源信息平台</title>
    <#include "include/head.ftl" />
    </head>
    <body>
        <div id="layout">

            <#--<div id="header">
                <div class="headerNav">
                    <div>
                        <span style="float: left;padding:15px 15px;font-size: 200%;color: white">教学资源管理平台<#if (roleId<2)>(教师专区)<#else>(学生专区)</#if></span>
                    </div>
                </div>
            </div>-->
            <#include "include/top-menu.ftl"/>
            <#include "include/left.ftl"/>
            <#include "include/main.ftl"/>
            <#include "include/foot.ftl"/>
        </div>
    </body>
</html>
