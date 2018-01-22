package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.FreightMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.InputGoodsViewMapper;
import cn.bupt.smartyagl.entity.autogenerate.Fre;
import cn.bupt.smartyagl.entity.autogenerate.Freight;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.InputGoods;
import cn.bupt.smartyagl.entity.autogenerate.InputGoodsView;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.model.GoodsPriceModel;
import cn.bupt.smartyagl.model.GoodsTypeModel;
import cn.bupt.smartyagl.service.IBlockService;
import cn.bupt.smartyagl.service.IFreightService;
import cn.bupt.smartyagl.service.IGoodsInputService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IGoodsTypeService;
import cn.bupt.smartyagl.service.IPushService;
import cn.bupt.smartyagl.service.IStatisticsService;
import cn.bupt.smartyagl.util.DateTag;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.pinyin4jUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

/**
 * 
 * <p>
 * Title:ProductController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author waiting
 * @date 2016年5月27日 上午8:57:13
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

	@Autowired
	IGoodsTypeService goodsTypeService;
	@Autowired
	IBlockService blockService;
	@Autowired
	IGoodsService goodsService;
	@Autowired
	IStatisticsService statisticsService;
	@Autowired
	IPushService pushService;
	@Autowired
	IFreightService freightService;
	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	IGoodsInputService goodsInputService;
	@Autowired
	InputGoodsViewMapper inputGoodsViewMapper;
	@Autowired
	FreightMapper freightMapper;
	int pageSize = Constants.PAGESIZE;// 每一页的大小

	/**
	 * 显示（后台）商品列表
	 * 
	 * @author wlz
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/index/{allPages}/{currentPage}/{type}/{status}/{saleStatus}/{productName}")
	public ModelAndView productIndex(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "status") int status,
			@PathVariable(value = "saleStatus") int saleStatus,
			@PathVariable(value = "productName") String productName)
			throws UnsupportedEncodingException {
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		// productName=URLDecoder.decode(productName,"utf-8");
		//productName = new String(productName.getBytes("iso8859-1"), "UTF-8");
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_INDEX);

		Page page = PageHelper.startPage(currentPage, pageSize, "id");

		List<GoodsList> goodsList = goodsService.getGoodsList(status,
				saleStatus, productName);

		modelAndView.addObject("goodsList", goodsList);
		// 总页数
		allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		if (!productName.equals("1")) {
			modelAndView.addObject("productName", productName);
		} else {
			modelAndView.addObject("productName", "");
		}
		modelAndView.addObject("status", status);
		return modelAndView;
	}

	/**
	 * 添加商品
	 * 
	 * @return
	 */
	@RequestMapping("/addproduct")
	public ModelAndView addProduct(HttpServletRequest request,Goods Product) {
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_ADD);
//		modelAndView.setViewName(Constants.PRODUCT_ADD);
		List<GoodsTypeModel> goodsTypeList = goodsTypeService
				.getDisplayGoodsType();
		List<BlockModel> blockList = blockService.getBlockList();
		modelAndView.addObject("goodsTypeList", goodsTypeList);
		modelAndView.addObject("blockList", blockList);
		return modelAndView;
	}

	/**
	 * 
	 * @param Product
	 *            添加商品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addProductPost")
	public ModelAndView addProductPost(Goods Product,
			HttpServletRequest request, String send_Time) throws Exception {
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_ADD);
		if(Product != null){
		// 上传文件路径
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/../" + Constants.FILE_PATH;
		// 上传文件
		List<String> filePath = new ArrayList<String>();
		filePath = this.uploadFile(request, path);
		if (filePath != null) {
			// json格式化
			ObjectMapper mapper = new ObjectMapper();
			String filePathJsonString = mapper.writeValueAsString(filePath);

			Product.setPicture(filePathJsonString);
		}

		// 处理预计送达时间
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(send_Time);
		} catch (ParseException e) {
		}
		Product.setSendTime(date);
		// 设置拼音
		String pinyin = pinyin4jUtil.getPinyinString(Product.getName());
		Product.setPinyin(pinyin);
		// Product.setGoodsDetail(this.formatHtml(Product.getGoodsDetail()));

		int goodsId = goodsService.addGoods(Product);
		/*
		goodsExample goodsExample = new goodsExample();
		Criteria criteria = goodsExample.createCriteria();
		criteria.andNameEqualTo(Product.getName());
		criteria.andTitleEqualTo(Product.getTitle());
		criteria.andPriceEqualTo(Product.getPrice());
		List<goods> getGoodeList = goodsMapper.selectByExample(goodsExample);
		goods getGoods = getGoodeList.get(0);
		*/
