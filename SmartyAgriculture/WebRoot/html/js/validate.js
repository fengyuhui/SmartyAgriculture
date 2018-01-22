/*
@author xiaoming

2016-8-18
*/
/**
 * 正则表达式验证Email格式
 * @param str 所要验证的邮箱地址
 * @return boolean
 */
function isEmail(str) {
    var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    if (emailReg.test(str)) {
        return true;
    } else {
        return false;
    }
}
/**
 * 正则表达式验证手机号码(中国大陆区)
 * @param str 所要验证的手机号
 * @return boolean
 */
function isMobile(str) {
    var regu = /^[1][3][0-9]{9}$/;
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入的电话号码格式是否正确
输入：strPhone：字符串
返回：如果通过验证返回true,否则返回false
*/
function checkPhone(strPhone) {
    var phoneRegWithArea = /^[0][1-9]{2,3}-[0-9]{5,10}$/;
    var phoneRegNoArea = /^[1-9]{1}[0-9]{5,8}$/;
    //var prompt = "您输入的电话号码不正确!"
    if (strPhone.length > 9) {
        if (phoneRegWithArea.test(strPhone)) {
            return true;
        } else {
            //alert( prompt );
            return false;
        }
    } else {
        if (phoneRegNoArea.test(strPhone)) {
            return true;
        } else {
            //alert(prompt);
            return false;
        }

    }
}
/**
 * 正则表达式验证网址
 * @param str 所要验证的网址
 * @return boolean
 */
function isUrl(str) {
    if (str.match(/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i) == null) {
        return false
    } else {
        return true;
    }
}

/**
 * 验证字符串是否包含特殊字符,包含返回false不包含返回true
 * @param str 所要验证的字符串
 * @return boolean
 */
function isString(str) {
    var items = new Array("~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "{", "}", "[", "]", "(", ")");
    items.push(":", ";", "'", "|", "\\", "<", ">", "?", "/", "<<", ">>", "||", "//");
    items.push("admin", "administrators", "administrator", "管理员", "系统管理员");
    items.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
    str = str.toLowerCase();
    for (var i = 0; i < items.length; i++) {
        if (str.indexOf(items[i]) >= 0) {
            return false;
        }
    }
    return true;
}
/**
 * 正则表达式验证身份证号码
 * @param str 所要验证的身份证号码
 * @return boolean
 */
function isPersonalCard(str) {
    //15位数身份证正则表达式
    var arg1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
    //18位数身份证正则表达式
    var arg2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
    if (str.match(arg1) == null && str.match(arg2) == null) {
        return false;
    } else {
        return true;
    }
}
/**
 *  正则表达式验证IP地址
 * @param str 所要验证的IP地址
 * @return boolean
 */
function isIp(str) {
    if (isNull(str)) return false;
    var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式
    if (re.test(str)) {
        if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256) return true;
    }
    return false;
}
/*
用途：检查输入字符串是否为空或者全部都是空格
输入：str
返回：如果全是空返回true,否则返回false
*/
function isNull(str) {
    if (str == "") return true;
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
}

/*
用途：检查输入的起止日期是否正确，规则为两个日期的格式正确，
且结束如期>=起始日期
输入：startDate：起始日期，字符串 endDate：结束如期，字符串
返回：如果通过验证返回true,否则返回false
*/
function checkTwoDate(startDate, endDate) {
    if (!isDate(startDate)) {
        alert("起始日期不正确!");
        return false;
    } else if (!isDate(endDate)) {
        alert("终止日期不正确!");
        return false;
    } else if (startDate > endDate) {
        alert("起始日期不能大于终止日期!");
        return false;
    }
    return true;
}

/*
用途：字符1是否以字符串2开始
输入：str1：字符串；str2：被包含的字符串
返回：如果通过验证返回true,否则返回false
*/
function isFirstMatch(str1, str2) {
    var index = str1.indexOf(str2);
    if (index == 0) return true;
    return false;
}
/*
用途：字符1是包含字符串2
输入：str1：字符串；str2：被包含的字符串
返回：如果通过验证返回true,否则返回false
*/
function isMatch(str1, str2) {
    var index = str1.indexOf(str2);
    if (index == -1) return false;
    return true;
}
/*
用途：字符1是否以字符串2结束
输入：str1：字符串；str2：被包含的字符串
返回：如果通过验证返回true,否则返回false
*/
function isLastMatch(str1, str2) {
    var index = str1.lastIndexOf(str2);
    if (str1.length == index + str2.length) return true;
    return false;
}
/*
用途：判断是否是日期
输入：date：日期；fmt：日期格式
返回：如果通过验证返回true,否则返回false
*/
function isDate(date, fmt) {
    if (fmt == null) fmt = "yyyyMMdd";
    var yIndex = fmt.indexOf("yyyy");
    if (yIndex == -1) return false;
    var year = date.substring(yIndex, yIndex + 4);
    var mIndex = fmt.indexOf("MM");
    if (mIndex == -1) return false;
    var month = date.substring(mIndex, mIndex + 2);
    var dIndex = fmt.indexOf("dd");
    if (dIndex == -1) return false;
    var day = date.substring(dIndex, dIndex + 2);
    if (!isNumber(year) || year > "2100" || year < "1900") return false;
    if (!isNumber(month) || month > "12" || month < "01") return false;
    if (day > getMaxDay(year, month) || day < "01") return false;
    return true;
}

function getMaxDay(year, month) {
    if (month == 4 || month == 6 || month == 9 || month == 11)
        return "30";
    if (month == 2)
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return "29";
        else
            return "28";
    return "31";
}
/*
用途：检查输入字符串是否符合金额格式
格式定义为带小数的正数，小数点后最多三位
输入：字符串
返回：如果通过验证返回true,否则返回false
*/
function isMoney(s) {
    var regu = "^[0-9]+[\.][0-9]{0,3}$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入字符串是否只由英文字母和数字和下划线组成
输入：字符串
返回：如果通过验证返回true,否则返回false
*/
function isNumberOr_Letter(s) { //判断是否是数字或字母
    var regu = "^[0-9a-zA-Z\_]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入字符串是否只由英文字母和数字组成
输入：字符串
返回：如果通过验证返回true,否则返回false
*/
function isNumberOrLetter(s) { //判断是否是数字或字母
    var regu = "^[0-9a-zA-Z]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入字符串是否只由汉字、字母、数字组成
输入：字符串
返回：如果通过验证返回true,否则返回false
*/
function isChinaOrNumbOrLett(s) { //判断是否是汉字、字母、数字组成
    var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入对象的值是否符合整数格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false
*/
function isInteger(str) {
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    return regu.test(str);
}


/*
用途：检查输入字符串是否符合正整数格式
输入：字符串
返回：如果通过验证返回true,否则返回false
*/
function isNumber(s) {
    var regu = "^[0-9]+$";
    var re = new RegExp(regu);
    if (s.search(re) != -1) {
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入字符串是否是带小数的数字格式,可以是负数
输入：字符串
返回：如果通过验证返回true,否则返回false
*/
function isDecimal(str) {
    if (isInteger(str)) return true;
    var re = /^[-]{0,1}(\d+)[\.]+(\d+)$/;
    if (re.test(str)) {
        if (RegExp.$1 == 0 && RegExp.$2 == 0) return false;
        return true;
    } else {
        return false;
    }
}
/*
用途：检查输入对象的值是否符合端口号格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false
*/
function isPort(str) {
    return (isNumber(str) && str < 65536);
}
