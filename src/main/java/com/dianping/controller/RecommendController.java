package com.dianping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.RankShopInfo;
import com.dianping.service.DianPingServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("")
public class RecommendController {
	@Autowired
	private DianPingServiceImpl dianpingService;
	
	
	@RequestMapping(value="/recommend" ,method = RequestMethod.GET)
	public String index(Model model ,@RequestParam(required=true,defaultValue="1")int pageNum,
			@RequestParam(required=true,defaultValue="10")int pageSize,
			@RequestParam(required=false)String category,
			@RequestParam(required=false)String start,@RequestParam(required=false)String end){
		PageHelper.startPage(pageNum, pageSize);
		List<RankShopInfo> findRecommend = dianpingService.findRecommend(category,start,end);
		PageInfo<RankShopInfo> page= new PageInfo<RankShopInfo>(findRecommend);
		model.addAttribute("pageInfo", page);
		model.addAttribute("data", findRecommend);
		
		
		return "recommend/index";
	} 
	
	@RequestMapping(value="/topTen" ,method = RequestMethod.GET)
	public String topTen(Model model ,@RequestParam(required=true,defaultValue="1")int pageNum,
			@RequestParam(required=true,defaultValue="10")int pageSize,
			@RequestParam(required=false)String category,
			@RequestParam(required=false)String start,@RequestParam(required=false)String end){
		PageHelper.startPage(pageNum, pageSize);
		List<RankShopInfo> findRecommend = dianpingService.findRecommend(category,start,end);
		PageInfo<RankShopInfo> page= new PageInfo<RankShopInfo>(findRecommend);
		model.addAttribute("pageInfo", page);
		model.addAttribute("data", findRecommend);
		
		
		return "recommend/index";
	} 
	
	 
}
