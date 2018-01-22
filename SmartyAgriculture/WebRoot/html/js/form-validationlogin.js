$(document).ready(function(){
		//����һ��*
		$(".form-validation").each(function(){
			var hdw = $("<strong class='reda'>*</strong>");
			$(this).parent().append(hdw);
			});
			//end
		$("form :input").blur(function(){
			$(this).parent().find(".a2").remove();
			//�ж�		
			if ($(this).is("#phone")){
				if (this.value=="" || isNaN($(this).val()) || this.value.length < 11 ){
					var hdw1 = $("<span class='a2 error'></span>");
					$(this).parent().append(hdw1);
					}else{
					var hdw1 = $("<span class='a2 righta'></span>");
					$(this).parent().append(hdw1);				
				}
			}
			//end
			//�ж�		
			if ($(this).is("#pw1")){
				if (this.value==""){
					var hdw1 = $("<span class='a2 error'></span>");
					$(this).parent().append(hdw1);
					}else{
					var hdw1 = $("<span class='a2 righta'></span>");
					$(this).parent().append(hdw1);				
				}
			}
			//end	
			//checkbox 			
	});	
	//blur  end
});	
