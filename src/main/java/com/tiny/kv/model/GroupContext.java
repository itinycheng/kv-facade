package com.tiny.kv.model;

import com.tiny.kv.annotation.StorageMedium;

/**
 * 查询结构
 * 
 * @author tiny
 *
 */
public class GroupContext {

	/**
	 * 表名
	 */
	private String tableName;

	private StorageMedium tableType;

	private String groupName;

	private String columnName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public StorageMedium getTableType() {
		return tableType;
	}

	public void setTableType(StorageMedium tableType) {
		this.tableType = tableType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
