package com.tiny.kv.hbase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * KV库 HBase相关操作
 * Singleton
 */
public class HBaseClient extends AbstractHBaseClient {

    /**
     * 根据单个rowkey查询数据
     *
     * @param tableName
     * @param rowkey
     * @param families
     * @return Map<family,<column,value>>
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> querySingleRow(String tableName, byte[] rowkey,
                                                           List<byte[]> families) throws Exception {
        Map<byte[], List<byte[]>> map = null;
        if (CollectionUtils.isNotEmpty(families)) {
            map = new HashMap<>(families.size());
            for (byte[] family : families) {
                map.put(family, ListUtils.EMPTY_LIST);
            }
        }
        return querySingleRow(tableName, rowkey, map);
    }

    /**
     * 根据单个rowkey查询数据
     *
     * @param tableName    表名
     * @param rowkey       row key
     * @param qualifierMap Map<family,List<column>>
     * @return Map<family,<column,value>>
     * @throws Exception
     */
    public Map<String, Map<String, String>> querySingleRow(String tableName, byte[] rowkey,
                                                           Map<byte[], List<byte[]>> qualifierMap) throws Exception {
        return querySingleRow(tableName, rowkey, qualifierMap, null);
    }

    /**
     * 根据单个rowkey查询数据
     *
     * @param tableName    表名
     * @param rowkey       row key
     * @param qualifierMap Map<family,List<column>>
     * @param filter       过滤器
     * @return Map<family,<column,value>>
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> querySingleRow(String tableName, byte[] rowkey,
                                                           Map<byte[], List<byte[]>> qualifierMap, Filter filter) throws Exception {
        List<byte[]> rowkeys = new ArrayList<>(1);
        rowkeys.add(rowkey);
        Map<String, Map<String, Map<String, String>>> multiRowkeys
                = queryMultiRows(tableName, rowkeys, qualifierMap, filter);
        if (MapUtils.isNotEmpty(multiRowkeys)) {
            return multiRowkeys.get(Bytes.toString(rowkey));
        }
        return MapUtils.EMPTY_MAP;
    }

    /**
     * 查询hbase基础方法
     * 查询多rowkey、多列簇、多列下的数据
     *
     * @param tableName    表名
     * @param rowkeys      rowkey数组
     * @param qualifierMap Map<family,List<column>>
     * @param filter
     * @return Map<rowkey,Map<family,<column,value>>>
     * @throws Exception
     */
    public Map<String, Map<String, Map<String, String>>> queryMultiRows(String tableName, List<byte[]> rowkeys
            , Map<byte[], List<byte[]>> qualifierMap, Filter filter) throws Exception {
        List<Get> gets = new ArrayList<>();
        for (byte[] rowkey : rowkeys) {
            if (MapUtils.isEmpty(qualifierMap)) {
                gets.add(generateGet(rowkey, null, null, filter));
                continue;
            }
            for (Entry<byte[], List<byte[]>> family : qualifierMap.entrySet()) {
                gets.add(generateGet(rowkey, family.getKey(), family.getValue(), filter));
            }
        }
        return queryMultiRows(tableName, gets);
    }

    /**
     * 根据tableName、列族等插入单行多列数据<br/>
     *
     * @param tableName 表名
     * @param rowkey    row key
     * @param map       Map<family, Map<column, value>>
     * @throws Exception
     */
    public void insertSingleRow(String tableName, byte[] rowkey, Map<byte[], Map<byte[], byte[]>> map) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        List<Put> list = generatePut(rowkey, map);
        if (CollectionUtils.isNotEmpty(list)) {
            table.put(list);
        }
    }

    /**
     * 删除单行数据
     *
     * @param tableName 表名
     * @param rowkey    row key
     * @param map       Map<family,column>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void deleteSingleRow(String tableName, byte[] rowkey, Map<byte[], byte[]> map) throws Exception {
        if (map == null) {
            map = MapUtils.EMPTY_MAP;
        }
        Map<byte[], Map<byte[], byte[]>> nestMap = new HashMap<>();
        nestMap.put(rowkey, map);
        deleteMultiRows(tableName, nestMap);
    }

    /**
     * 删除多行数据
     *
     * @param tableName
     * @param map<rowkey,<family,column>>对照关系
     * @throws Exception
     */
    public void deleteMultiRows(String tableName, Map<byte[], Map<byte[], byte[]>> map) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        if (MapUtils.isEmpty(map)) {
            return;
        }
        List<Delete> list = new ArrayList<>();
        // 循环行
        for (Entry<byte[], Map<byte[], byte[]>> row : map.entrySet()) {
            if (row.getKey() == null) {
                continue;
            }
            Delete delete = new Delete(row.getKey());
            Map<byte[], byte[]> colMap = row.getValue();
            if (MapUtils.isNotEmpty(colMap)) {
                for (Entry<byte[], byte[]> colum : colMap.entrySet()) {
                    if (ArrayUtils.isNotEmpty(colum.getKey())) {
                        if (ArrayUtils.isNotEmpty(colum.getValue())) {
                            delete.addColumn(colum.getKey(), colum.getValue());
                        } else {
                            delete.addFamily(colum.getKey());
                        }
                    }
                }
            }
            list.add(delete);
        }
        table.delete(list);
    }

}
