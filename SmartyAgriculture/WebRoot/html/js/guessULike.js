function guessULike(){
    navigationJudge();
    var token=localStorage.getItem("token");
    $.ajax({
        type:"POST",
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getLikeGoodsList",
        data:{
            token:token
        },
        dataType:"JSON",
        success:function(data){
            var goodsList=JSON.stringify(data['content']);
            goodsList=JSON.parse(goodsList);
            console.log(goodsList);
            jQuery.each(goodsList,function(i,val){
                var label=Math.floor(i/4);
                var strParent='<li class="slide-'+label+'"></li>'
                var str='<div class="isotope-item"><img src="'+goodsList[i].picture
                    +'" alt="'+goodsList[i].name+'">'
                    +'<p class="item-name">'+goodsList[i].name+'</p></div>';
                if(!($('#sliderName').children('.slide-'+label))){
                    $('.slider').append(strParent);
                    $('.slide'+label).append(str);
                }else{
                    $('.slide'+label).append(str);
                }
            });
        },
        error:function(data){
            alert(data['resultDesp']);
        }
    });
}
