<#include "common/layout.ftl"/>
<@html page_tab="about" page_title="关于 - ${siteTitle!}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><a href="/">主页</a> / 关于</div>
      <div class="panel-body topic-detail-content">
        <p>有朋自对面来是用Java语言编写的社区（论坛）系统.</p>
        <p>特性:
        <ul>
          <li>社区兼容性（IE9+）</li>
          <li>不用session,选用cookie,为了集群方便</li>
          <li>大量使用了缓存（redis）减轻服务器压力, 集群更加方便</li>
          <li>不做本地账户,不会出现用户密码泄露问题（已支持: Github登录, 新浪微博登录）</li>
          <li>权限配置简单,轻松管理用户</li>
          <li>使用 <a href="https://github.com/lepture/editor" target="_blank">editor</a> 作为 Markdown编辑器, 书写更方便, 还支持截图粘贴上传
          </li>
          <li>使用solr来检索,速度更快,配置文件里可一键开关,方便使用</li>
        </ul>
        <p>使用者</p>
        <ul>
          <li><a href="${baseUrlC!}" target="_blank">${baseUrlC!}</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>
