package com.tiny.kv.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;
import java.util.Map.Entry;

/**
 * 数据类型转换的工具类<br/>
 * 包括将Map<byte[],byte[]>转换为Map<String,String>等工具方法
 *
 */
public class HBaseUtils {
    
    /**
     * 将由byte[]数组对象组成的map转为由String对象组成的map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toStrMap(Map<byte[], byte[]> bytesMap) {
        if (MapUtils.isEmpty(bytesMap)) {
            return Collections.EMPTY_MAP;
        }
        Map<String, String> stringMap = new HashMap<>();
        for (Entry<byte[], byte[]> entry : bytesMap.entrySet()) {
            byte[] key = entry.getKey();
            byte[] value = entry.getValue();
            if (key != null && value != null) {
                stringMap.put(Bytes.toString(key), Bytes.toString(value));
            }
        }
        return stringMap;
    }
    
    /**
     * 将byte元素转换成string
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Map<String, String>> toStrNestMap(Map<byte[], NavigableMap<byte[],byte[]>> bytesMap) {
        if (MapUtils.isEmpty(bytesMap)) {
            return Collections.EMPTY_MAP;
        }
        Map<String, Map<String, String>> stringMap = new HashMap<>();
        for (Entry<byte[], NavigableMap<byte[],byte[]>> entry : bytesMap.entrySet()) {
            byte[] key = entry.getKey();
            Map<byte[], byte[]> value = entry.getValue();
            if(MapUtils.isNotEmpty(value)){
            	Map<String, String> subMap = new HashMap<>();
            	for (Entry<byte[], byte[]> subEntry : value.entrySet()) {
            		subMap.put(Bytes.toString(subEntry.getKey()), Bytes.toString(subEntry.getValue()));
            	}
            	if(ArrayUtils.isNotEmpty(key)){
            		stringMap.put(Bytes.toString(key), subMap);
            	}
            }
        }
        return stringMap;
    }

    /**
     * 将byte元素转换成string
     */
    @SuppressWarnings("unchecked")
    public static Map<byte[], Map<byte[],byte[]>> toByteNestMap(Map<String, Map<String, String>> strMap) {
        if (MapUtils.isEmpty(strMap)) {
            return Collections.EMPTY_MAP;
        }
        Map<byte[], Map<byte[],byte[]>> byteMap = new HashMap<>();
        for (Entry<String, Map<String, String>> entry : strMap.entrySet()) {
            String key = entry.getKey();
            Map<String, String> value = entry.getValue();
            if(MapUtils.isNotEmpty(value)){
            	Map<byte[],byte[]> subMap = new HashMap<>();
            	for (Entry<String, String> subEntry : value.entrySet()) {
            		subMap.put(Bytes.toBytes(subEntry.getKey()), Bytes.toBytes(subEntry.getValue()));
            	}
            	if(StringUtils.isNotEmpty(key)){
            		byteMap.put(Bytes.toBytes(key), subMap);
            	}
            }
        }
        return byteMap;
    }
    
    @SuppressWarnings("unchecked")
	public static List<byte[]> toByteList(List<String> strList){
    	if(CollectionUtils.isEmpty(strList)){
    		return ListUtils.EMPTY_LIST;
    	}
    	List<byte[]> bytes = new ArrayList<>(strList.size());
    	for (String str : strList) {
    		bytes.add(Bytes.toBytes(str));
    	}
		return bytes;
    }
	
	@SuppressWarnings("unchecked")
	public static Map<byte[], List<byte[]>> toByteMap(Map<String, List<String>> columnMap){
    	if(MapUtils.isEmpty(columnMap)){
    		return MapUtils.EMPTY_MAP;
    	}
    	Map<byte[], List<byte[]>> byteMap = new HashMap<>(columnMap.size());
    	for (Entry<String, List<String>> entry : columnMap.entrySet()) {
			String key = entry.getKey();
			List<String> value = entry.getValue();
			if(CollectionUtils.isNotEmpty(value)){
				List<byte[]> list = new ArrayList<>(value.size());
				for (String str : value) {
					list.add(Bytes.toBytes(str));
				}
				byteMap.put(Bytes.toBytes(key), list);
			}else{
				byteMap.put(Bytes.toBytes(key), ListUtils.EMPTY_LIST);
			}
		}
		return byteMap;
    }
	
	
    
    
}