//		goodsId = Product.getId();
		if (request.getParameterValues("province") != null) {
			// 设置运费模板
			String[] ProvinceArray = request.getParameterValues("province");
			String[] CityArray = request.getParameterValues("city");
			String[] MoneyArray = request.getParameterValues("money");
			// List<Freight> freightList=new ArrayList<Freight>();
			if (ProvinceArray.length != 0) {
				for (int i = 0; i < ProvinceArray.length; i++) {
					if (ProvinceArray[i] != null) {
						Freight freight = new Freight();
						freight.setMoney(Double.valueOf(MoneyArray[i]));
						freight.setProvince(ProvinceArray[i]);
						freight.setCity(CityArray[i]);
						freight.setGoodsId(Product.getId());
						freight.setAuditStatus(1);
						freightService.addFreight(freight);
					}
				}
			}
		}
		// 添加成功
		if (goodsId != 0) {
			modelAndView.addObject("resultMsg", "添加商品成功");
//			modelAndView = this.productIndex(0, 1, "prvious", 3,-1, "1");
//			return modelAndView;
			// return Constants.PRODUCT_INDEX;
		} else {
			modelAndView.addObject("resultMsg", "添加商品失败");
//			modelAndView = this.addProduct(request, Product);
			
		}
		//跳转到首页
	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious/3/-1/1" );
	  		modelAndView.setView(redirectView);
		}
		return modelAndView;
		
	}

	/**
     * 提交删除申请，并非真的删除
     * @param request
     * @param id
     * @param 
     * @return
     */
    @RequestMapping("/deleteRequest")
    public ModelAndView deleteRequest(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        Map<String, String>map = new HashMap<String, String>();
        Goods fa = new Goods();
        fa.setId(id);
        fa.setAuditStatus( ConstantsSql.Audit_WaitDelete );
        boolean rs = goodsService.deletePostGoods(fa);
        if(rs){
            mv.addObject("resultMsg", "申请删除成功");
        }else{
            mv.addObject("resultMsg", "申请删除失败");
        }
        RedirectView redirectView = new RedirectView( "index/0/1/prvious/3/-1/1" );
        mv.setView(redirectView);
        return mv;
    }
	
	
	/**
	 * 修改商品状态
	 * 
	 * @param status
	 *            状态
	 * @param id
	 *            商品id
	 * @return
	 */
	@RequestMapping("/updateGoodsStatus")
	@ResponseBody
	public Map<String, String> updateGoodsStatus(int status, int id) {
		boolean flag = goodsService.updateGoodsStatus(status, id);
		Map<String, String> returnMap = new HashMap<String, String>();
		if (flag) {
			returnMap.put("msg", "修改成功");
		} else {
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}

	/**
	 * 修改商品销售状态
	 * 
	 * @param id
	 * @param saleStatus
	 * @param time
	 * @return
	 */
	@RequestMapping("/updateSaleStatus")
	@ResponseBody
	public Map<String, String> updateSaleStatus(int id, int saleStatus,
			String time) {
		Date date = null;
		if (time.equals("____/__/__ __:__")) {
			date = null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean flag = goodsService.updateGoodsSaleStatus(id, saleStatus, date,
				null, null);
		Map<String, String> returnMap = new HashMap<String, String>();
		if (flag) {
			returnMap.put("msg", "修改成功");
		} else {
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}

	public List<String> uploadFile(HttpServletRequest request, String rootPath) {
		List<String> filePath = new ArrayList<String>();
		try {
			// 设置上下方文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				if (!iter.hasNext())// 没有文件上传
					return null;
				int i = 0;
				Long time = System.currentTimeMillis();
				String fileName = time.toString();
				while (iter.hasNext()) {// 遍历文件
					// 由CommonsMultipartFile继承而来,拥有上面的方法.
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String tempString = "goods/" + fileName + "_" + i;
						String fileNameThis = rootPath + tempString;
						String fileNameString = ImageUtil.uploadImage(file,
								fileNameThis);
						if (fileNameString != null) {
							tempString += "/" + fileNameString;
							filePath.add(tempString);
							i++;
						} else {
							return null;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filePath;
	}

	/**
	 * 查看商品详情
	 * 
	 * @param id
	 * @return
	 * @author waiting
	 */
	@RequestMapping("/getProductDetail/{id}")
	public ModelAndView getProductDetail(@PathVariable(value = "id") int id,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_DETAIL);
		GoodsList goodsList = goodsService.getGoodsDetail(id);
		modelAndView.addObject("goodsList", goodsList);
		if (goodsList.getPicture() != null) {
			// 处理商品图片
			List<String> pictureList = new ArrayList<String>();
			try {
				pictureList = JsonConvert.getProductPicture(
						goodsList.getPicture(), request);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			modelAndView.addObject("pictureList", pictureList);
		}

		// 销售状态
		int salestatus = goodsList.getSaleStatus();
		String saleString = null;
		switch (salestatus) {
		case ConstantsSql.SPECIALPRICE:
			saleString = "特价";
			break;
		case ConstantsSql.LIMITATIONTIME:
			saleString = "限时";
			break;
		case ConstantsSql.NEWGOODS:
			saleString = "新品";
			break;
		case ConstantsSql.HOTGOODS:
			saleString = "热卖";
			break;
		default:
			saleString = "新品";
			break;
		}
		// 地块名字
		String blockName;
		if(goodsList.getBlockId() == null) {
			blockName = "无";
		}
		else {
			blockName = blockService.getBlockName(goodsList.getBlockId());
		}
		// 商品状态
		int status = goodsList.getStatus();
		String statusString = null;
		switch (status) {
		case ConstantsSql.ONSALE:
			statusString = "在售";
			break;
		case ConstantsSql.OFFTHESHELVES:
			statusString = "下架";
			break;
		}
		// 运费模板
		List<Freight> freightList = freightService.freightList(id);
		modelAndView.addObject("freightList", freightList);
		int freightListLength = freightList.size();
		if (freightListLength != 0) {
			modelAndView.addObject("flag", true);
		} else {
			modelAndView.addObject("flag", false);
		}
		modelAndView.addObject("goodsList", goodsList);
		modelAndView.addObject("saleStatus", saleString);
		modelAndView.addObject("blockName", blockName);
		modelAndView.addObject("status", statusString);
		modelAndView.addObject("goodsDetail", goodsList.getGoodsDetail());
		return modelAndView;
	}

	/**
	 * 跳转到商品编辑界面
	 */
	@RequestMapping("/toProductEdit/{id}")
	public ModelAndView toProductEdit(@PathVariable(value = "id") int id) {
//		System.out.println("1="+id);
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_EDIT);
		GoodsList goodsList = goodsService.getGoodsDetail(id);
		modelAndView.addObject("goodsList", goodsList);
		List<Freight> freightList = freightService.freightList(id);
		// 地块
		List<BlockModel> blockList = blockService.getBlockList();
		modelAndView.addObject("blockList", blockList);
		// 商品种类
		List<GoodsTypeModel> goodsTypeList = goodsTypeService.getDisplayGoodsType();
		modelAndView.addObject("goodsTypeList", goodsTypeList);
		int freightListLength = freightList.size();
//		System.out.println("3="+id);
		if (freightListLength != 0) {
			modelAndView.addObject("flag", true);
		} else {
			modelAndView.addObject("flag", false);
		}
		modelAndView.addObject("freightListLength", freightListLength);
		modelAndView.addObject("freightList", freightList);
		return modelAndView;
	}

	/**
	 * 编辑商品
	 * 
	 * @return
	 * @author waiting
	 */

	@RequestMapping("/updateProduct")
	public String updateProduct(HttpServletRequest request, Goods Product,
			String send_Time) {
//		ModelAndView mv = new ModelAndView(Constants.PRODUCT_EDIT);
//		if(Product != null && Product.getId() != null){
		// 设置商品编辑之前的id
		System.out.println("4="+Product.getId());
		Product.setSourceId(Product.getId());
		Goods sourceGoods = goodsMapper.selectByPrimaryKey(Product
				.getSourceId());
		Product.setId(null);
		Product.setSaleStatus(sourceGoods.getSaleStatus());
		Product.setStatus(sourceGoods.getStatus());
		Product.setAuditStatus(ConstantsSql.Audit_Draft);

		// 判断是否有上传的文件 上传文件路径
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/../" + Constants.FILE_PATH;
		// 上传文件
		List<String> filePath = new ArrayList<String>();
		filePath = this.uploadFile(request, path);
		if (filePath != null) {
			// json格式化
			ObjectMapper mapper = new ObjectMapper();
			String filePathJsonString;
			try {
				filePathJsonString = mapper.writeValueAsString(filePath);
				Product.setPicture(filePathJsonString);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// 处理预计送达时间
		if (send_Time != null) {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(send_Time);
			} catch (ParseException e) {
			}
			Product.setSendTime(date);
		}
		// 设置拼音
		String pinyin = pinyin4jUtil.getPinyinString(Product.getName());
		Product.setPinyin(pinyin);
		Product.setAuditStatus(ConstantsSql.Audit_Draft);
		boolean flag = goodsService.updateGoods(Product);
		// goodsExample example=new goodsExample();
		// Criteria criteria=example.createCriteria();
		// criteria.andSourceIdEqualTo(Product.getSourceId());
		// List<goods> goods=goodsMapper.selectByExample(example);
		// 新增运费模板
		// 原来的运费模板一样的处理
		if (request.getParameterValues("province") != null) {
			// 设置运费模板
			String[] ProvinceArray = request.getParameterValues("province");
			String[] CityArray = request.getParameterValues("city");
			String[] MoneyArray = request.getParameterValues("money");
			if (ProvinceArray.length != 0) {
				for (int i = 0; i < ProvinceArray.length; i++) {
					if (ProvinceArray[i] != null) {
						Freight freight = new Freight();
						freight.setMoney(Double.valueOf(MoneyArray[i]));
						freight.setProvince(ProvinceArray[i]);
						freight.setCity(CityArray[i]);
						freight.setGoodsId(Product.getSourceId());
						freight.setAuditStatus(ConstantsSql.Audit_Draft);
						freightService.addFreight(freight);
					}
				}
			}
		}
		// 更新原来的运费模板
		if (request.getParameterValues("update_province") != null) {
			// 设置运费模板
			String[] ProvinceArray = request
					.getParameterValues("update_province");
			String[] CityArray = request.getParameterValues("update_city");
			String[] MoneyArray = request.getParameterValues("update_money");
			if (ProvinceArray.length != 0) {
				for (int i = 0; i < ProvinceArray.length; i++) {
					if (ProvinceArray[i] != null) {
						Freight freight = new Freight();
						freight.setMoney(Double.valueOf(MoneyArray[i]));
						freight.setProvince(ProvinceArray[i]);
						freight.setCity(CityArray[i]);
						freight.setGoodsId(Product.getSourceId());
						freight.setAuditStatus(ConstantsSql.Audit_Draft);
						freightService.updateFreight(freight);
					}
				}
			}
		}
		//

		sourceGoods.setAuditStatus(ConstantsSql.Audit_Finish_hasDraft);
		goodsMapper.updateByPrimaryKey(sourceGoods);
		if (flag) {
			// 商品更新成功，跳转到商品列表界面
			return "redirect:/product/index/0/1/prvious/3/-1/1";
		} else {
			// 商品更新失败，重新跳转到编辑商品界面
			return "redirect:/product/toProductEdit/" + Product.getId();
		}
//		boolean flag = goodsService.updateGoods(Product);
////		sourceGoods.setAuditStatus(ConstantsSql.Audit_Finish_hasDraft);
////		goodsMapper.updateByPrimaryKey(sourceGoods);
//		if (flag) {
//			// 商品更新成功，跳转到商品列表界面
//			mv.addObject("resultMsg", "修改成功");
////			return "redirect:/product/index/0/1/prvious/3/-1/1";
//		} else {
//			// 商品更新失败，重新跳转到编辑商品界面
//			mv.addObject("resultMsg", "修改失败");
////			return "redirect:/product/toProductEdit/" + Product.getId();
//		}
//		//跳转到首页
//	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious/3/-1/1" );
//	  		mv.setView(redirectView);
//		}
//		return mv;
	}

	/**
	 * 通过idList返回商品信息
	 * 
	 * @param idList
	 * @return
	 * @author waiting
	 */
	@RequestMapping("/getProductListByIdList")
	@ResponseBody
	public List<GoodsList> getProductListByIdList(String idList) {
		String[] idListArray = idList.split("_");
		List<GoodsList> returnGoodsList = new ArrayList<GoodsList>();
		for (int i = 1; i < idListArray.length; i++) {
			int id = Integer.parseInt(idListArray[i]);
			GoodsList goods = goodsService.getGoodsDetail(id);
			returnGoodsList.add(goods);
		}
		return returnGoodsList;
	}

	/**
	 * 批量更新商品的销售状态（转成普通和新品 ）
	 * 
	 * @param idList
	 * @param saleStatus
	 * @return
	 * @author waiting
	 */
	@RequestMapping("/updateProductSalStatusByBath")
	@Transactional
	@ResponseBody
	public Map<String, String> updateProductSalStatusByBath(String idList,
			Integer saleStatus) {
		Map<String, String> result = new HashMap<String, String>();
		String[] idListArray = idList.split("_");
		boolean flag = false;
		List<Integer> pushList = new ArrayList<Integer>();
		for (int i = 1; i < idListArray.length; i++) {
			int id = Integer.parseInt(idListArray[i]);
			flag = goodsService.updateGoodsSaleStatus(id, saleStatus, null,
					null, null);
			pushList.add(id);
		}
		// 新品推送
		if (saleStatus == ConstantsSql.NEWGOODS) {
			pushService.newSale(pushList, "");
		}
		if (flag) {
			result.put("msg", "修改成功");
		} else {
			result.put("msg", "修改失败");
		}
		return result;
	}

	/**
	 * 批量更新商品的销售状态（转成特价和限时 ）
	 * 
	 * @param jsonList
	 * @return
	 */
	@RequestMapping("/updateProductSalStatusByBathPrice")
	@ResponseBody
	// @Transactional
	public Map<String, String> updateProductSalStatusByBathPrice(
			String jsonList, int saleStatus, Date endTime, Date openTime) {
		System.out.println();
		Map<String, String> result = new HashMap<String, String>();
		StringBuffer jsonListbuffer = new StringBuffer(jsonList);
		List<GoodsPriceModel> goodsPriceList = new ArrayList<GoodsPriceModel>();

		int idIndex = jsonListbuffer.indexOf("id");// -2;
		int spIndex = jsonListbuffer.indexOf("specialPrice");
		int fanIndex = jsonListbuffer.indexOf("}");// 第一个'}'的位置
		while (idIndex > 0) {
			GoodsPriceModel goodsPriceModel = new GoodsPriceModel();
			int id = Integer.parseInt(jsonListbuffer.substring(idIndex + 5,
					spIndex - 3));

			double specialPrice = Double.parseDouble(jsonListbuffer.substring(
					spIndex + 15, fanIndex - 1));

			jsonListbuffer.replace(idIndex - 6, fanIndex + 2, "");

			goodsPriceModel.setId(id);
			goodsPriceModel.setSpecialPrice(specialPrice);
			goodsPriceList.add(goodsPriceModel);
			idIndex = jsonListbuffer.indexOf("id");// -2;
			spIndex = jsonListbuffer.indexOf("specialPrice");
			fanIndex = jsonListbuffer.indexOf("}");// 第一个'}'的位置
		}
		if (saleStatus != ConstantsSql.LIMITATIONTIME) {
			endTime = openTime = null;
		}
		boolean flag = false;
		List<Integer> pushList = new ArrayList<Integer>();
		for (GoodsPriceModel goodsPriceModel : goodsPriceList) {
			flag = goodsService.updateGoodsSaleStatus(goodsPriceModel.getId(),
					saleStatus, endTime, openTime,
					goodsPriceModel.getSpecialPrice());
			pushList.add(goodsPriceModel.getId());
			if (!flag) {
				break;
			}
		}
		// 推送
		if (saleStatus == ConstantsSql.SPECIALPRICE) {// 特价
			pushService.specalOffer(pushList, null);
		} else {// 限时
				// 时间转字符串
			String sendTime = DateTag.dateTimaFormat(openTime);
			pushService.flashSale(pushList, sendTime);
		}
		if (flag) {
			result.put("msg", "修改成功");
		} else {
			result.put("msg", "修改失败");
		}
		return result;
	}

	/**
	 * 单个商品统计销售额
	 * 
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @param status
	 * @param timeFlag
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/productStatistics/{allPages}/{currentPage}/{type}/{status}/{starttime}/{endtime}/{goodsId}")
	public ModelAndView productStatistics(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime")String endtime,
			@PathVariable(value = "goodsId") int goodsId) {
		ModelAndView modelAndView = new ModelAndView(
				Constants.PRODUCT_SALES_STATISTICS);
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		statisticsService.productStatictics(modelAndView, currentPage,
				pageSize, status, starttime,endtime, goodsId);
		// 查询商品名称
		GoodsList goodsList = goodsService.getGoodsDetail(goodsId);
		modelAndView.addObject("goodsName", goodsList.getName());
		modelAndView.addObject("goodsId", goodsId);
		modelAndView.addObject("stock", goodsList.getStock());
		modelAndView.addObject("unit", goodsList.getUnit());
		modelAndView.addObject("status", status);
		// 返回时间
				if ((starttime != null) && (!starttime.equals("0"))) {
					starttime = starttime.replaceFirst("-", "年");
					starttime = starttime.replace("-", "月");
					starttime = starttime + "日";
					modelAndView.addObject("starttime", "\"" + starttime + "\"");
				}
				else{
					modelAndView.addObject("starttime", "0");
				}
				if ((endtime != null) && (!endtime.equals("0"))) {
					endtime = endtime.replaceFirst("-", "年");
					endtime = endtime.replace("-", "月");
					endtime = endtime + "日";
					modelAndView.addObject("endtime", "\"" + endtime + "\"");
				}
				else{
					modelAndView.addObject("endtime", "0");
				}
		return modelAndView;
	}

	/**
	 * 商品销售统计 图标数据
	 * 
	 * @param status
	 * @param timeFlag
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/echartsProductStatistics/{status}/{starttime}/{endtime}/{goodsId}")
	public Map<String, List<String>> echartsProductStatistics(
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime")String endtime,
			@PathVariable(value = "goodsId") int goodsId) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		statisticsService.echartsProductStatistics(status, starttime,endtime, returnMap,
				goodsId);
		return returnMap;
	}

	@RequestMapping(value = "/exportProductStatictics/{status}/{starttime}/{endtime}/{goodsId}")
	public void exportProductStatistics(
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value = "endtime") String endtime,
			@PathVariable(value = "goodsId") int goodsId,
			HttpServletResponse response) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		GoodsList goodsList = goodsService.getGoodsDetail(goodsId);
		String fileNameString = goodsList.getName() + "商品销售额";
		statisticsService.exportExcelTotalSales(goodsId,status, starttime,endtime,
				fileNameString, response);
	}

	/**
	 * 运费模板变成删除待审核状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/updateFreight/{id}")
	@ResponseBody
	public Map<String, String> updateFreight(@PathVariable(value = "id") int id) {
		Map<String, String> returnMap = new HashMap<String, String>();
		boolean flag = freightService.deleteAuditFreight(id);
		// 将原商品修改成编辑待审核状态
		if (flag) {
			returnMap.put("msg", "删除成功");
		} else {
			returnMap.put("msg", "删除失败");
		}
		return returnMap;
	}

	/**
	 * 商品进货界面
	 * 
	 * @return
	 */
	@RequestMapping("/inputproduct/{id}")
	public ModelAndView inputProduct(HttpServletRequest request,
			@PathVariable(value = "id") int id) {
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_INPUT);
		Goods goods = goodsMapper.selectByPrimaryKey(id);
		modelAndView.addObject("goods", goods);
		return modelAndView;
	}
	/**
	 * 商品运费修改
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/freightEdit/{id}")
	public ModelAndView freightEdit(HttpServletRequest request,
			@PathVariable(value = "id") int id) {
		ModelAndView modelAndView = new ModelAndView(Constants.FREIGHTEDIT);
		Fre fre = freightService.getFreightById(id);
		modelAndView.addObject("freight", fre);
		return modelAndView;
	}
	@RequestMapping("/freightEditPost/{id}")
	public ModelAndView freightEditPost(HttpServletRequest request,Fre fre) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/product/getFreightList");
//		System.out.println("id="+fre.getId());
//		System.out.println("首重="+fre.getFirstweight());
//		System.out.println("首重价格="+fre.getFirstweightprice());
//		System.out.println("续重="+fre.getAdditionweight());
//		System.out.println("续重价格="+fre.getAdditionweightprice());
//		System.out.println("类型="+fre.getType());
		int yuanId = fre.getId();
		fre.setSourceId(fre.getId());
		fre.setAuditStatus(Constants.FREIGHT_DRAFT);
		fre.setId(null);
		freightService.updateAuditStatusById(yuanId,Constants.FREIGHT_HASDRAFT);
		freightService.addFreightDraft(fre);
		return modelAndView;
	}
	@RequestMapping("/freightEditReview")
	public ModelAndView freightEditReview() {
		ModelAndView mv = new ModelAndView(Constants.FREIGHTREVIEW);
		List<Fre> list = freightService.getUnreviewedFreightList();
		mv.addObject("freights", list);
		return mv;
	}
	@RequestMapping("/judgeFreightDraft/{id}")
	public ModelAndView judgeFreightDraft(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/product/freightEditReview");
		freightService.doReview(id);
		return mv;
	}
	@RequestMapping("/deleteFreightDraft/{id}")
	public ModelAndView deleteFreightDraft(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/product/freightEditReview");
		freightService.deleteFreightDraft(id);
		return mv;
	}
	@RequestMapping("/getFreightList")
	public ModelAndView getFreightList() {
		ModelAndView mv = new ModelAndView(Constants.FREIGHTINDEX);
		mv.addObject("freights", freightService.getFreightList());
		return mv;
	}
	/**
	 * 保存商品进货
	 * 
	 * @param inputGoods
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/inputProductPost")
	public ModelAndView inputProductPost(InputGoods inputGoods,
			HttpServletRequest request) throws UnsupportedEncodingException {
		Boolean flag = goodsInputService.inputGoods(inputGoods);
		// 添加成功
		if (flag) {
			ModelAndView modelAndView = this.productIndex(0, 1, "prvious", 3,
					-1, "1");
			return modelAndView;
		} else {
			ModelAndView modelAndView = this.inputProduct(request,
					inputGoods.getGoods_id());
			return modelAndView;
		}
	}
	/**
	 * 进货审核界面
	 * @param allPages
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value="/auditInputGoods/{allPages}/{currentPage}/{type}")
	public ModelAndView auditInputGoods(@PathVariable(value="allPages") int allPages,
			@PathVariable(value="currentPage") int currentPage,
			@PathVariable(value="type")String type){
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_AUDIT_INPUT);

		Page page = PageHelper.startPage(currentPage, pageSize, "id");
		List<InputGoodsView> inputGoodsViewsList=goodsInputService.getInputGoods(0);

		modelAndView.addObject("inputGoodsViewsList", inputGoodsViewsList);
		// 总页数
		allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		return modelAndView;
	}
	@ResponseBody
	@RequestMapping("/auditInputGoodsStatus")
	public Map<String, String> auditInputGoodsStatus(int id, int status) {
		boolean flag = goodsInputService.auditInputGoods(id, status);		
		Map<String, String> returnMap = new HashMap<String, String>();
		if (flag) {
			returnMap.put("msg", "修改成功");
		} else {
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}
	/**
	 * 单个商品统计销售额
	 * 
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @param status
	 * @param timeFlag
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/productInputStatistics/{allPages}/{currentPage}/{type}/{starttime}/{endtime}/{goodsId}")
	public ModelAndView productInputStatistics(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime")String endtime,
			@PathVariable(value = "goodsId") int goodsId) {
		ModelAndView modelAndView = new ModelAndView(
				Constants.PARENT_INPUT_STATISTICS);
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		statisticsService.productInputStatics(modelAndView, currentPage,
				pageSize, starttime,endtime, goodsId);
		// 查询商品名称
		GoodsList goodsList = goodsService.getGoodsDetail(goodsId);
		modelAndView.addObject("goodsName", goodsList.getName());
		modelAndView.addObject("goodsId", goodsId);
		modelAndView.addObject("stock", goodsList.getStock());
		modelAndView.addObject("unit", goodsList.getUnit());
		// 返回时间
				if ((starttime != null) && (!starttime.equals("0"))) {
					starttime = starttime.replaceFirst("-", "年");
					starttime = starttime.replace("-", "月");
					starttime = starttime + "日";
					modelAndView.addObject("starttime", "\"" + starttime + "\"");
				}
				else{
					modelAndView.addObject("starttime", "0");
				}
				if ((endtime != null) && (!endtime.equals("0"))) {
					endtime = endtime.replaceFirst("-", "年");
					endtime = endtime.replace("-", "月");
					endtime = endtime + "日";
					modelAndView.addObject("endtime", "\"" + endtime + "\"");
				}
				else{
					modelAndView.addObject("endtime", "0");
				}
		return modelAndView;
	}
	/**
	 * 商品进货统计 图形展示
	 * @param starttime
	 * @param endtime
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/echartsProductInputStatistics/{starttime}/{endtime}/{goodsId}")
	public Map<String, List<String>> echartsProductInputStatistics(
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime")String endtime,
			@PathVariable(value = "goodsId") int goodsId) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		statisticsService.echartsProductInputStatistics(starttime,endtime, returnMap,
				goodsId);
		return returnMap;
	}
	/**
	 * 商品进货统计 导出报表
	 * @param starttime
	 * @param endtime
	 * @param goodsId
	 * @param response
	 */
	@RequestMapping(value = "/exportProductInputStatistics/{starttime}/{endtime}/{goodsId}")
	public void exportProductInputStatistics(
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value = "endtime") String endtime,
			@PathVariable(value = "goodsId") int goodsId,
			HttpServletResponse response) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		GoodsList goodsList = goodsService.getGoodsDetail(goodsId);
		String fileNameString = goodsList.getName() + "商品进货统计";
		statisticsService.exportExcelProductInput(starttime,endtime,goodsId,
				fileNameString, response);
	}
}
