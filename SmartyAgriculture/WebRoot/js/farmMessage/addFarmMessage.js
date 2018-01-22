/**
	* 提交农场信息的代码
	*/
	function addProduct(){
		var name=document.getElementsByName("messageName").value;
		var currentMessage=document.getElementsByName("currentMessage").value;
		var topOrNot=0;
		var messageType=2;
		$.ajax({ 
            type:"POST", 
            url:"farmMessage/status",
	    	dataType : "json",
			contentType : "application/json",
			//data : JSON.stringify(schemeMap),
			success: function(data){  
			   alert("添加成功！");
			  // window.location.reload();
            },  
            error: function()   
            {  
                alert("添加失败！");
               // window.location.reload();
            } 
			});
	}
	
	//建立一個可存取到該file的url
	function getObjectURL(file) {
		var url = null;
		if (window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}
	/**
	*隐藏inputfile
	*/
	function onmouseoutHide(){
		$(this).parent().next().next().hide();
	}
	/**
	*显示inputfile
	*/
	function onmouseoutShow(){
		$(this).parent().next().next().show();
	}
	