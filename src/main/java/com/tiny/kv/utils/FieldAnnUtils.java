package com.tiny.kv.utils;

import com.tiny.kv.annotation.Group;
import com.tiny.kv.annotation.StorageMedium;
import com.tiny.kv.annotation.Table;
import com.tiny.kv.model.GroupContext;
import com.tiny.kv.model.QueryContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 解析field上的注解{@link Group},{@link Table}的工具类
 *
 * @author tiny
 */
public class FieldAnnUtils {

    private static final Map<Class<?>, Map<Long, GroupContext>> groupAliasContextMap = new HashMap<>();

    public static Map<Long, GroupContext> getGroupFromClazz(Class<?> clazz) {
        Map<Long, GroupContext> map = groupAliasContextMap.get(clazz);
        if (MapUtils.isNotEmpty(map)) {
            return map;
        }
        synchronized (groupAliasContextMap) {
            // 防呆
            Map<Long, GroupContext> rtn = groupAliasContextMap.get(clazz);
            if (MapUtils.isNotEmpty(rtn)) {
                return rtn;
            }
            // get annotation value
            Field[] fields = clazz.getDeclaredFields();
            rtn = new HashMap<>();
            for (Field field : fields) {
                field.setAccessible(true);
                Annotation[] anns = field.getAnnotations();
                GroupContext query = new GroupContext();
                Long key = null;
                for (Annotation ann : anns) {
                    if (ann instanceof Group) {
                        Group group = (Group) ann;
                        String name = group.name();
                        if (StringUtils.isNotEmpty(name)) {
                            String[] names = name.split(":");
                            if (names.length > 1) {
                                query.setGroupName(names[0]);
                                query.setColumnName(names[1]);
                            } else {
                                query.setGroupName(name);
                            }
                        }
                        key = group.alias();
                    } else if (ann instanceof Table) {
                        Table table = (Table) ann;
                        query.setTableName(table.name());
                        query.setTableType(table.type());
                    }
                }
                if (key != null) {
                    rtn.put(key, query);
                }
            }
            groupAliasContextMap.put(clazz, rtn);
            return rtn;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<QueryContext> generateQueries(List<GroupContext> groups) {
        Map<String, QueryContext> contentMap = new HashMap<>();
        for (GroupContext group : groups) {
            String tableName = group.getTableName();
            String groupName = group.getGroupName();
            String columnName = group.getColumnName();
            StorageMedium tableType = group.getTableType();
            // get group and column name
            String key = tableName + "-" + tableType.name();
            QueryContext query = contentMap.get(key);
            if (query == null) {
                query = new QueryContext();
                query.setTableName(group.getTableName());
                query.setType(tableType);
                Map<String, List<String>> columnMap = query.getColumnMap();
                if (StringUtils.isNotEmpty(columnName)) {
                    List<String> colList = new ArrayList<>();
                    colList.add(columnName);
                    columnMap.put(groupName, colList);
                } else {
                    columnMap.put(groupName, ListUtils.EMPTY_LIST);
                }
                contentMap.put(key, query);
            } else {
                Map<String, List<String>> colMap = query.getColumnMap();
                List<String> colList = colMap.get(groupName);
                if (CollectionUtils.isNotEmpty(colList)) {
                    if (StringUtils.isNotEmpty(columnName)) {
                        colList.add(columnName);
                    }
                } else {
                    if (StringUtils.isNotEmpty(columnName)) {
                        colList = new ArrayList<>();
                        colList.add(columnName);
                        colMap.put(groupName, colList);
                    } else {
                        colMap.put(groupName, ListUtils.EMPTY_LIST);
                    }
                }
            }
        }
        return new ArrayList<>(contentMap.values());
    }

}
