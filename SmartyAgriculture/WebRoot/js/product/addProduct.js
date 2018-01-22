/**
	* 提交商品的代码
	*/
	function addProduct(){
		var name=document.getElementsByName("name").value;
		var price=document.getElementsByName("price").value;
		var typeIdParent=document.getElementsByName("typeIdParent").value;
		$.ajax({ 
            type:"POST", 
            url:"product/updateGoodsStatus",
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
	* 继续添加商品图片的按钮
	*/
	function continuePicture(){
		//查看有几个元素
		var length=$(".line").length;
		if(length<9){
			var name="productPicture"+length;
			var element="<div class='line'>"
//				+"<span class='span'> <input name='' type='text' id='viewfile' onmouseout='onmouseoutHide()' class='inputstyle' />"
//				+"</span><label for='unload' onmouseover='onmouseoutShow()'class='file1'>&nbsp;浏 览</label>"							 
//			    +"<input type='file' onchange='this.style.display='none';'class='file'/></div>"
//				+"<img src=''  class='showImage'>";
					+"<input type='file' name='"+name+"'></input></div>";
				$(".br").before(element);
				return false;
		}
		else{
			alert("最多可选择9张图片")
		}
		
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
	/**
	 * 设置运费模板点击事件
	 */
	function showTracking(){
		$("#tracking").css("display","block");
		var s=["s_province","s_city"];//三个select的name
		_init_area(s);
	}
	/**
	 * 添加运费模板
	 */
	function addTracking(){
		var length=$(".province").length*1+1;
		var id1='s_province'+length;
		var id2="s_city"+length;
		var s=[id1,id2];
		var addString="<select id='"+id1+"'name='province' class='province'></select>&nbsp;&nbsp;"
			+"<select id='"+id2+ "'name='city'></select>&nbsp;&nbsp; "
			+'<input class="input-sm" type="text" id="form-field-4" placeholder="运费" name="money" />'
			+'<div class="space-2"></div><div class="help-block" id="input-size-slider">';
	   $("#addTrackingButton").before(addString);
	   _init_area(s);
	}
	/**
	*删除运费模板
	*/
	function updateTrackingModel(freightid,element){
		$.ajax({ 
            type:"POST", 
            url:"product/updateFreight/"+freightid,
	    	dataType : "json",
			contentType : "application/json",
			//data : JSON.stringify(schemeMap),
			success: function(data){  
			  console.log(data);
			  if(data['msg']=="删除成功"){
				  $(element).prev().prev().prev().remove();
				  $(element).prev().prev().remove();
				  $(element).prev().remove();
				  $(element).remove();
				  var length=$("#setedTracking_div").children("input").length;
				  if(length==0){
					  $("#setedTracking").empty();
				  }
			  }
            },  
            error: function()   
            {  
                alert("添加失败！");
            } 
			});
	}