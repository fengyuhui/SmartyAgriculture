// 初始化插件

// 全局保存当前选中窗口
var g_iWndIndex = 0; //可以不用设置这个变量，有窗口参数的接口中，不用传值，开发包会默认使用当前选择窗口
$(function() {
    // 检查插件是否已经安装过
    if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
        alert("您还未安装过插件");
        return;
    }

    // 初始化插件参数及插入插件
    WebVideoCtrl.I_InitPlugin(550, 350, {
        iWndowType: 1,
        cbSelWnd: function(xmlDoc) {
            g_iWndIndex = $(xmlDoc).find("SelectWnd").eq(0).text();
            // var szInfo = "当前选择的窗口编号：" + g_iWndIndex;
            // showCBInfo(szInfo);
        }
    });
    WebVideoCtrl.I_InsertOBJECTPlugin("box");

    // 检查插件是否最新
    if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
        alert("检测到新的插件版本，双击开发包目录里的WebComponents.exe升级！");
        return;
    }

    // 窗口事件绑定
    $(window).bind({
        resize: function() {
            var $Restart = $("#restartDiv");
            if ($Restart.length > 0) {
                var oSize = getWindowSize();
                $Restart.css({
                    width: oSize.width + "px",
                    height: oSize.height + "px"
                });
            }
        }
    });

    //初始化日期时间
    // var szCurTime = dateFormat(new Date(), "yyyy-MM-dd");
    // $("#starttime").val(szCurTime + " 00:00:00");
    // $("#endtime").val(szCurTime + " 23:59:59");
});


// 显示操作信息
function showOPInfo(szInfo) {
    szInfo = "<div>" + dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss") + " " + szInfo + "</div>";
    $("#opinfo").html(szInfo + $("#opinfo").html());
}

// 显示回调信息
function showCBInfo(szInfo) {
    szInfo = "<div>" + dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss") + " " + szInfo + "</div>";
    $("#cbinfo").html(szInfo + $("#cbinfo").html());
}

