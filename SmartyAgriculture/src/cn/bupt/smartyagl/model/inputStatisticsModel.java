package cn.bupt.smartyagl.model;
/**
 * 
 *<p>Title:inputStatisticsModel</p>
 *<p>Description:商品进货统计模型</p>
 * @author waiting
 *@date 2016年10月25日 上午10:40:29
 */
public class inputStatisticsModel {
	  	private String showTime;//进货时间
		private Integer num;// 进货数量
	    private Integer input_num;// 进货次数
		public String getShowTime() {
			return showTime;
		}
		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}
		public Integer getNum() {
			return num;
		}
		public void setNum(Integer num) {
			this.num = num;
		}
		public Integer getInput_num() {
			return input_num;
		}
		public void setInput_num(Integer input_num) {
			this.input_num = input_num;
		}
	    
}
