package com.tiny.kv.api.service;

import com.tiny.kv.api.model.ProdDimFacade;

import java.util.List;

/**
 * 产品维度信息查询
 * @author tiny
 *
 */
public interface ProdDimService {

	/**
	 * 
	 * <b><i>获取商品维度的相关数据</i></b><br>
	 * @param pid 商品ID
	 * @param groupAliases 组别名汇总
	 * @return {@link ProdDimFacade} 满足条件的结果封装的bean
	 * @throws Exception
	 * @description
	 */
	public ProdDimFacade queryProdData(Long pid, Long groupAliases) throws Exception;

	/**
	 * 
	 * <b><i>获取多个商品的相关数据</i></b><br>
	 * @param pids 商品ID列表
	 * @param groupAliases 组别名汇总
	 * @return List<{@link ProdDimFacade}> 满足条件的结果封装的bean
	 * @throws Exception
	 * @description
	 */
	public List<ProdDimFacade> queryProdData(List<Long> pids, Long groupAliases) throws Exception;
	
	
	/**
	 * TODO
	 * 还有增、删、改操作，稍后继续
	 */
	
}
