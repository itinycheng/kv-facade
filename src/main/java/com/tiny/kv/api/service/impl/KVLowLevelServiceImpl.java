package com.tiny.kv.api.service.impl;

import com.tiny.kv.api.service.KVLowLevelService;
import com.tiny.kv.hbase.HBaseClient;
import com.tiny.kv.utils.HBaseUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KVLowLevelServiceImpl implements KVLowLevelService {
	
	private HBaseClient hbaseClient = null;
	
	//private Cache cache;
	
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> queryData(String tableName, String key, List<String> groups) throws Exception {
		Map<String, List<String>> columnMap = new HashMap<>();
		for (String group : groups) {
			columnMap.put(group, ListUtils.EMPTY_LIST);
		}
		return queryData(tableName, key, columnMap);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> queryData(String tableName, String key, Map<String, List<String>> columnMap)
			throws Exception {
		List<String> keys = new ArrayList<>(1);
		keys.add(key);
		Map<String, Map<String, Map<String, String>>> result = queryData(tableName, keys, columnMap);
		if(MapUtils.isNotEmpty(result)){
			return result.get(key);
		}
		return MapUtils.EMPTY_MAP;
	}

	public Map<String, Map<String, Map<String, String>>> queryData(String tableName, List<String> keys,
			Map<String, List<String>> columnMap) throws Exception {
		if(StringUtils.isEmpty(tableName) || CollectionUtils.isEmpty(keys)){
			throw new IllegalArgumentException(" input params (tableName,keys) are not allowed to be null ! ");
		}
		return hbaseClient.queryMultiRows(tableName, HBaseUtils.toByteList(keys), HBaseUtils.toByteMap(columnMap), null);
	}
	
	public void insertData(String tableName, String key, Map<String, Map<String, String>> map) throws Exception {
		if(StringUtils.isEmpty(tableName) || StringUtils.isEmpty(key) || MapUtils.isEmpty(map)){
			throw new IllegalArgumentException(" input params (tableName,key,map) are not allowed to be null ! ");
		}
		hbaseClient.insertSingleRow(tableName, Bytes.toBytes(key), HBaseUtils.toByteNestMap(map));
	}
	
	public void deleteData(String tableName, String key, Map<String, String> map) throws Exception {
		Map<String, Map<String, String>> nestMap = new HashMap<>(1);
		nestMap.put(key, map);
		deleteData(tableName, nestMap);
	}
	
	public void deleteData(String tableName, Map<String, Map<String, String>> map) throws Exception {
		if(StringUtils.isEmpty(tableName) || MapUtils.isEmpty(map)){
			throw new IllegalArgumentException(" input params (tableName,map) are not allowed to be null ! ");
		}
		hbaseClient.deleteMultiRows(tableName, HBaseUtils.toByteNestMap(map));
	}

	public void setHbaseClient(HBaseClient hbaseClient) {
		this.hbaseClient = hbaseClient;
	}
	
}
