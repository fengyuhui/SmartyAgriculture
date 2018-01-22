package cn.bupt.smartyagl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.dao.autogenerate.HotSearchMapper;
import cn.bupt.smartyagl.entity.autogenerate.HotSearch;
import cn.bupt.smartyagl.entity.autogenerate.HotSearchExample;
import cn.bupt.smartyagl.service.ISearchService;

@Service
public class SearchServiceImpl implements ISearchService{

	@Autowired
	HotSearchMapper hotSearchMapper;
	@Override
	public String hotSearch(Integer num) {
		try{
			HotSearchExample ae = new HotSearchExample();
//			Criteria cta = ae.createCriteria();
			String nau = new String();
			Page page = PageHelper.startPage(1, num, "frequency DESC");
			List<HotSearch> list = hotSearchMapper.selectByExample(ae);
			for(HotSearch hotSearch : list){ 

			hotSearch.getContent();
			String content = hotSearch.getContent();	
			nau += content+",";
			}
		nau=nau.substring(0,nau.lastIndexOf(","));		
		return nau;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

//	@Override
//	public String searchHistory() {
//		try{
//			HotSearchExample ae = new HotSearchExample();
////			Criteria cta = ae.createCriteria();
//			String nau = new String();
//			Page page = PageHelper.startPage(1, 10, "time DESC");
//			List<HotSearch> list = hotSearchMapper.selectByExample(ae);
//			for(HotSearch hotSearch : list){ 
//
//			hotSearch.getContent();
//			String content = hotSearch.getContent();	
//			nau += content+",";
//			}
//		nau=nau.substring(0,nau.lastIndexOf(","));		
//		return nau;
//		}catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//	}
//	
//	@Override
//	public String deleteHistory() {
//		try{
//			HotSearchExample ae = new HotSearchExample();
////			Criteria cta = ae.createCriteria();
//			String nau = new String();
//			Page page = PageHelper.startPage(1, 10, "frequency DESC");
//			List<HotSearch> list = hotSearchMapper.selectByExample(ae);
//			for(HotSearch hotSearch : list){ 
//
//			hotSearch.getContent();
//			String content = hotSearch.getContent();	
//			nau += content+",";
//			}
//		nau=nau.substring(0,nau.lastIndexOf(","));		
//		return nau;
//		}catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//	}
	
	@Override
	public HotSearch getcontentBynum(Integer num, Integer phraseId) {//unuse
		// TODO Auto-generated method stub
		HotSearch hotSearch=hotSearchMapper.selectByPrimaryKey(phraseId);
		return hotSearch;
	}
}
