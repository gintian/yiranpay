package com.yiranpay.web.controller.payorder;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiranpay.common.annotation.Log;
import com.yiranpay.common.core.controller.BaseController;
import com.yiranpay.common.core.domain.AjaxResult;
import com.yiranpay.common.core.page.TableDataInfo;
import com.yiranpay.common.enums.BusinessType;
import com.yiranpay.common.utils.poi.ExcelUtil;
import com.yiranpay.payorder.domaindo.PaySignInfoDO;
import com.yiranpay.payorder.service.IPaySignInfoService;
/**
 * 签约 信息操作处理
 * 
 * @author yiran
 * @date 2019-07-13
 */
@Controller
@RequestMapping("/payorder/paySignInfo")
public class PaySignInfoController extends BaseController
{
    private String prefix = "payorder/paySignInfo";
	
	@Autowired
	private IPaySignInfoService paySignInfoService;
	
	@RequiresPermissions("payorder:paySignInfo:view")
	@GetMapping()
	public String paySignInfo()
	{
	    return prefix + "/paySignInfo";
	}
	
	/**
	 * 查询签约列表
	 */
	@RequiresPermissions("payorder:paySignInfo:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(PaySignInfoDO paySignInfo)
	{
		startPage();
        List<PaySignInfoDO> list = paySignInfoService.selectPaySignInfoList(paySignInfo);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出签约列表
	 */
	@RequiresPermissions("payorder:paySignInfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PaySignInfoDO paySignInfo)
    {
    	List<PaySignInfoDO> list = paySignInfoService.selectPaySignInfoList(paySignInfo);
        ExcelUtil<PaySignInfoDO> util = new ExcelUtil<PaySignInfoDO>(PaySignInfoDO.class);
        return util.exportExcel(list, "paySignInfo");
    }
	
	/**
	 * 新增签约
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存签约
	 */
	@RequiresPermissions("payorder:paySignInfo:add")
	@Log(title = "签约", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(PaySignInfoDO paySignInfo)
	{		
		return toAjax(paySignInfoService.insertPaySignInfo(paySignInfo));
	}

	/**
	 * 修改签约
	 */
	@GetMapping("/edit/{signReqId}")
	public String edit(@PathVariable("signReqId") Integer signReqId, ModelMap mmap)
	{
		PaySignInfoDO paySignInfo = paySignInfoService.selectPaySignInfoById(signReqId);
		mmap.put("paySignInfo", paySignInfo);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存签约
	 */
	@RequiresPermissions("payorder:paySignInfo:edit")
	@Log(title = "签约", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PaySignInfoDO paySignInfo)
	{		
		return toAjax(paySignInfoService.updatePaySignInfo(paySignInfo));
	}
	
	/**
	 * 删除签约
	 */
	@RequiresPermissions("payorder:paySignInfo:remove")
	@Log(title = "签约", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(paySignInfoService.deletePaySignInfoByIds(ids));
	}
	
}
