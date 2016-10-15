package com.tiny.kv.api.service.impl;

import com.tiny.kv.annotation.StorageMedium;
import com.tiny.kv.api.model.ProdDimFacade;
import com.tiny.kv.api.service.KVLowLevelService;
import com.tiny.kv.api.service.ProdDimService;
import com.tiny.kv.api.utils.ProdDimUtils;
import com.tiny.kv.model.QueryContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;

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
public class ProdDimServiceImpl implements ProdDimService {
	
	private KVLowLevelService lowLevelService;
	
	public ProdDimFacade queryProdData(Long pid, Long groupAliases) throws Exception {
		if(pid == null || groupAliases == null){
			return null;
		}
		ProdDimFacade facade = new ProdDimFacade();
		List<QueryContext> queries = ProdDimUtils.parseAliasToQueries(groupAliases, ProdDimFacade.class);
		if(CollectionUtils.isNotEmpty(queries)){
			for (QueryContext query : queries) {
				if(StorageMedium.HBASE.equals(query.getType())){
					Map<String, Map<String, String>> result
						= lowLevelService.queryData(query.getTableName(), pid.toString(), query.getColumnMap());
					// value copy to bean
					facade.setProductId(pid);
					ProdDimUtils.copyProperties(result,facade);
				}else if(StorageMedium.REDIS.equals(query.getType())){
					// other logic
				}
			}
		}
		return facade;
	}

	public List<ProdDimFacade> queryProdData(List<Long> pids, Long groupAliases) throws Exception {
		if(pids == null || groupAliases == null){
			return null;
		}
		List<ProdDimFacade> facades = new ArrayList<>();
		// query action
		List<QueryContext> queries = ProdDimUtils.parseAliasToQueries(groupAliases, ProdDimFacade.class);
		if(CollectionUtils.isNotEmpty(queries)){
			// 类型转换
			List<String> ids = new ArrayList<>(pids.size());
			for (Long pid : pids) {
				ids.add(pid.toString());
			}
			// query 
			for (QueryContext query : queries) {
				if(StorageMedium.HBASE.equals(query.getType())){
					Map<String, Map<String, Map<String, String>>> result
						= lowLevelService.queryData(query.getTableName(), ids, query.getColumnMap());
					// value copy to bean
					for (Entry<String, Map<String, Map<String, String>>> entry : result.entrySet()) {
						ProdDimFacade facade = new ProdDimFacade();
						facade.setProductId(NumberUtils.toLong(entry.getKey(),0));
						ProdDimUtils.copyProperties(entry.getValue(),facade);
						facades.add(facade);
					}
				}else if(StorageMedium.REDIS.equals(query.getType())){
					// other logic
				}
			}
		}
		return facades;
	}

	public void setLowLevelService(KVLowLevelService lowLevelService) {
		this.lowLevelService = lowLevelService;
	}
	
}
