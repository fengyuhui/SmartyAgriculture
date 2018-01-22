// $(function delectBrower() {
//     var browser = navigator.appName
//     var b_version = navigator.appVersion
//     var version = parseInt(b_version)

//     var userAgent = navigator.userAgent;
//     var isOpera = userAgent.indexOf("Opera") > -1;
//     var isMaxthon = userAgent.indexOf("Maxthon") > -1;
//     var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera;
//     var isFF = userAgent.indexOf("Firefox") > -1;
//     var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") < 1;
//     var isChrome = userAgent.indexOf("Chrome") > -1;
//     var isIE5 = isIE55 = isIE6 = isIE7 = isIE8 = false;

//     if (isFF) {
//         alert("当前浏览器是  Firefox");
//     }
//     if (isMaxthon) {
//         alert("当前浏览器是 傲游(webkit核心)");
//     }
//     if (isOpera) {
//         alert("当前浏览器是  Opera");
//     }
//     if (isSafari) {
//         alert("当前浏览器是  Safari");
//     }
//     if (isChrome) {
//         alert("当前浏览器是  Chrome");
//     }
//     if (isIE) {
//         alert("当前浏览器是  IE");
//     }
//     alert("浏览器内核名称：" + browser);

//     if ((browser == "Microsoft Internet Explorer") && (version >= 11)) {
//         // alert("您的浏览器已经很先进了。")
//     } else {
//         alert("请升级您的浏览器内核IE版本！");
//     }
// });


// $(document).ready(function() {

//     var brow = $.browser;

//     var bInfo = "";

//     if (brow.msie) { bInfo = "MicrosoftInternetExplorer" + brow.version; }

//     if (brow.mozilla) { bInfo = "MozillaFirefox" + brow.version; }

//     if (brow.safari) { bInfo = "AppleSafari" + brow.version; }

//     if (brow.opera) { bInfo = "Opera" + brow.version; }

//     alert(bInfo);

// });


