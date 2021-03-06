package com.zlb.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zlb.common.vo.SysResult;
import com.zlb.pojo.ProductAttr;
import com.zlb.pojo.ProductScore;
import com.zlb.pojo.ProductSer;
import com.zlb.service.SeriousIllnessProductService;
/**
 * 重疾产品管理controller
 * @author sicle
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	@Autowired
	private SeriousIllnessProductService productService;
	/**
	 * 重疾产品页的跳转
	 * @return
	 */
	@RequestMapping("/seriousIllness.do")
	public String doSeriousIllnessIndex() {
		return "sys/serious_illness";
	}
	/**
	 * 产品添加
	 * @param productName
	 * @param scoreCompany
	 * @param scoreIllnessNum
	 * @param scoreIllnessProportion
	 * @param scoreIllnessTimes
	 * @param scoreDeadDuty
	 * @param scoreSeriousIllnessDuty
	 * @param scoreMildCaseNum
	 * @param scoreMildCaseTimes
	 * @param scoreMildCaseProportion
	 * @param attrCompany
	 * @param attrIllnessNum
	 * @param attrIllnessProportion
	 * @param attrIllnessTimes
	 * @param attrDeadDuty
	 * @param attrSeriousIllnessDuty
	 * @param attrMildCaseNum
	 * @param attrMildCaseTimes
	 * @param attrMildCaseProportion
	 * @return
	 */
	@RequestMapping(value="/doInsertSeriousIllnessProduct.do", method = RequestMethod.POST)
	@ResponseBody
	public SysResult doInsertSeriousIllnessProduct(String productName,String scoreCompany, 
			String scoreIllnessNum,String scoreIllnessProportion,String scoreIllnessTimes,
			String scoreDeadDuty,String scoreSeriousIllnessDuty, String scoreMildCaseNum,
			String scoreMildCaseTimes,String scoreMildCaseProportion,String attrCompany,
			String attrIllnessNum,String attrIllnessProportion,String attrIllnessTimes,
			String attrDeadDuty,String attrSeriousIllnessDuty,String attrMildCaseNum,
			String attrMildCaseTimes,String attrMildCaseProportion) {
		try {
			logger.info("---------新增产品");
			//对产品属性进行赋值
			ProductAttr productAttr=new ProductAttr();
			productAttr.setAttrCompany(attrCompany);
			productAttr.setAttrDeadDuty(attrDeadDuty);
			productAttr.setAttrIllnessNum(attrIllnessNum);
			productAttr.setAttrIllnessProportion(attrIllnessProportion);
			productAttr.setAttrIllnessTimes(attrIllnessTimes);
			productAttr.setAttrMildCaseNum(attrMildCaseNum);
			productAttr.setAttrMildCaseProportion(attrMildCaseProportion);
			productAttr.setAttrMildCaseTimes(attrMildCaseTimes);
			productAttr.setAttrSeriousIllnessDuty(attrSeriousIllnessDuty);
			//对产品评分进行赋值
			ProductScore productScore=new ProductScore();
			productScore.setScoreCompany(scoreCompany);
			productScore.setScoreDeadDuty(scoreDeadDuty);
			productScore.setScoreIllnessNum(scoreIllnessNum);
			productScore.setScoreIllnessProportion(scoreIllnessProportion);
			productScore.setScoreIllnessTimes(scoreIllnessTimes);
			productScore.setScoreMildCaseNum(scoreMildCaseNum);
			productScore.setScoreMildCaseTimes(scoreMildCaseTimes);
			productScore.setScoreMildCaseProportion(scoreMildCaseProportion);
			productScore.setScoreSeriousIllnessDuty(scoreSeriousIllnessDuty);
			logger.info("--------"+productAttr);
			logger.info("--------"+productScore);
			int result=productService.insertSeriousIllnessProduct(productAttr,productScore,productName);
			if(result==1) {
				return SysResult.build(200, "产品添加成功！");
			}
			return SysResult.build(201, "产品添加失败！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "产品添加失败！");
	}
	@RequestMapping("/seriousIllnessProduct/soonList.do")
	public String doSoonList() {
		return "sys/soon_list";
	}
	/**
	 * 分页查找待上线产品信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/doFindSoonList.do")
	@ResponseBody
	public SysResult doFindSoonList(Integer page,Integer rows) {
		logger.info("-----------doFindSoonList-----------");
		try {
			Map<String, Object> result=productService.findSoonList(page,rows);
			logger.info(result);
			return SysResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询失败");
	}
	/**
	 * 删除产品操作
	 * @param productId
	 * @param attrId
	 * @param scoreId
	 * @return
	 */
	@RequestMapping("/doDeleteObject.do")
	@ResponseBody
	public SysResult doDeleteObject(Integer productId,Integer attrId,Integer scoreId) {
		logger.info("---------------doDeleteObject------------");
		try {
			productService.deleteObjectById(productId,attrId,scoreId);
			return SysResult.build(200, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "删除失败！");
	}
	/**
	 * 产品上线操作
	 * @param productId
	 * @return
	 */
	@RequestMapping("/changeStaOnline.do")
	@ResponseBody
	public SysResult changeStaOnline(Integer productId) {
		logger.info("---------------changeStaOnline------------");
		try {
			productService.changeStaOnline(productId);
			return SysResult.build(200, "上线成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "上线失败！");
	}
	@RequestMapping("/seriousIllnessProduct/onlineList.do")
	public String onlineList() {
		return "sys/online_list";
	}
	/**
	 * 分页查找待上线产品信息
	 * @param page
	 * @param rows
	 * @return
	 */
	
	@RequestMapping("/doFindOnlineList.do")
	@ResponseBody
	public SysResult doFindOnlineList(Integer page,Integer rows) {
		logger.info("-----------doFindOnlineList-----------");
		try {
			Map<String, Object> result=productService.findOnlineList(page,rows);
			logger.info(result);
			return SysResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询失败");
	}
}