// 格式化时间
function dateFormat(oDate, fmt) {
    var o = {
        "M+": oDate.getMonth() + 1, //月份
        "d+": oDate.getDate(), //日
        "h+": oDate.getHours(), //小时
        "m+": oDate.getMinutes(), //分
        "s+": oDate.getSeconds(), //秒
        "q+": Math.floor((oDate.getMonth() + 3) / 3), //季度
        "S": oDate.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (oDate.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

// 获取窗口尺寸
function getWindowSize() {
    var nWidth = $(this).width() + $(this).scrollLeft(),
        nHeight = $(this).height() + $(this).scrollTop();

    return { width: nWidth, height: nHeight };
}

// 打开选择框  参数id:相应input组件的id..........
function clickOpenFileDlg(id, iType) {
    //iType--> 0：文件夹  1：文件
    var szDirPath = WebVideoCtrl.I_OpenFileDlg(iType);

    if (szDirPath != -1 && szDirPath != "" && szDirPath != null) {
        $("#" + id).val(szDirPath);
    }
}

// 获取本地参数{获取button}................................................................
function clickGetLocalCfg() {
    var xmlDoc = WebVideoCtrl.I_GetLocalCfg(); //返回插件的本地配置参数（xml格式）

    $("#netsPreach").val($(xmlDoc).find("BuffNumberType").eq(0).text()); //播放性能
    $("#wndSize").val($(xmlDoc).find("PlayWndType").eq(0).text()); //图像尺寸
    $("#rulesInfo").val($(xmlDoc).find("IVSMode").eq(0).text()); //规则信息

    $("#captureFileFormat").val($(xmlDoc).find("CaptureFileFormat").eq(0).text()); //抓图文件格式
    $("#packSize").val($(xmlDoc).find("PackgeSize").eq(0).text()); //录像文件打包大小
    $("#protocolType").val($(xmlDoc).find("ProtocolType").eq(0).text()); //协议类型

    $("#recordPath").val($(xmlDoc).find("RecordPath").eq(0).text()); //录像文件保存路径
    $("#downloadPath").val($(xmlDoc).find("DownloadPath").eq(0).text()); //回放下载保存路径
    $("#previewPicPath").val($(xmlDoc).find("CapturePath").eq(0).text()); //预览抓图保存路径
    $("#playbackPicPath").val($(xmlDoc).find("PlaybackPicPath").eq(0).text()); //回放抓图保存路径
    $("#playbackFilePath").val($(xmlDoc).find("PlaybackFilePath").eq(0).text()); //回放剪辑保存路径


    showOPInfo("本地配置获取成功！");
}

// 设置本地参数{设置button}......................................
function clickSetLocalCfg() {
    var arrXml = [],
        szInfo = "";

    arrXml.push("<LocalConfigInfo>");
    arrXml.push("<PackgeSize>" + $("#packSize").val() + "</PackgeSize>");
    arrXml.push("<PlayWndType>" + $("#wndSize").val() + "</PlayWndType>");
    arrXml.push("<BuffNumberType>" + $("#netsPreach").val() + "</BuffNumberType>");
    arrXml.push("<RecordPath>" + $("#recordPath").val() + "</RecordPath>");
    arrXml.push("<CapturePath>" + $("#previewPicPath").val() + "</CapturePath>");
    arrXml.push("<PlaybackFilePath>" + $("#playbackFilePath").val() + "</PlaybackFilePath>");
    arrXml.push("<PlaybackPicPath>" + $("#playbackPicPath").val() + "</PlaybackPicPath>");
    arrXml.push("<DownloadPath>" + $("#downloadPath").val() + "</DownloadPath>");
    arrXml.push("<IVSMode>" + $("#rulesInfo").val() + "</IVSMode>");
    arrXml.push("<CaptureFileFormat>" + $("#captureFileFormat").val() + "</CaptureFileFormat>");
    arrXml.push("<ProtocolType>" + $("#protocolType").val() + "</ProtocolType>");
    arrXml.push("</LocalConfigInfo>");

    var iRet = WebVideoCtrl.I_SetLocalCfg(arrXml.join(""));

    if (0 == iRet) {
        szInfo = "本地配置设置成功！";
    } else {
        szInfo = "本地配置设置失败！";
    }
    showOPInfo(szInfo);
}

// Video窗口分割......
function changeVideoWindNumber(iType) {
    iType = parseInt(iType, 10); //iType转换为十进制。
    WebVideoCtrl.I_ChangeWndNum(iType);
}

// 登录
function clickLogin() {
    //获取输入框的 IP,Port,Username,Password.
    var szIP = $("#loginip").val(),
        szPort = $("#port").val(),
        szUsername = $("#username").val(),
        szPassword = $("#password").val();

    if ("" == szIP || "" == szPort) {
        return;
    }

    //var iRet = WebVideoCtrl.I_Login(szIP, 1, szPort, szUsername, szPassword, {
    /*
    	@ 1:http协议 2:https协议
    */
    var iRet = WebVideoCtrl.I_Login(szIP, 1, szPort, szUsername, szPassword, {
        success: function(xmlDoc) {
            showOPInfo(szIP + " 登录成功！");
            //prepend().在被选元素的开头插入指定内容
            $("#ip").prepend("<option value='" + szIP + "'>" + szIP + "</option>");
            //setTimeout( ) 是属于 window 的 method, 但我们都是略去 window 这顶层物件名称, 这是用来设定一个时间, 时间到了, 就会执行一个指定的 method.
            //设定:1秒。
            setTimeout(function() {
                $("#ip").val(szIP);
                getChannelInfo();
            }, 1000);
        },
        error: function() {
            showOPInfo(szIP + " 登录失败！");
        }
    });

    if (-1 == iRet) {
        showOPInfo(szIP + " 已登录过！");
    }
}

// 退出
function clickLogout() {
    var szIP = $("#ip").val(),
        szInfo = "";

    if (szIP == "") {
        return;
    }

    var iRet = WebVideoCtrl.I_Logout(szIP);
    if (0 == iRet) {
        szInfo = "退出成功！";

        $("#ip option[value='" + szIP + "']").remove();
        getChannelInfo();
    } else {
        szInfo = "退出失败！";
    }
    showOPInfo(szIP + " " + szInfo);
}

// 获取设备信息
function clickGetDeviceInfo() {
    var szIP = $("#ip").val();

    if ("" == szIP) {
        return;
    }

    WebVideoCtrl.I_GetDeviceInfo(szIP, {
        success: function(xmlDoc) {
            var arrStr = [];
            arrStr.push("设备名称：" + $(xmlDoc).find("deviceName").eq(0).text() + "\r\n");
            arrStr.push("设备ID：" + $(xmlDoc).find("deviceID").eq(0).text() + "\r\n");
            arrStr.push("型号：" + $(xmlDoc).find("model").eq(0).text() + "\r\n");
            arrStr.push("设备序列号：" + $(xmlDoc).find("serialNumber").eq(0).text() + "\r\n");
            arrStr.push("MAC地址：" + $(xmlDoc).find("macAddress").eq(0).text() + "\r\n");
            arrStr.push("主控版本：" + $(xmlDoc).find("firmwareVersion").eq(0).text() + " " + $(xmlDoc).find("firmwareReleasedDate").eq(0).text() + "\r\n");
            arrStr.push("编码版本：" + $(xmlDoc).find("encoderVersion").eq(0).text() + " " + $(xmlDoc).find("encoderReleasedDate").eq(0).text() + "\r\n");

            showOPInfo(szIP + " 获取设备信息成功！");
            alert(arrStr.join(""));
        },
        error: function() {
            showOPInfo(szIP + " 获取设备信息失败！");
        }
    });
}

// 获取通道
function getChannelInfo() {
    var szIP = $("#ip").val(),
        oSel = $("#channels").empty(),
        nAnalogChannel = 0;

    if ("" == szIP) {
        return;
    }

    // 模拟通道
    WebVideoCtrl.I_GetAnalogChannelInfo(szIP, {
        async: false,
        success: function(xmlDoc) {
            var oChannels = $(xmlDoc).find("VideoInputChannel");
            nAnalogChannel = oChannels.length;

            //jquery: -->$.each():遍历。

            $.each(oChannels, function(i) {
                var id = parseInt($(this).find("id").eq(0).text(), 10),
                    name = $(this).find("name").eq(0).text();
                if ("" == name) {
                    name = "Camera " + (id < 9 ? "0" + id : id);
                }
                oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
            });
            showOPInfo(szIP + " 获取模拟通道成功！");
        },
        error: function() {
            showOPInfo(szIP + " 获取模拟通道失败！");
        }
    });
}

// 开始预览
function clickStartRealPlay() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szIP = $("#ip").val(),
        iStreamType = parseInt($("#streamtype").val(), 10),
        iChannelID = parseInt($("#channels").val(), 10),
        //bZeroChannel = $("#channels option").eq($("#channels").get(0).selectedIndex).attr("bZero") == "true" ? true : false,
        szInfo = "";

    if ("" == szIP) {
        return;
    }

    if (oWndInfo != null) { // 已经在播放了，先停止
        WebVideoCtrl.I_Stop();
    }

    var iRet = WebVideoCtrl.I_StartRealPlay(szIP, {
        iStreamType: iStreamType,
        iChannelID: iChannelID,
        //bZeroChannel: bZeroChannel    //是否播放零通道
    });

    if (0 == iRet) {
        szInfo = "开始预览成功！";
    } else {
        szInfo = "开始预览失败！";
    }

    showOPInfo(szIP + " " + szInfo);
}

// 停止预览
function clickStopRealPlay() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szInfo = "";

    if (oWndInfo != null) {
        var iRet = WebVideoCtrl.I_Stop();
        if (0 == iRet) {
            szInfo = "停止预览成功！";
        } else {
            szInfo = "停止预览失败！";
        }
        showOPInfo(oWndInfo.szIP + "    " + szInfo);
    }
}

// 打开声音。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
function clickOpenSound() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szInfo = "";

    if (oWndInfo != null) {
        var allWndInfo = WebVideoCtrl.I_GetWindowStatus();
        // 循环遍历所有窗口，如果有窗口打开了声音，先关闭
        for (var i = 0, iLen = allWndInfo.length; i < iLen; i++) {
            oWndInfo = allWndInfo[i];
            if (oWndInfo.bSound) {
                WebVideoCtrl.I_CloseSound(oWndInfo.iIndex);
                break;
            }
        }

        var iRet = WebVideoCtrl.I_OpenSound();

        if (0 == iRet) {
            szInfo = "打开声音成功！";
        } else {
            szInfo = "打开声音失败！";
        }
        showOPInfo(oWndInfo.szIP + " " + szInfo);
    }
}

