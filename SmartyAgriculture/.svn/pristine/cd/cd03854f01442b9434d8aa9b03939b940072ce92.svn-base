var option = {
	title : {
		text : '',// 图标标题
		// subtext: '纯属虚构',//副标题
		x : 'left',// 设置标题的位置 left靠左 center 居中 right 靠右
	},
	tooltip : {},// 消息提示框
	xAxis: {//横坐标的数据
        data: []
    },
    yAxis: {//纵坐标的数据
    	//name : '销售额', //纵坐标的名字
    },
	toolbox : {
		show : true,
		orient : 'horizontal', // 布局方式，默认为水平布局，可选为：
		// 'horizontal' ¦ 'vertical'
		x : 'right', // 水平安放位置，默认为全图右对齐，可选为：
		// 'center' ¦ 'left' ¦ 'right'
		// ¦ {number}（x坐标，单位px）
		y : 'top', // 垂直安放位置，默认为全图顶端，可选为：
		// 'top' ¦ 'bottom' ¦ 'center'
		// ¦ {number}（y坐标，单位px）
		color : [ '#1e90ff', '#22bb22', '#4b0082', '#d2691e' ],
		backgroundColor : 'rgba(0,0,0,0)', // 工具箱背景颜色
		borderColor : '#ccc', // 工具箱边框颜色
		borderWidth : 0, // 工具箱边框线宽，单位px，默认为0（无边框）
		padding : 5, // 工具箱内边距，单位px，默认各方向内边距为5，
		showTitle : true,
		feature : {
			magicType : {
				show : true,
				title : {
					line : '动态类型切换-折线图',
					bar : '动态类型切换-柱形图',
				},
				type : [ 'line', 'bar' ]
			},
			restore : {
				show : true,
				title : '还原',
				color : 'black'
			},
			saveAsImage : {
				show : true,
				title : '保存为图片',
				type : 'jpeg',
				lang : [ '点击本地保存' ]
			},
//			myTool : {
//				show : true,
//				title : '自定义扩展方法',
//				icon : 'image://../asset/ico/favicon.png',
//				onclick : function() {
//					alert('myToolHandler')
//				}
//			}
		}
	},
	  series: [{
	        type: 'bar',
	        itemStyle : {//图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
                normal : {
                    borderRadius : 0,// 提示边框圆角
                    color : '#0099CC'
                }
            },
	        data: []
	    }]
}