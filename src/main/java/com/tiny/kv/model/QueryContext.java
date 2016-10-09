package com.tiny.kv.model;

import com.tiny.kv.annotation.StorageMedium;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询结构
 * 
 * @author tiny
 *
 */
public class QueryContext {

	/**
	 * 表名
	 */
	private String tableName;

	private StorageMedium type;

	/**
	 * Map<family, column>
	 */
	private Map<String, List<String>> columnMap = new HashMap<>();
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public StorageMedium getType() {
		return type;
	}

	public void setType(StorageMedium type) {
		this.type = type;
	}

	public Map<String, List<String>> getColumnMap() {
		return columnMap;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryContext other = (QueryContext) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
