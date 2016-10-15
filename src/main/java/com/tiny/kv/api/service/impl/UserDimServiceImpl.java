package com.tiny.kv.api.service.impl;

import com.tiny.kv.annotation.StorageMedium;
import com.tiny.kv.api.model.UserDimFacade;
import com.tiny.kv.api.service.KVLowLevelService;
import com.tiny.kv.api.service.UserDimService;
import com.tiny.kv.api.utils.ProdDimUtils;
import com.tiny.kv.api.utils.UserDimUtils;
import com.tiny.kv.model.QueryContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <pre>
 * high-level interface <br>
 * 不同维度数据单独提供接口文档，调用方不需要知道底层表&表结构；
 * 后期kv维护会更灵活
 * </pre>
 * @author tiny
 *
 */
public class UserDimServiceImpl implements UserDimService {
	
	private KVLowLevelService lowLevelService;

	public UserDimFacade queryUserData(String userId, Long groupAliases) throws Exception {
		List<QueryContext> queries = UserDimUtils.parseAliasToQueries(groupAliases, UserDimFacade.class);
		UserDimFacade facade = new UserDimFacade();
		if(CollectionUtils.isNotEmpty(queries)){
			for (QueryContext query : queries) {
				if(StorageMedium.HBASE.equals(query.getType())){
					Map<String, Map<String, String>> result
						= lowLevelService.queryData(query.getTableName(), userId, query.getColumnMap());
					// value copy to bean
					UserDimUtils.copyProperties(result,facade);
				}else if(StorageMedium.REDIS.equals(query.getType())){
					// other logic
				}
			}
		}
		return facade;
	}

	public List<UserDimFacade> queryUserData(List<String> userIds, Long groupAliases) throws Exception {
		List<QueryContext> queries = ProdDimUtils.parseAliasToQueries(groupAliases, UserDimFacade.class);
		List<UserDimFacade> list = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(queries)){
			for (QueryContext query : queries) {
				if(StorageMedium.HBASE.equals(query.getType())){
					Map<String, Map<String, Map<String, String>>> result
						= lowLevelService.queryData(query.getTableName(), userIds, query.getColumnMap());
					// value copy to bean
					for (Entry<String, Map<String, Map<String, String>>> entry : result.entrySet()) {
						UserDimFacade facade = new UserDimFacade();
						UserDimUtils.copyProperties(entry.getValue(),facade);
						list.add(facade);
					}
				}else if(StorageMedium.REDIS.equals(query.getType())){
					// other logic
				}
			}
		}
		return list;
	}

	public void setLowLevelService(KVLowLevelService lowLevelService) {
		this.lowLevelService = lowLevelService;
	}
	
}