// $(function() {
//     alert("进入浏览器的版本的测试");
//     if (browser.ie) {
//         var UA = navigator.userAgent.toLowerCase().toString();
//         //判断是不是IE内核下的非IE版本  
//         if ((UA.indexOf('360ee') > -1) || (UA.indexOf('360se') > -1) || (UA.indexOf('se') > -1) || (UA.indexOf('aoyou') > -1) ||
//             (UA.indexOf('theworld') > -1) || (UA.indexOf('worldchrome') > -1) || (UA.indexOf('greenbrowser') > -1) ||
//             (UA.indexOf('baidu') > -1) || (UA.indexOf('qqbrowser') > -1)) {
//             //是的话切换兼容模式  
//             // window.open("publicPage/point-se.aspx");
//             alert("浏览器的版本");
//         } else {
//             //不是的话，建议更换浏览器  
//             alert('建议更换成IE内核的浏览器');
//         }
//     } else {
//         //判断IE的版本型号  
//         alert("判断IE的版本");
//         if ((browser.version == 10 && browser.ie10Compat) || (browser.version == 11 && browser.ie11Compat)) {
//             // window.open("publicPage/point.aspx");
//         }
//         /*  
//          * @desc   判断浏览器的版本以及浏览器内核  
//          * @author wangyanling  
//          * @date   2014年7月4日  
//          */
//         var browser = function() {
//             var agent = navigator.userAgent.toLowerCase(),
//                 opera = window.opera,
//                 browser = {
//                     //检测当前浏览器是否为IE  
//                     ie: /(msie\s|trident.*rv:)([\w.]+)/.test(agent),
//                     //检测当前浏览器是否为Opera  
//                     opera: (!!opera && opera.version),
//                     //检测当前浏览器是否是webkit内核的浏览器  
//                     webkit: (agent.indexOf(' applewebkit/') > -1),
//                     //检测当前浏览器是否是运行在mac平台下  
//                     mac: (agent.indexOf('macintosh') > -1),
//                     //检测当前浏览器是否处于“怪异模式”下  
//                     quirks: (document.compatMode == 'BackCompat')
//                 };
//             //检测当前浏览器内核是否是gecko内核  
//             browser.gecko = (navigator.product == 'Gecko' && !browser.webkit && !browser.opera && !browser.ie);
//             var version = 0;
//             // Internet Explorer 6.0+  
//             if (browser.ie) {
//                 var v1 = agent.match(/(?:msie\s([\w.]+))/);
//                 var v2 = agent.match(/(?:trident.*rv:([\w.]+))/);
//                 if (v1 && v2 && v1[1] && v2[1]) {
//                     version = Math.max(v1[1] * 1, v2[1] * 1);
//                 } else if (v1 && v1[1]) {
//                     version = v1[1] * 1;
//                 } else if (v2 && v2[1]) {
//                     version = v2[1] * 1;
//                 } else {
//                     version = 0;
//                 }
//                 //检测浏览器模式是否为 IE11 兼容模式  
//                 browser.ie11Compat = document.documentMode == 11;
//                 //检测浏览器模式是否为 IE9 兼容模式  
//                 browser.ie9Compat = document.documentMode == 9;
//                 //检测浏览器模式是否为 IE10 兼容模式  
//                 browser.ie10Compat = document.documentMode == 10;
//                 //检测浏览器是否是IE8浏览器  
//                 browser.ie8 = !!document.documentMode;
//                 //检测浏览器模式是否为 IE8 兼容模式  
//                 browser.ie8Compat = document.documentMode == 8;
//                 //检测浏览器模式是否为 IE7 兼容模式  
//                 browser.ie7Compat = ((version == 7 && !document.documentMode) || document.documentMode == 7);
//                 //检测浏览器模式是否为 IE6 模式 或者怪异模式  
//                 browser.ie6Compat = (version < 7 || browser.quirks);
//                 browser.ie9above = version > 8;
//                 browser.ie9below = version < 9;
//             }
//             // Gecko.  
//             if (browser.gecko) {
//                 var geckoRelease = agent.match(/rv:([\d\.]+)/);
//                 if (geckoRelease) {
//                     geckoRelease = geckoRelease[1].split('.');
//                     version = geckoRelease[0] * 10000 + (geckoRelease[1] || 0) * 100 + (geckoRelease[2] || 0) * 1;
//                 }
//             }
//             //检测当前浏览器是否为Chrome, 如果是，则返回Chrome的大版本号  
//             if (/chrome\/(\d+\.\d)/i.test(agent)) {
//                 browser.chrome = +RegExp['\x241'];
//             }
//             //检测当前浏览器是否为Safari, 如果是，则返回Safari的大版本号  
//             if (/(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(agent) && !/chrome/i.test(agent)) {
//                 browser.safari = +(RegExp['\x241'] || RegExp['\x242']);
//             }
//             // Opera 9.50+  
//             if (browser.opera)
//                 version = parseFloat(opera.version());
//             // WebKit 522+ (Safari 3+)  
//             if (browser.webkit)
//                 version = parseFloat(agent.match(/ applewebkit\/(\d+)/)[1]);
//             //检测当前浏览器版本号  
//             browser.version = version;
//             return browser;
//         }
//     }
// })










// var browser = {
//     versions: function() {
//         var u = navigator.userAgent,
//             app = navigator.appVersion;
//         return { //移动终端浏览器版本信息
//             trident: u.indexOf('Trident') > -1, //IE内核
//             presto: u.indexOf('Presto') > -1, //opera内核
//             webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
//             gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
//             mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
//             ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
//             android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
//             iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
//             iPad: u.indexOf('iPad') > -1, //是否iPad
//             webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
//         };
//     }(),
//     language: (navigator.browserLanguage || navigator.language).toLowerCase()
// }

// alert("语言版本: " + browser.language);
// alert(" 是否为移动终端: " + browser.versions.mobile);
// alert(" ios终端: " + browser.versions.ios);
// alert(" android终端: " + browser.versions.android);
// alert(" 是否为iPhone: " + browser.versions.iPhone);
// alert(" 是否iPad: " + browser.versions.iPad);
// alert(avigator.userAgent);


// document.writeln("语言版本: " + browser.language);
// document.writeln(" 是否为移动终端: " + browser.versions.mobile);
// document.writeln(" ios终端: " + browser.versions.ios);
// document.writeln(" android终端: " + browser.versions.android);
// document.writeln(" 是否为iPhone: " + browser.versions.iPhone);
// document.writeln(" 是否iPad: " + browser.versions.iPad);
// document.writeln(navigator.userAgent);
