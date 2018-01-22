/*-------------------------------------- 

Create by xiaoming 

 2016-5-27

-------------------------------------*/

$(document).ready(function(){
  $('input').iCheck({
    checkboxClass: 'icheckbox_square-green',
    radioClass: 'iradio_square-green',
    increaseArea: '20%' // optional
  });
});


function moveTofavorite(){
    alert("移动成功");
}
function deleteGoods(){
    alert("删除成功");
}
function minus(){
    var shoppingNumber=0;
    shoppingNumber=$('.shopping-number').val();
    shoppingNumber=shoppingNumber-1;
    $('.shopping-number').val(shoppingNumber);
}
function pluse(){
    var shoppingNumber=0;
    shoppingNumber=$('.shopping-number').val();
    shoppingNumber=shoppingNumber+1;
    $('.shopping-number').val(shoppingNumber);
}
