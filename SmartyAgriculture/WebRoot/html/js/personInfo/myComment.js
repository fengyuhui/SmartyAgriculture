var allcomment;
///var allPages;
var idstr,levelstr;
//获取评论内容
function getAllComment(id,level){
    idstr=id;
    levelstr=level;
    console.log(idstr,levelstr);
    jQuery.ajax({
        type:"post",
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/commentApp/getCommentList",
        data:{
            goodsId:id,
            level:level,
            pageSize:3,
            pageNum:1,
        },
        dataType:"JSON",
        success: function(data){
            var allPages=data['content']['allPages'];
            loadComment(data);
            if(allPages>1){
                console.log($('#pagebar').length>0);
                if($('#pagebar').length>0)
                    getCmtPages(allPages);
                else{
                    var $pageContainer=$('<div/>',{id:"pagebar"});
                    var $pagination=$('<ul/>',{class:"pagination"});
                    $pageContainer.append($pagination);
                    $('#comment-area').after($pageContainer);
                    getCmtPages(allPages);
                }
            }  
        },
        error: function(data){
        }
    });
}
//获取下一页评论内容
function getNextComment(num){
    jQuery.ajax({
        type:"post",
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/commentApp/getCommentList",
        data:{
            goodsId:idstr,
            level:levelstr,
            pageSize:3,
            pageNum:num,
        },
        dataType:"JSON",
        success: function(data){       
            loadComment(data);    
        },
        error: function(data){
        }
    });
}
//加载每页评论
function loadComment(data){
     var flag=data['content']['allPages'];
            if(flag=='0'){
                var str='<tr class=""><td>本商品暂无评论</td></tr>';
                $('#test').html(str);
            }else{    
                var allStr="";
                    jQuery.each(data['content']['commentList'],function(i,val){
                        var picli="";
                        var pics=val['pictureList'];
                        var tmpArr=pics.split(",");
                        if(val['pictureList'].length>0){
                            picli='<div class="rv-comment-imgs"><ul class="rv-photos-thumb">';
                            for(var i=0;i<tmpArr.length;i++){
                                picli+='<li class="photo-thumb" id=""><img src="'+tmpArr[i]
                                    +'" onclick="zoomOut(this)" data-picid="'+i+'"></li>';
                            }
                            picli+='</ul><div class="rv-photo-viewer"><img></div>';
                        }
                        var stramp=new Date(val['createTime']);
                        var time=formatDate(stramp);
                        var replynum;
                        var isAnonymous=val['isAnonymous'];
                        var strUserImg;
                        if(isAnonymous==1||val['portrait']==null){
                            strUserImg="images/icon/user_null.png";
                        }else{
                            strUserImg=val['portrait'];
                                    }
                        if(val['replyList']==null){
                            replynum="";
                        }else{
                            replynum=val['replyList'];
                        }
                        //console.log(strUserImg);
                            var str='<tr><td class="col-author"><div class="rv-userInfo"><span><img src="'+strUserImg
                                +'" alt="头像" />'+val['userName']+'</span></div><div class="rv-data">'
                                +time+'</div></td><td class="col-master"><div class="rv-content"><div class="rv-content-fulltxt">'
                                +val['content']+'</div>'
                                +picli
                                +'</div></td><td class="col-meta"><ul class="parise-reply">'
                                +'<li class="rv-reply"><a href="javascript:void(0)" class="rv-reply" onclick="reply(this)"></a><em>'+replynum+'</em></li>'
                                +'</ul></td></tr>';
                            allStr+=str;
                            //$('#test').append(str);
                    });
                $('#test').html(allStr);
                
                }
}
//function getpics(data){
//        var picli="";
//        var $commentimgs=$('<div/>',{class:"rv-comment-imgs"});
//        var $ul=$('<ul/>',{class:"rv-photos-thumb"});
//        $commentimgs.append($ul);
//        var $imgviewer=$('<div/>',{class:"rv-photo-viewer;"});
//        var $img=$('<img/>');
//        $imgviewer.append($img);
//        var pics=data['pictureList'];
//        var tmpArr=pics.split(",");
//        var zoominlist="";
//        if(tmpArr.length>0){
//            for(var i=0;i<tmpArr.length;i++){
//                $imgli=$('<li/>',{class:"photo-thumb"});
//                $imgnode=$('<img/>',{src:tmpArr,onclick:"zoomOut(this)"})
//                $imgli.append($imgnode);
//                //$('.rv-photos-thumb').append(str);
//                $($ul).append($imgli);
//            }
//        }
//}
//浏览评论图片
var identy;
function zoomOut(dom){
    var pho=dom.src;
    var parent=dom.parentNode.parentNode;//获取ul节点
    var childNodes=new Array();
    childNodes=$(parent).children();//获取li节点数组
    for(var i=0;i<childNodes.length;i++){
        if(childNodes[i]!=dom.parentNode){
            $(childNodes[i]).removeClass('thumb-current');
        }
    }
    var node=dom.parentNode.parentNode.nextSibling;//获取大图div节点
    var nodeimg=node.firstChild;
    if($(dom).data("picid")!=identy){
        $(node).hide();
        $(dom.parentNode).toggleClass('thumb-current');
        nodeimg.src=pho;
        $(node).toggle(500);
        identy=$(dom).data("picid");
    }else{
        $(dom.parentNode).toggleClass('thumb-current');
        nodeimg.src=pho;
        $(node).toggle(500);
        identy=$(dom).data("picid");//获取图片识别码
    }
}
//获取评论数量
function getCmtNum(goodsid){
    //var array=new Array();
    jQuery.ajax({
        type:"post",
        async:false,
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/commentApp/getCommentNumber",
        data:{
            goodsId:goodsid,
        },
        dataType:"JSON",
        success: function(data){
            var array=new Array();
            //var allNum=array[0],picNum=array[1],goodNum=array[2],middleNum=array[3],badNum=array[4];
            array=data['content'];
            var numList={"all":array[0],"hasPic":array[1],"good":array[2],"middle":array[3],"bad":array[4]};
            localStorage.setItem("commentNumList",JSON.stringify(numList));     
        },
        error: function(data){
        }
    }); 
}
//评论分页
function getCmtPages(allpages){
    $('.pagination').jqPaginator({
        totalPages:allpages,
        visiblePages:3,
        currentPage:1,
        onPageChange:function(num,type){
            jQuery.ajax({
                type:"post",
                async:false,
                url:"http://127.0.0.1:8080/SmartyAgriculture/interface/commentApp/getCommentList",
                data:{
                    goodsId:idstr,
                    level:levelstr,
                    pageSize:3,
                    pageNum:num,
                },
                dataType:"JSON",
                success: function(data){
                    allPages=data['content']['allPages']
                    loadComment(data);
                },
                error: function(data){
                    //alert("失败");
                }
            });
        }
    });
}
function formatDate(now){   
          var   year=now.getFullYear();   
          var   month=now.getMonth()+1;   
          var   date=now.getDate();   
          var   hour=now.getHours();   
          var   minute=now.getMinutes();   
          var   second=now.getSeconds();   
          return   year+"-"+month+"-"+date+" ";   
}
