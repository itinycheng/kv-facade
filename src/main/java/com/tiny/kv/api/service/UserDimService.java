package com.tiny.kv.api.service;

import com.tiny.kv.api.model.UserDimFacade;

import java.util.List;

/**
 * 产品维度信息查询
 * @author tiny
 *
 */
public interface UserDimService {

	/**
	 * 
	 * <b><i>获取商品维度的相关数据</i></b><br>
	 * @param userId 用户ID
	 * @param groupAliases 组别名汇总
	 * @return {@link com.tiny.kv.api.model.ProdDimFacade} 满足条件的结果封装的bean
	 * @throws Exception
	 * @description
	 */
	UserDimFacade queryUserData(String userId, Long groupAliases) throws Exception;

	/**
	 * 
	 * <b><i>获取多个商品的相关数据</i></b><br>
	 * @param userIds 用户ID列表
	 * @param groupAliases 组别名汇总
	 * @return List<{@link com.tiny.kv.api.model.UserDimFacade}> 满足条件的结果封装的bean
	 * @throws Exception
	 * @description
	 */
	List<UserDimFacade> queryUserData(List<String> userIds, Long groupAliases) throws Exception;
	
	
	/**
	 * TODO
	 * 还有增、删、改操作，稍后继续
	 * 
	 */
	

}