// 关闭声音
function clickCloseSound() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szInfo = "";

    if (oWndInfo != null) {
        var iRet = WebVideoCtrl.I_CloseSound();
        if (0 == iRet) {
            szInfo = "关闭声音成功！";
        } else {
            szInfo = "关闭声音失败！";
        }
        showOPInfo(oWndInfo.szIP + " " + szInfo);
    }
}

// 设置音量
function clickSetVolume() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        iVolume = parseInt($("#volume").val(), 10),
        szInfo = "";

    if (oWndInfo != null) {
        var iRet = WebVideoCtrl.I_SetVolume(iVolume);
        if (0 == iRet) {
            szInfo = "音量设置成功！";
        } else {
            szInfo = "音量设置失败！";
        }
        showOPInfo(oWndInfo.szIP + " " + szInfo);
    }
}


//每3秒自动调用抓图
setInterval(function() { autoClickCapturePic() }, 3000);


//自动抓图
function autoClickCapturePic() {

    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szInfo = "";

    if (oWndInfo != null) {
        var szChannelID = $("#channels").val(),
            szPicName = oWndInfo.szIP + "_" + szChannelID + "_" + new Date().getTime(),
            iRet = WebVideoCtrl.I_CapturePic(szPicName);

        var xmlDoc = WebVideoCtrl.I_GetLocalCfg();
        var basepath = $(xmlDoc).find("CapturePath").eq(0).text(); //根路径
        var szCurTime = dateFormat(new Date(), "yyyy-MM-dd");
        var imagepath = basepath + "\\" + szCurTime + "\\" + szPicName + ".jpg";

        //alert(imagepath);

        //alert(imagepath);
        //把截图路径上传到服务器
        /* $.post("http://192.168.1.101:8080/ImgeDemo/ImageCompareServlet",{imageurl:imagepath},function(result){
			 //获得返回值？？？
 			 	alert(result);
 		 });*/

        $.get("http://192.168.1.102:8080/ImgeDemo/ImageCompareServlet", { imageurl: imagepath },
            function(data) {
                //
            });

        /*
        if (0 == iRet) {
        	szInfo = "抓图成功！";
        } else {
        	szInfo = "抓图失败！";
        }
        showOPInfo(oWndInfo.szIP + " " + szInfo);
        */
    }
}

