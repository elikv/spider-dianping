package com.dianping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.RankShopInfo;
import com.dianping.model.ShopStarEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("")
public class RecommendController {
	@Autowired
	private DianPingDAO dianpingDao;
	
	
	@RequestMapping("/recommend")
	public String index(Model model ,@RequestParam(required=true,defaultValue="1")int pageNum,
			@RequestParam(required=true,defaultValue="10")int pageSize){
		List<RankShopInfo> findRecommend = dianpingDao.findRecommend();
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<RankShopInfo> page= new PageInfo<RankShopInfo>(findRecommend);
		model.addAttribute("page", page);
		model.addAttribute("list", findRecommend);
		return "recommend/index";
	} 
	
	 
}
