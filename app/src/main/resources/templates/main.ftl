<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf-8">
    <title>学科资源信息平台</title>
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="robots" content="" />
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="${c_static}/dwz/css/style.css" media="all" />
    <link href="${c_static}/dwz/favicon.ico" rel="icon"  type="image/x-icon" />
    <link href="${c_static}/dwz/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <!--[if IE]><link rel="stylesheet" href="css/ie.css" media="all" /><![endif]-->
</head>
<body class="login">
<section>
    <h1><strong>学科资源信息平台</strong></h1>
    <br/>
    <br/>

    <a href="${ctx}/loginIndex"><button id="teacher" class="blue">教师专区</button></a>
    <br/>
    <br/>
    <a href="${ctx}/index?role=2"><button id="student" class="blue" >学生专区</button></a>


</section>
<#--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>-->
<#--
<script>
    $(function () {
        $("#teacher").click(function () {
            $.ajax(function () {
                url:"${ctx}/loginIndex";

            });
        })
    });
</script>-->