// 抓图
function clickCapturePic() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
        szInfo = "";

    if (oWndInfo != null) {
        var szChannelID = $("#channels").val(),
            //命名的-->图片名:IP_通道_时间毫秒
            szPicName = oWndInfo.szIP + "_" + szChannelID + "_" + new Date().getTime(),
            //抓图图片格式与接口调用时传的文件名有关：如果文件名带有.bmp 后缀，则抓取 bmp 图片；如果
            //不带则是 jpg。
            //图片保存路径通过 I_GetLocalCfg()获取。 I_GetLocalCfg():获取插件的本地配置参数。
            iRet = WebVideoCtrl.I_CapturePic(szPicName); //第二个参数没传，表示操作当前选中窗口。 返回值：0---成功。-1----失败。

        if (0 == iRet) {
            szInfo = "抓图成功！";
        } else {
            szInfo = "抓图失败！";
        }
        showOPInfo(oWndInfo.szIP + " " + szInfo);
    }
}



// 获取对讲通道
function clickGetAudioInfo() {
    var szIP = $("#ip").val();

    if ("" == szIP) {
        return;
    }

    WebVideoCtrl.I_GetAudioInfo(szIP, {
        success: function(xmlDoc) {
            var oAudioChannels = $(xmlDoc).find("TwoWayAudioChannel"),
                oSel = $("#audiochannels").empty();
            $.each(oAudioChannels, function() {
                var id = $(this).find("id").eq(0).text();

                oSel.append("<option value='" + id + "'>" + id + "</option>");
            });
            showOPInfo(szIP + " 获取对讲通道成功！");
        },
        error: function() {
            showOPInfo(szIP + " 获取对讲通道失败！");
        }
    });
}

// 开始对讲
function clickStartVoiceTalk() {
    var szIP = $("#ip").val(),
        iAudioChannel = parseInt($("#audiochannels").val(), 10),
        szInfo = "";

    if ("" == szIP) {
        return;
    }

    if (isNaN(iAudioChannel)) {
        alert("请选择对讲通道！");
        return;
    }

    var iRet = WebVideoCtrl.I_StartVoiceTalk(szIP, iAudioChannel);

    if (0 == iRet) {
        szInfo = "开始对讲成功！";
    } else {
        szInfo = "开始对讲失败！";
    }
    showOPInfo(szIP + " " + szInfo);
}

