package com.tiny.kv.hbase;

import com.tiny.kv.utils.HBaseUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * KV HBase client
 */
abstract class AbstractHBaseClient {
	
	protected Connection conn;
	
	protected String hbaseConfig;
	
	protected void init(){
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream input = classloader.getResourceAsStream(hbaseConfig);
			Configuration conf = HBaseConfiguration.create();
			conf.addResource(input);
			conn = ConnectionFactory.createConnection(conf);
		} catch (Exception e) {
			throw new RuntimeException("create HBase Connection failed.");
		}
	}
	
	protected void close() throws Exception{
		conn.close();
	}


    /**
	 * 查询多行数据
     * @param tableName
     * @param gets
     * @return Map<rowKey,Map<family, Map<column, value>>>
     * @throws Exception
     */
	protected Map<String,Map<String, Map<String, String>>> queryMultiRows(String tableName, List<Get> gets)
			throws Exception {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Result[] resultList = table.get(gets);
		Map<String,Map<String, Map<String, String>>> resultMap = new HashMap<>();
		if (resultList != null && resultList.length > 0) {
			for (Result resultItem : resultList) {
				if (resultItem.getRow() == null) {
					continue;
				}
				String rowKey = Bytes.toString(resultItem.getRow());
				//Map<family,Map<qualifier,value>>
				NavigableMap<byte[],NavigableMap<byte[],byte[]>> noVersionMap = resultItem.getNoVersionMap();
				Map<String, Map<String, String>> nestMap = HBaseUtils.toStrNestMap(noVersionMap);
				resultMap.put(rowKey, nestMap);
			}
		}
		return resultMap;
	}

    /**
     * generate PUT
     * @param rowKey 行键
     * @param map
     * @return Map<family,<column,value>>
     */
	@SuppressWarnings({ "unchecked" })
	protected List<Put> generatePut(byte[] rowKey, Map<byte[], Map<byte[], byte[]>> map) {
		if (MapUtils.isEmpty(map)) {
			return Collections.EMPTY_LIST;
		}
		List<Put> list = new ArrayList<>();
		for (Entry<byte[], Map<byte[], byte[]>> famEntry : map.entrySet()) {
			Map<byte[], byte[]> colMap = famEntry.getValue();
			byte[] family = famEntry.getKey();
			if (MapUtils.isEmpty(colMap)) {
				continue;
			}
			Put put = new Put(rowKey);
			for (Entry<byte[], byte[]> colum : colMap.entrySet()) {
				if (colum.getKey() != null) {
					put.addColumn(family, colum.getKey(), colum.getValue());
				}
			}
			list.add(put);
		}
		return list;
	}

	/**
	 *
	 * @param rowkey 主键
	 * @param family 列簇
	 * @param qualifieres 限定名
	 * @param filter 过滤器
     * @return Get对象
     */
	protected Get generateGet(byte[] rowkey, byte[] family, List<byte[]> qualifieres, Filter filter) {
		// generate
		Get get = new Get(rowkey);
		if (filter != null) {
			get.setFilter(filter);
		}
		if(ArrayUtils.isEmpty(family)){
			return get;
		}
		if(CollectionUtils.isNotEmpty(qualifieres)){
			for (byte[] qualifier : qualifieres) {
				get.addColumn(family, qualifier);
			}
		}else{
			get.addFamily(family);
		}
		return get;
	}

	public void setHbaseConfig(String hbaseConfig) {
		this.hbaseConfig = hbaseConfig;
	}
	
}
