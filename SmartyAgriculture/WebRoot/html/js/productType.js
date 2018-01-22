
function getProductTYPE(id) {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        data: {
            parentId: id,
        },
        dataType: "json",
        success: function (data) {
            var str = JSON.stringify(data['content']);
            sessionStorage.setItem("productList", str);
            window.location.href = "productType.html";
        },
    });
}
/**
 * 页面皆在的时候加载的函数
 */
function ptoductTypeOnload() {
    var productList = sessionStorage.getItem("productList");
    //重新转换为对象
    productList = JSON.parse(productList);
    jQuery.each(productList, function (i, val) {
        var string = " <li class=' isotope-item product_type_li' data-categories='vegetable'>"
            + '   <div class="item-wrapp">'
            + ' <div onclick="getSubChildType('+val['id']+')"> '
            + '<img src="'
            + val['picture'] + '" alt="" width="80px" height="50px" />'
            + '</div>'
            + '<div>'
            + ' <ul>'
            + ' <div >'
            + "<li class='product_desc_li' >"
            + '<div class="product_title_div"> <a href="javascript:void(0);" class="product_title_a" title="'+val['name']+'">'
            + val['name'] + '</a>'
            + '</div>'
            + '</li >'
            // +"<li class='product_desc_li li_border_top' >"
            // + '<div class="product_title_div div_margin_product_title ">'
            // + '<span class="product_desc_span">'
            // + val['title']
            // + '</span >'
            // + '</div>'
            // + '</li >'
            // + "<li class='product_desc_li li_border_top' >"
            // + '<div class="product_title_div div_margin_product_title">'
            // + '<span>' + val['buyNum'] + '人已购买</span>'
            // + '<span class="price">￥ ' + val['price'] + '</span>'
            // + '</div>'
            // + '</li>'
            + '</div >'
            + '</ul >'
            + '</div >'
            + '</div >'
            + '</li >';
        jQuery("#portfolio-container").append(string);
    });
}
/**
 * 查看商品二级分类
 */
function getSubChildType(id){
        jQuery.ajax({
        type: "POST",
        url:"http://127.0.0.1:8e080/SmartyAgriculture/interface/goods/getGoodsList",
        data: {
            typeIdChild: id,
            pageNum:1,
            pageSize:12,
        },
        dataType: "json",
        success: function (data) {
            console.log(data);
            var str = JSON.stringify(data['content']['goodsList']);
            console.log(str);
            sessionStorage.setItem("productListLast", str);
            window.location.href = "productTypeLast.html";
        },
    });
}
/**
 * 商品列表页面加载时执行
 */
function ptoductTypeLastOnload(){
    var productList = sessionStorage.getItem("productListLast");
     productList = JSON.parse(productList);
        jQuery.each(productList, function (i, val) {
        var string = " <li class=' isotope-item product_type_li' data-categories='vegetable'>"
            + '   <div class="item-wrapp">'
            + ' <div onclick="getSubChildType('+val['id']+')"> '
            + '<img src="'
            + val['picture'] + '" alt="" width="80px" height="50px" />'
            + '</div>'
            + '<div>'
            + ' <ul>'
            + ' <div >'
            + "<li class='product_desc_li' >"
            + '<div class="product_title_div"> <a href="javascript:void(0);" class="product_title_a" title="'+val['name']+'">'
            + val['name'] + '</a>'
            + '</div>'
            + '</li >'
            +"<li class='product_desc_li li_border_top' >"
            + '<div class="product_title_div div_margin_product_title ">'
            + '<span class="product_desc_span">'
            + val['title']
            + '</span >'
            + '</div>'
            + '</li >'
            + "<li class='product_desc_li li_border_top' >"
            + '<div class="product_title_div div_margin_product_title">'
            + '<span>' + val['buyNum'] + '人已购买</span>'
            + '<span class="price">￥ ' + val['price'] + '</span>'
            + '</div>'
            + '</li>'
            + '</div >'
            + '</ul >'
            + '</div >'
            + '</div >'
            + '</li >';
        jQuery("#portfolio-container").append(string);
    });
}