// 停止对讲
function clickStopVoiceTalk() {
    var szIP = $("#ip").val(),
        iRet = WebVideoCtrl.I_StopVoiceTalk(),
        szInfo = "";

    if ("" == szIP) {
        return;
    }

    if (0 == iRet) {
        szInfo = "停止对讲成功！";
    } else {
        szInfo = "停止对讲失败！";
    }
    showOPInfo(szIP + " " + szInfo);
}


// 全屏.......................................................................................................
function tikFullScreen() {
    WebVideoCtrl.I_FullScreen(true); //true:全屏，false:退出全屏。
}




// PTZ控制 9为自动，1,2,3,4为方向PTZ
var g_bPTZAuto = false;

function mouseDownPTZControl(iPTZIndex) {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),

        bStop = false; //是否停止iPTZIndex指定的操作。

    if (oWndInfo != null) {

        g_bPTZAuto = false; // 点击其他方向，自动肯定会被关闭
        bStop = false;
    }

    WebVideoCtrl.I_PTZControl(iPTZIndex, bStop, {
        success: function(xmlDoc) {
            showOPInfo(oWndInfo.szIP + " 开启云台成功！");
        },
        error: function() {
            showOPInfo(oWndInfo.szIP + " 开启云台失败！");
        }
    });
}


// 方向PTZ停止
function mousePTZControl() {
    var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex);

    if (oWndInfo != null) {
        WebVideoCtrl.I_PTZControl(1, true, {
            success: function(xmlDoc) {
                showOPInfo(oWndInfo.szIP + " 停止云台成功！");
            },
            error: function() {
                showOPInfo(oWndInfo.szIP + " 停止云台失败！");
            }
        });
    }
}






// 重连
function reconnect(szIP) {
    WebVideoCtrl.I_Reconnect(szIP, {
        success: function(xmlDoc) {
            $("#restartDiv").remove();
        },
        error: function() {
            setTimeout(function() { reconnect(szIP); }, 5000);
        }
    });
}



// 检查插件版本
function ticCheckPluginVersion() {
    var iRet = WebVideoCtrl.I_CheckPluginVersion();
    if (0 == iRet) {
        alert("您的插件版本已经是最新的！");
    } else {
        alert("检测到新的插件版本！");
    }
}







dateFormat = function(oDate, fmt) {
    var o = {
        "M+": oDate.getMonth() + 1, //月份
        "d+": oDate.getDate(), //日
        "h+": oDate.getHours(), //小时
        "m+": oDate.getMinutes(), //分
        "s+": oDate.getSeconds(), //秒
        "q+": Math.floor((oDate.getMonth() + 3) / 3), //季度
        "S": oDate.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (oDate.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};



// 获取设备IP
function clickGetDeviceIP() {
    var iDeviceMode = parseInt($("#devicemode").val(), 10),
        szAddress = $("#serveraddress").val(),
        iPort = parseInt($("#serverport").val(), 10) || 0,
        szDeviceID = $("#deviceid").val(),
        szDeviceInfo = "";

    szDeviceInfo = WebVideoCtrl.I_GetIPInfoByMode(iDeviceMode, szAddress, iPort, szDeviceID);
    // szDeviceInfo = WebVideoCtrl.I_GetIPInfoByMode(1, szAddress ,8000, 494458167);
    //showOPInfo(szDeviceInfo);
    /*	if(szDeviceInfo)
    	{
    		showOPInfo("设备IP和端口解析成功！");

    	var arrTemp = szDeviceInfo.split("-");
    	$("#loginip").val(arrTemp[0]);
    	$("#deviceport").val(arrTemp[1]);
    	}
    	else
        {showOPInfo("设备IP和端口解析失败！");}
    	*/
    $("#loginip").val('192.168.1.103');
    $("#deviceport").val('8000');

    // if ("" == szDeviceInfo) {
    // 	showOPInfo("设备IP和端口解析失败！");
    // } else {
    // 	showOPInfo("设备IP和端口解析成功！");

    // 	var arrTemp = szDeviceInfo.split("-");
    // 	$("#loginip").val(arrTemp[0]);
    // 	$("#deviceport").val(arrTemp[1]);
    // }
}
