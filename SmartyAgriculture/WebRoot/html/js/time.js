/**倒计时~秒**/
var maxTime = 59;
function countDownMinus() {
    var msg ;
        if (maxTime >= 0) {
            var seconds = Math.floor(maxTime % 60);
            msg =seconds+"秒";
            --maxTime;
            //return msg;
        } else {
            maxTime = 59;
            msg = "";
            //return msg;
        } 
        console.log(msg);
        return msg;
}

