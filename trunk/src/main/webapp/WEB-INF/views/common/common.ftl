<#---->

<#macro html title="" import="">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>${title}</title>
    <script type="text/javascript" language="javascript">
        var folder = "<@com.rootPath/>";
    </script>
    <#if import??>
        <#list import?split(",") as lib>
            <#switch lib>
                <#case "jquery">
                    <@script name="js/jquery/jquery_1.9.0.js"></@script>
                    <@script name="js/jquery/jquery-ui.min.js"></@script>
                    <@script name="js/jquery/jquery.validationEngine-zh_CN.js"></@script>
                    <@script name="js/jquery/jquery.validationEngine.js"></@script>
                    <#break />
                <#case "swf">
                    <@script name="js/swfobject.js"></@script>
                    <#break />
                <#case "portal">
                	<@script name="js/portal/lib/bootstrap.js"></@script>
                    <@script name="js/portal/main.js"></@script>
                    <@style name="css/portal/bootstrap.min.css"></@style>
                    <@style name="css/portal/main.css"></@style>
                    <#break/>
                <#case "fullscreen">
                    <@style name="css/portal/fullscreen.css"></@style>
                    <@script name="js/portal/fullscreen.js"></@script>
                    <#break/>
                <#--<#case "map">-->
                <#--<script src="js/map/jsapi.js"></script>-->
                <#default>
            </#switch>
        </#list>
    </#if>
</head>
<body>
    <#nested />
    
</body>
</html>
</#macro>



<#macro swf title="" swfname="" tpl="">
    <@com.html title="${title!}" import="swf">
    <style type="text/css" media="screen">
        html, body {
            height: 100%;
        }

        body {
            margin: 0;
            padding: 0;
            overflow: auto;
            text-align: center;
            background-color: #25639c;
        }

        object:focus {
            outline: none;
        }

        #flashContent {
            top: 50%;
            width: 360px;
            margin: 0px auto;
        }

        #flashContent p {
            color: #ffffff;
        }
    </style>

        <#nested />

    <script type="text/javascript">
        var swfVersionStr = "11.5.0";
        var xiSwfUrlStr = "playerProductInstall.swf";
        var flashvars = {};
        var params = {};
        params.quality = "high";
        params.bgcolor = "#fcfffb";
        params.allowscriptaccess = "sameDomain";
        //params.allowfullscreen = "true";
        params.allowFullScreenInteractive = "true";
        params.wmode = "direct";
        var attributes = {};
        attributes.id = "${swfname}";
        attributes.name = "${swfname}";
        attributes.align = "middle";
        attributes.tpl = "${tpl!}";
        swfobject.embedSWF(
                "<@rootPath/>/flash/${swfname}.swf", "flashContent",
                "100%", "100%",
                swfVersionStr, xiSwfUrlStr,
                flashvars, params, attributes);
        swfobject.createCSS("#flashContent", "display:block;text-align:left;");
    </script>

    <div id="flashContent">
        <p>
            亲，难道你不知道看Flash要装Adobe Flash Player的么，记得安装11.5.0以上的版本哦！<br/>
            点击以下链接即可下载安装哦~<br/>安装后记得重启浏览器。
        </p>
        <script type="text/javascript">
            var axUrl = "<@rootPath/>/bin/install_flash_player_ax.exe";
            var otherUrl = "<@rootPath/>/bin/install_flash_player.exe";
            var onlineUrl = "http://get.adobe.com/cn/flashplayer/";
            var playerUrl = window.ActiveXObject ? axUrl : otherUrl;
            document.write("<a href='" + onlineUrl + "'><images src='<@rootPath/>/images/get_flash_player.gif'/> </a>");
        </script>
    </div>
    </@com.html>
</#macro>

<#---->
<#macro script name>
<script src="<@rootPath/>/${name}" type="text/javascript"></script>
</#macro>

<#macro style name>
<link href="<@rootPath/>/${name}" type="text/css" media="screen" rel="stylesheet"/>
</#macro>

<#macro rootPath>${springMacroRequestContext.getContextPath()}</#macro>