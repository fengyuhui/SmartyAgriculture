/*---------------------
  Created by xiaoming
  2016-7-5 
 ----------------------*/
// window.onload = function() {
function logicInitialization() {
    //alert("success");
    // table logic js start

    if (!document.getElementsByClassName) {
        document.getElementsByClassName = function(cls) {
            var ret = [];
            var els = document.getElementsByTagName('*');
            for (var i = 0, len = els.length; i < len; i++) {

                if (els[i].className.indexOf(cls + ' ') >= 0 || els[i].className.indexOf(' ' + cls + ' ') >= 0 || els[i].className.indexOf(' ' + cls) >= 0) {
                    ret.push(els[i]);
                }
            }
            return ret;
        }
    }
    //meat var start
    var tableMeat = document.getElementById('cartTableMeat'); // meat购物车表格
    var MeatcheckAllInputs = document.getElementsByClassName('MeatcheckAll') // 全选框
    var Meattr = tableMeat.children[1].rows; //行
    var MeatselectedTotal = document.getElementById('MeatselectedTotal'); //已选商品数目容器
    var MeatpriceTotal = document.getElementById('MeatpriceTotal'); //总计
    var MeatselectInputs = document.getElementsByClassName('Meatcheck'); //meat所有勾选框
    //meat var end
    //fruit var start
    var tableFruit = document.getElementById('cartTableFruit'); // fruit购物车表格
    var FruitcheckAllInputs = document.getElementsByClassName('FruitcheckAll') // 全选框
    var Fruittr = tableFruit.children[1].rows; //行
    var FruitselectedTotal = document.getElementById('FruitselectedTotal'); //已选商品数目容器
    var FruitpriceTotal = document.getElementById('FruitpriceTotal'); //总计
    var FruitselectInputs = document.getElementsByClassName('Fruitcheck'); //fruit所有勾选框
    //fruit var end
    //vegetable var start
    var tableVegetable = document.getElementById('cartTableVegetable'); // vegetable购物车表格
    var VegetablecheckAllInputs = document.getElementsByClassName('VegetablecheckAll') // 全选框
    var Vegetabletr = tableVegetable.children[1].rows; //行
    var VegetableselectedTotal = document.getElementById('VegetableselectedTotal'); //已选商品数目容器
    var VegetablepriceTotal = document.getElementById('VegetablepriceTotal'); //总计
    var VegetableselectInputs = document.getElementsByClassName('Vegetablecheck'); //Vegetable所有勾选框
    //vegetable var end
    //food var start
    var tableFood = document.getElementById('cartTableFood'); // Food购物车表格
    var FoodcheckAllInputs = document.getElementsByClassName('FoodcheckAll') // 全选框
    var Foodtr = tableFood.children[1].rows; //行
    var FoodselectedTotal = document.getElementById('FoodselectedTotal'); //已选商品数目容器
    var FoodpriceTotal = document.getElementById('FoodpriceTotal'); //总计
    var FoodselectInputs = document.getElementsByClassName('Foodcheck'); //Food所有勾选框
    //food var end
    var selectInputs = document.getElementsByClassName('check'); // 所有勾选框
    var checkAllInputs = document.getElementsByClassName('check-all') //全选框
    var selectedTotal = document.getElementById('selectedTotal'); //已选商品数目容器
    var priceTotal = document.getElementById('priceTotal'); //总计
    var deleteAll = document.getElementById('deleteAll'); // 删除全部按钮
    var selectedViewList = document.getElementById('selectedViewList'); //浮层已选商品列表容器
    var selected = document.getElementById('selected'); //已选商品
    var foot = document.getElementById('foot');


    /*----------------------------------------------------------------------
                        meat JS table start
    ---------------------------------------------------------------------*/
    // 更新总数和总价格，已选浮层
    function getMeatTotal() {
        var seleted = 0;
        var price = 0;
        var HTMLstr = '';
        for (var i = 0, len = Meattr.length; i < len; i++) {
            if (Meattr[i].getElementsByTagName('input')[0].checked) {
                Meattr[i].className = 'on';
                seleted += parseInt(Meattr[i].getElementsByTagName('input')[1].value);
                price += parseFloat(Meattr[i].cells[5].innerHTML);
                console.log(i);
                console.log(price);
                HTMLstr += '<div><img src="' + Meattr[i].getElementsByTagName('img')[0].src + '"><span class="del" index="' + i + '">取消选择</span></div>'
            } else {
                Meattr[i].className = '';
            }
        }
        MeatselectedTotal.innerHTML = seleted;
        MeatpriceTotal.innerHTML = price.toFixed(2);
        selectedViewList.innerHTML = HTMLstr;
        getTotal();
        if (seleted == 0) {
            foot.className = 'foot';
        }
    }


    // 计算单行价格
    function getSubtotal(Meattr) {
        var cells = Meattr.cells;
        var price = cells[2]; //单价
        var subtotal = cells[5]; //小计td
        var countInput = Meattr.getElementsByTagName('input')[1]; //数目input
        var span = Meattr.getElementsByTagName('span')[1]; //-号
        //写入HTML
        subtotal.innerHTML = (parseInt(countInput.value) * parseFloat(price.innerHTML)).toFixed(2);
        //如果数目只有一个，把-号去掉
        // if (countInput.value == 1) {
        //     span.innerHTML = '';
        // } else {
        //     span.innerHTML = '-';
        // }
    }

    //为每行元素添加事件
    for (var i = 0; i < Meattr.length; i++) {
        //将点击事件绑定到tr元素
        Meattr[i].onclick = function(e) {
                var e = e || window.event;
                var el = e.target || e.srcElement; //通过事件对象的target属性获取触发元素
                var cls = el.className; //触发元素的class
                var countInout = this.getElementsByTagName('input')[1]; // 数目input
                var value = parseInt(countInout.value); //数目
                //通过判断触发元素的class确定用户点击了哪个元素
                switch (cls) {
                    case 'add': //点击了加号
                        countInout.value = value + 1;
                        getSubtotal(this);
                        break;
                    case 'reduce': //点击了减号
                        if (value > 1) {
                            countInout.value = value - 1;
                            getSubtotal(this);
                        }
                        break;
                    case 'delete': //点击了删除
                        var conf = confirm('确定删除此商品吗？');
                        if (conf) {
                            this.parentNode.removeChild(this);
                        }
                        break;
                }
                getMeatTotal();
            }
            // 给数目输入框绑定keyup事件
        Meattr[i].getElementsByTagName('input')[1].onkeyup = function() {
            var val = parseInt(this.value);
            if (isNaN(val) || val <= 0) {
                val = 1;
            }
            if (this.value != val) {
                this.value = val;
            }
            getSubtotal(this.parentNode.parentNode); //更新小计
            getMeatTotal(); //更新总数
        }
    }

    //已选商品弹层中的取消选择按钮
    selectedViewList.onclick = function(e) {
        var e = e || window.event;
        var el = e.srcElement;
        if (el.className == 'del') {
            var input = Meattr[el.getAttribute('index')].getElementsByTagName('input')[0]
            input.checked = false;
            input.onclick();
        }
    }



    /*----------------------------------------------------------------------
                              meat JS table end
    ---------------------------------------------------------------------*/



    // selectedViewList.onclick = function(e) {
    //         var e = e || window.event;
    //         var el = e.srcElement;
    //         if (el.className == 'del') {
    //             var input = Meattr[el.getAttribute('index')].getElementsByTagName('input')[0]
    //             input.checked = false;
    //             input.onclick();
    //         }
    //     }
    /*----------------------------------------------------------------------
                        meat JS table end
    ---------------------------------------------------------------------*/



    /*----------------------------------------------------------------------
                        fruit JS table start
    ---------------------------------------------------------------------*/
    // 更新总数和总价格，已选浮层


    function getFruitTotal() {
        var seleted = 0;
        var price = 0;
        var HTMLstr = '';
        for (var i = 0, len = Fruittr.length; i < len; i++) {
            if (Fruittr[i].getElementsByTagName('input')[0].checked) {
                Fruittr[i].className = 'on';
                seleted += parseInt(Fruittr[i].getElementsByTagName('input')[1].value);
                price += parseFloat(Fruittr[i].cells[5].innerHTML);
                HTMLstr += '<div><img src="' + Fruittr[i].getElementsByTagName('img')[0].src + '"><span class="del" index="' + i + '">取消选择</span></div>'
            } else {
                Fruittr[i].className = '';
            }
        }
        FruitselectedTotal.innerHTML = seleted;
        FruitpriceTotal.innerHTML = price.toFixed(2);
        selectedViewList.innerHTML = HTMLstr;
        getTotal();
        if (seleted == 0) {
            foot.className = 'foot';
        }
    }


    // 计算单行价格
    function getSubtotal(Fruittr) {
        var cells = Fruittr.cells;
        var price = cells[2]; //单价
        var subtotal = cells[5]; //小计td
        var countInput = Fruittr.getElementsByTagName('input')[1]; //数目input
        var span = Fruittr.getElementsByTagName('span')[1]; //-号
        //写入HTML
        subtotal.innerHTML = (parseInt(countInput.value) * parseFloat(price.innerHTML)).toFixed(2);
        //如果数目只有一个，把-号去掉
        // if (countInput.value == 1) {
        //     span.innerHTML = '';
        // } else {
        //     span.innerHTML = '-';
        // }
    }

    //为每行元素添加事件
    for (var i = 0; i < Fruittr.length; i++) {
        //将点击事件绑定到tr元素
        Fruittr[i].onclick = function(e) {
                var e = e || window.event;
                var el = e.target || e.srcElement; //通过事件对象的target属性获取触发元素
                var cls = el.className; //触发元素的class
                var countInout = this.getElementsByTagName('input')[1]; // 数目input
                var value = parseInt(countInout.value); //数目
                //通过判断触发元素的class确定用户点击了哪个元素
                switch (cls) {
                    case 'add': //点击了加号
                        countInout.value = value + 1;
                        getSubtotal(this);
                        break;
                    case 'reduce': //点击了减号
                        if (value > 1) {
                            countInout.value = value - 1;
                            getSubtotal(this);
                        }
                        break;
                    case 'delete': //点击了删除
                        var conf = confirm('确定删除此商品吗？');
                        if (conf) {
                            this.parentNode.removeChild(this);
                        }
                        break;
                }
                getFruitTotal();
            }
            // 给数目输入框绑定keyup事件
        Fruittr[i].getElementsByTagName('input')[1].onkeyup = function() {
            var val = parseInt(this.value);
            if (isNaN(val) || val <= 0) {
                val = 1;
            }
            if (this.value != val) {
                this.value = val;
            }
            getSubtotal(this.parentNode.parentNode); //更新小计
            getFruitTotal(); //更新总数
        }
    }

    //已选商品弹层中的取消选择按钮

    selectedViewList.onclick = function(e) {
        var e = e || window.event;
        var el = e.srcElement;
        if (el.className == 'del') {
            var input = Fruittr[el.getAttribute('index')].getElementsByTagName('input')[0]
            input.checked = false;
            input.onclick();
        }
    }


    /*----------------------------------------------------------------------
                        fruit JS table end
    ---------------------------------------------------------------------*/


    /*----------------------------------------------------------------------
                        Vegetable JS table start
    ---------------------------------------------------------------------*/
    // 更新总数和总价格，已选浮层


    // selectedViewList.onclick = function(e) {
    //         var e = e || window.event;
    //         var el = e.srcElement;
    //         if (el.className == 'del') {
    //             var input = Fruittr[el.getAttribute('index')].getElementsByTagName('input')[0]
    //             input.checked = false;
    //             input.onclick();
    //         }
    //     }
    /*----------------------------------------------------------------------
                        fruit JS table end
    ---------------------------------------------------------------------*/
    /*----------------------------------------------------------------------
                        Vegetable JS table start
    ---------------------------------------------------------------------*/
    // 更新总数和总价格，已选浮层

    function getVegetableTotal() {
        var seleted = 0;
        var price = 0;
        var HTMLstr = '';
        for (var i = 0, len = Vegetabletr.length; i < len; i++) {
            if (Vegetabletr[i].getElementsByTagName('input')[0].checked) {
                Vegetabletr[i].className = 'on';
                seleted += parseInt(Vegetabletr[i].getElementsByTagName('input')[1].value);
                price += parseFloat(Vegetabletr[i].cells[5].innerHTML);
                HTMLstr += '<div><img src="' + Vegetabletr[i].getElementsByTagName('img')[0].src + '"><span class="del" index="' + i + '">取消选择</span></div>'
            } else {
                Vegetabletr[i].className = '';
            }
        }
        VegetableselectedTotal.innerHTML = seleted;
        VegetablepriceTotal.innerHTML = price.toFixed(2);
        selectedViewList.innerHTML = HTMLstr;
        getTotal();
        if (seleted == 0) {
            foot.className = 'foot';
        }
    }
    // 计算单行价格
    function getSubtotal(Vegetabletr) {
        var cells = Vegetabletr.cells;
        var price = cells[2]; //单价
        var subtotal = cells[5]; //小计td
        var countInput = Vegetabletr.getElementsByTagName('input')[1]; //数目input
        var span = Vegetabletr.getElementsByTagName('span')[1]; //-号
        //写入HTML
        subtotal.innerHTML = (parseInt(countInput.value) * parseFloat(price.innerHTML)).toFixed(2);
        //如果数目只有一个，把-号去掉
        // if (countInput.value == 1) {
        //     span.innerHTML = '';
        // } else {
        //     span.innerHTML = '-';
        // }
    }

    //为每行元素添加事件
    for (var i = 0; i < Vegetabletr.length; i++) {
        //将点击事件绑定到tr元素
        Vegetabletr[i].onclick = function(e) {
                var e = e || window.event;
                var el = e.target || e.srcElement; //通过事件对象的target属性获取触发元素
                var cls = el.className; //触发元素的class
                var countInout = this.getElementsByTagName('input')[1]; // 数目input
                var value = parseInt(countInout.value); //数目
                //通过判断触发元素的class确定用户点击了哪个元素
                switch (cls) {
                    case 'add': //点击了加号
                        countInout.value = value + 1;
                        getSubtotal(this);
                        break;
                    case 'reduce': //点击了减号
                        if (value > 1) {
                            countInout.value = value - 1;
                            getSubtotal(this);
                        }
                        break;
                    case 'delete': //点击了删除
                        var conf = confirm('确定删除此商品吗？');
                        if (conf) {
                            this.parentNode.removeChild(this);
                        }
                        break;
                }
                getVegetableTotal();
            }
            // 给数目输入框绑定keyup事件
        Vegetabletr[i].getElementsByTagName('input')[1].onkeyup = function() {
            var val = parseInt(this.value);
            if (isNaN(val) || val <= 0) {
                val = 1;
            }
            if (this.value != val) {
                this.value = val;
            }
            getSubtotal(this.parentNode.parentNode); //更新小计
            getVegetableTotal(); //更新总数
        }
    }

    //已选商品弹层中的取消选择按钮
    // selectedViewList.onclick = function(e) {
    //         var e = e || window.event;
    //         var el = e.srcElement;
    //         if (el.className == 'del') {
    //             var input = Vegetabletr[el.getAttribute('index')].getElementsByTagName('input')[0]
    //             input.checked = false;
    //             input.onclick();
    //         }
    //     }
    /*----------------------------------------------------------------------
                        Vegetable table end
    ---------------------------------------------------------------------*/
    /*----------------------------------------------------------------------
                        Food JS table start
    ---------------------------------------------------------------------*/
    // 更新总数和总价格，已选浮层
    function getFoodTotal() {
        var seleted = 0;
        var price = 0;
        var HTMLstr = '';
        for (var i = 0, len = Foodtr.length; i < len; i++) {
            if (Foodtr[i].getElementsByTagName('input')[0].checked) {
                Foodtr[i].className = 'on';
                seleted += parseInt(Foodtr[i].getElementsByTagName('input')[1].value);
                price += parseFloat(Foodtr[i].cells[5].innerHTML);
                HTMLstr += '<div><img src="' + Foodtr[i].getElementsByTagName('img')[0].src + '"><span class="del" index="' + i + '">取消选择</span></div>'
            } else {
                Foodtr[i].className = '';
            }
        }
        FoodselectedTotal.innerHTML = seleted;
        FoodpriceTotal.innerHTML = price.toFixed(2);
        selectedViewList.innerHTML = HTMLstr;
        getTotal();
        if (seleted == 0) {
            foot.className = 'foot';
        }
    }
    // 计算单行价格
    function getSubtotal(Foodtr) {
        var cells = Foodtr.cells;
        var price = cells[2]; //单价
        var subtotal = cells[5]; //小计td
        var countInput = Foodtr.getElementsByTagName('input')[1]; //数目input
        var span = Foodtr.getElementsByTagName('span')[1]; //-号
        //写入HTML
        subtotal.innerHTML = (parseInt(countInput.value) * parseFloat(price.innerHTML)).toFixed(2);
        //如果数目只有一个，把-号去掉
        // if (countInput.value == 1) {
        //     span.innerHTML = '';
        // } else {
        //     span.innerHTML = '-';
        // }
    }


    //为每行元素添加事件
    for (var i = 0; i < Foodtr.length; i++) {
        //将点击事件绑定到tr元素
        Foodtr[i].onclick = function(e) {
                var e = e || window.event;
                var el = e.target || e.srcElement; //通过事件对象的target属性获取触发元素
                var cls = el.className; //触发元素的class
                var countInout = this.getElementsByTagName('input')[1]; // 数目input
                var value = parseInt(countInout.value); //数目
                //通过判断触发元素的class确定用户点击了哪个元素
                switch (cls) {
                    case 'add': //点击了加号
                        countInout.value = value + 1;
                        getSubtotal(this);
                        break;
                    case 'reduce': //点击了减号
                        if (value > 1) {
                            countInout.value = value - 1;
                            getSubtotal(this);
                        }
                        break;
                    case 'delete': //点击了删除
                        var conf = confirm('确定删除此商品吗？');
                        if (conf) {
                            this.parentNode.removeChild(this);
                        }
                        break;
                }
                getFoodTotal();
            }
            // 给数目输入框绑定keyup事件
        Foodtr[i].getElementsByTagName('input')[1].onkeyup = function() {
            var val = parseInt(this.value);
            if (isNaN(val) || val <= 0) {
                val = 1;
            }
            if (this.value != val) {
                this.value = val;
            }
            getSubtotal(this.parentNode.parentNode); //更新小计
            getFoodTotal(); //更新总数
        }

    }
    /*----------------------------------------------------------------------
                               Vegetable table end
     ---------------------------------------------------------------------*/



    //已选商品弹层中的取消选择按钮
    // selectedViewList.onclick = function(e) {
    //         var e = e || window.event;
    //         var el = e.srcElement;
    //         if (el.className == 'del') {
    //             var input = Foodtr[el.getAttribute('index')].getElementsByTagName('input')[0]
    //             input.checked = false;
    //             input.onclick();
    //         }
    //     }
    /*----------------------------------------------------------------------
                        Food JS table end
    ---------------------------------------------------------------------*/

    function getTotal() {

        var meatTotalprice = parseFloat($("#MeatpriceTotal").text());
        var fruitTotalprice = parseFloat($("#FruitpriceTotal").text());
        var vegetableTotalprice = parseFloat($("#VegetablepriceTotal").text());
        var foodTotalprice = parseFloat($("#FoodpriceTotal").text());

        var meatTotalnumber = parseInt($("#MeatselectedTotal").text());
        var fruitTotalnumber = parseInt($("#FruitselectedTotal").text());
        var vegetableTotalnumber = parseInt($("#VegetableselectedTotal").text());
        var foodTotalnumber = parseInt($("#FoodselectedTotal").text());

        var totalprice = meatTotalprice + fruitTotalprice + vegetableTotalprice + foodTotalprice;
        var totalnumber = meatTotalnumber + fruitTotalnumber + vegetableTotalnumber + foodTotalnumber;
        totalprice = changeTwoDecimal(totalprice);
        selectedTotal.innerHTML = totalnumber;
        priceTotal.innerHTML = totalprice;
        //selectedViewList.innerHTML = HTMLstr;
    }

    // 保留小数点后两位
    function changeTwoDecimal(x) {
        var f_x = parseFloat(x);
        if (isNaN(f_x)) {
            //alert('function:changeTwoDecimal->parameter error');
            return false;
        }
        f_x = Math.round(f_x * 100) / 100;

        return f_x;
    }



    /*-----------------------------------------------------------
                     所有 checkbox 逻辑函数 start
    ------------------------------------------------------------*/
    var singlecheck;
    var singleMeatcheck;
    var singleFruitcheck;
    var singleVegetablecheck;
    for (var i = 0; i < selectInputs.length; i++) {
        selectInputs[i].onclick = function() {
            if (this.className.indexOf('MeatcheckAll') >= 0) { //meat 全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className.indexOf('Meatcheck') >= 0) {
                        selectInputs[j].checked = this.checked;
                    }
                }
            }
            if (this.className.indexOf('FruitcheckAll') >= 0) { //fruit 全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className.indexOf('Fruitcheck') >= 0) {
                        selectInputs[j].checked = this.checked;
                    }
                }
            }
            if (this.className.indexOf('VegetablecheckAll') >= 0) { //vegetable 全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className.indexOf('Vegetablecheck') >= 0) {
                        selectInputs[j].checked = this.checked;
                    }
                }
            }
            if (this.className.indexOf('FoodcheckAll') >= 0) { //food 全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className.indexOf('Foodcheck') >= 0) {
                        selectInputs[j].checked = this.checked;
                    }
                }
            }
            if (this.className.indexOf('Meatcheck') >= 0) { //meat 单选全部致全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className == "Meatcheck check") {
                        if (!selectInputs[j].checked) {
                            singlecheck = 0;
                            break;
                        } else {
                            singlecheck = 1;
                            continue;
                        }
                    }
                }
                if (singlecheck == 0) {
                    MeatcheckAllInputs[0].checked = false;
                } else {
                    MeatcheckAllInputs[0].checked = true;
                }
            }
            if (this.className.indexOf('Fruitcheck') >= 0) { //fruit 单选全部致全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className == "Fruitcheck check") {
                        if (!selectInputs[j].checked) {
                            singlecheck = 0;
                            break;
                        } else {
                            singlecheck = 1;
                            continue;
                        }
                    }
                }
                if (singlecheck == 0) {
                    FruitcheckAllInputs[0].checked = false;
                } else {
                    FruitcheckAllInputs[0].checked = true;
                }
            }
            if (this.className.indexOf('Vegetablecheck') >= 0) { //vegetable 单选全部致全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className == "Vegetablecheck check") {
                        if (!selectInputs[j].checked) {
                            singlecheck = 0;
                            break;
                        } else {
                            singlecheck = 1;
                            continue;
                        }
                    }
                }
                if (singlecheck == 0) {
                    VegetablecheckAllInputs[0].checked = false;
                } else {
                    VegetablecheckAllInputs[0].checked = true;
                }
            }
            if (this.className.indexOf('Foodcheck') >= 0) { //food 单选全部致全选
                for (var j = 0; j < selectInputs.length; j++) {
                    if (selectInputs[j].className == "Foodcheck check") {
                        if (!selectInputs[j].checked) {
                            singlecheck = 0;
                            break;
                        } else {
                            singlecheck = 1;
                            continue;
                        }
                    }
                }
                if (singlecheck == 0) {
                    FoodcheckAllInputs[0].checked = false;
                } else {
                    FoodcheckAllInputs[0].checked = true;
                }
            }

            // //全部 单选全部致全选
            // for (var j = 0; j < selectInputs.length; j++) {
            //     if (selectInputs[j].className == "MeatcheckAll check") {
            //         if (!selectInputs[j].checked) {
            //             singleMeatcheck = 0;
            //         } else {
            //             singleMeatcheck = 1;
            //         }
            //     } //判断meat是否全选
            //     if (selectInputs[j].className == "MeatcheckAll check") {
            //         if (!selectInputs[j].checked) {
            //             singleFruitcheck = 0;
            //         } else {
            //             singleFruitcheck = 1;
            //         }
            //     } //判断fruit是否全选
            //     if (selectInputs[j].className == "MeatcheckAll check") {
            //         if (!selectInputs[j].checked) {
            //             singleVegetablecheck = 0;
            //         } else {
            //             singleVegetablecheck = 1;
            //         }
            //     } //判断vegetable是否全选
            // }
            // if (singleMeatcheck == 1 && singleFruitcheck == 1 && singleVegetablecheck == 1) {
            //     checkAllInputs[0].checked = true;
            // } 



            if (this.className.indexOf('check-all') >= 0) { //如果是全选，则吧所有的选择框选中
                for (var j = 0; j < selectInputs.length; j++) {
                    selectInputs[j].checked = this.checked;
                }
            }
            if (!this.checked) { //只要有一个未勾选，则取消全选框的选中状态
                for (var i = 0; i < checkAllInputs.length; i++) {
                    if (this.className.indexOf('Meatcheck') >= 0) { //meat 全选
                        MeatcheckAllInputs[i].checked = false;
                    }
                    if (this.className.indexOf('Fruitcheck') >= 0) { //meat 全选
                        FruitcheckAllInputs[i].checked = false;
                    }
                    if (this.className.indexOf('Vegetablecheck') >= 0) { //meat 全选
                        VegetablecheckAllInputs[i].checked = false;
                    }
                    if (this.className.indexOf('Foodcheck') >= 0) { //meat 全选
                        VegetablecheckAllInputs[i].checked = false;
                    }
                    checkAllInputs[i].checked = false;
                }
            }
            if (FoodcheckAllInputs[0].checked && MeatcheckAllInputs[0].checked && FruitcheckAllInputs[0].checked && VegetablecheckAllInputs[0].checked) {
                checkAllInputs[0].checked = true;
            }
            getMeatTotal();
            getFruitTotal();
            getVegetableTotal();
            getFoodTotal();
            getTotal(); //更新总计
        }
    }

    /*--------------------------------------------------------------
                     所有 checkbox 逻辑函数 end
    ------------------------------------------------------------*/


    // 显示已选商品弹层
    selected.onclick = function() {
        if (selectedTotal.innerHTML != 0) {
            foot.className = (foot.className == 'foot' ? 'foot show' : 'foot');
        }
    }

    // // 点击全部删除
    // deleteAll.onclick = function() {
    //     if (selectedTotal.innerHTML != 0) {
    //         var con = confirm('确定删除所选商品吗？'); //弹出确认框
    //         if (con) {
    //             for (var i = 0; i < tr.length; i++) {
    //                 // 如果被选中，就删除相应的行
    //                 if (tr[i].getElementsByTagName('input')[0].checked) {
    //                     tr[i].parentNode.removeChild(tr[i]); // 删除相应节点
    //                     i--; //回退下标位置
    //                 }
    //             }
    //         }
    //     } else {
    //         alert('请选择商品！');
    //     }
    //     getTotal(); //更新总数
    // }
    // console.log("\u767e\u5ea6\u641c\u7d22\u3010\u7d20\u6750\u5bb6\u56ed\u3011\u4e0b\u8f7d\u66f4\u591aJS\u7279\u6548\u4ee3\u7801");
    // // 默认全选
    // checkAllInputs[0].checked = true;
    // checkAllInputs[0].onclick();

    // table js end



}
