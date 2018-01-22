
  var search = location.search.substring(1);
	
		  var s = search.split('&'),
		          kv;
		  var result = {};
		  for(var i = 0, len = s.length; i < len; i++){
		      kv = s[i].split('=');
		      result[kv[0]] = decodeURIComponent(kv[1]);
		  }
		        WKIT.init({
		        	  width: 300,
                height: 500,
		            uid: '中远农庄',//需要登录的用户nick
		            appkey: '23372284',//申请的appkey
		            credential:'123456',//需要登录的用户的密码
		            touid: 'text',// 需要聊天的用户nick
		            sendMsgToCustomService:true,//使用客服模式即给淘宝/千牛客服发送消息, 默认：false
		                                        //使用sendMsgToCustomService前, 需要先去百川控制台设置下E客服, 绑定E客服账号, touid传入在控制台绑定的E客服账号
		            timeout:5000,
		           
		            theme: 'red', //主题名称
		            msgBgColor: '#2db769',//消息背景颜色；例如：#f60, #dc2a2e
		            msgColor: 'orange',//消息字体颜色；例如：#fff, #eaeaea
		            sendBtn:true,
		            title: '客服',
		            logo:'images/test kefu.jpg',//左侧栏顶部的logo，移动端无效
		            toAvatar:'images/login-left.jpg',//聊天对象的头像
		            avatar:'images/test-user-head.png',//登录用户的头像
		            autoMsg: '这是默认用户打开页面给客服发的第一条消息',
		            autoMsgType: 0,
		            sendBtn:true,
		            pluginUrl: 'plugin.html',// 左侧自定义区域url，移动端设置无效，若不传则左侧隐藏
		            customUrl: 'costom.html',//右侧自定义区域url，若不传则右侧隐藏
		            onMsgReceived: function(data){//收到消息后的回调
		                console.log(data);
		            },
		            onMsgSent: function(data){//消息发送后的回调
		                console.log(data);
		            },      
		            //titleBar: true,
		            onBack: function(){//移动端点击返回的回调
		              console.log('back');
		            },
		            onUploaderError: function(error){//上传图片失败后回调
		                console.log(error);
		            },
		             success: function(data){
						       // {code: 1000, resultText: 'SUCCESS'}
						       console.log('login success', data);
						    },
		       
		        });
		             WKIT.sendMsg({
					        msgType: 0,
					        msg: '消息'
 						   });
