<#macro html page_title page_tab="">
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name='TTUNION_verify' content='f302d44a917c2b94774ad2d730fa0f48'>
${weibometa!}
  <title>${page_title!siteTitle}</title>
  <link rel="icon" href="${baseUrlC!}/static/images/favicon.ico">

  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.css"/>
<#--<link rel="stylesheet/less" href="/static/bootstrap/css/less/bootstrap.less">-->
<#--<script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>-->
  <link rel="stylesheet" href="/static/bootstrap/css/github.css">
  <link rel="stylesheet" href="/static/bootstrap/css/pybbs.css">
  <script src="//cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/layer/2.4/layer.min.js"></script>
</head>
<body>
<div class="wrapper">
  <#include "./header.ftl">
  <@header page_tab=page_tab/>
  <div class="container">
    <#nested />
  </div>
</div>
  <#include "./footer.ftl">
  <@footer/>
<script src="/static/bootstrap/js/pybbs.js"></script>
</body>
</html>
</#macro>