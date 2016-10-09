package com.tiny.kv.api.service;

import java.util.List;
import java.util.Map;

public interface KVLowLevelService {

	/**
	 * 
	 * @param tableName 表名
	 * @param key 主键/行键
	 * @param groups 组名集合
	 * @return Map<group, Map<column,value>>
	 * @throws Exception
	 */
	Map<String, Map<String, String>> queryData(String tableName, String key, List<String> groups) throws Exception;
	
	/**
	 * 
	 * @param tableName 表名
	 * @param key 主键/行键
	 * @param columnMap Map<family,List<column>>
	 * @return Map<family, List<column>>
	 * @throws Exception 
	 */
	Map<String, Map<String, String>> queryData(String tableName, String key, Map<String, List<String>> columnMap) throws Exception;

	/**
	 * 
	 * @param tableName 表名
	 * @param keys 主键/行键集合
	 * @param columnMap Map<family,List<column>>
	 * @return Map<key, Map<family, Map<column, value>>>
	 * @throws Exception
	 */
	Map<String, Map<String, Map<String, String>>> queryData(String tableName, List<String> keys, Map<String, List<String>> columnMap) throws Exception;

	/**
	 * 插入单行数据
	 * 
	 * @param tableName 表名
	 * @param key 
	 * @param map Map<group,map<column,value>>
	 * @throws Exception
	 */
	void insertData(String tableName, String key, Map<String, Map<String, String>> map) throws Exception;

	/**
	 * 删除单行数据
	 * 
	 * @param tableName 表名
	 * @param key row key
	 * @param map Map<family,column>
	 * @throws Exception
	 */
	void deleteData(String tableName, String key, Map<String, String> map) throws Exception;

	/**
	 * 删除多行数据
	 * 
	 * @param tableName 表名
	 * @param map Map<key,<group,column>>
	 * @throws Exception
	 */
	void deleteData(String tableName, Map<String, Map<String, String>> map) throws Exception;

	
	
	

}
