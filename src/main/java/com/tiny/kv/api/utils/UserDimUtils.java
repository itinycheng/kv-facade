package com.tiny.kv.api.utils;

import com.tiny.kv.api.model.*;
import com.tiny.kv.model.GroupContext;
import com.tiny.kv.model.QueryContext;
import com.tiny.kv.utils.FieldAnnUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 用户表视图工具类
 *
 * @author tiny
 */
public class UserDimUtils {

    /**
     * @param alias
     * @return Map<databaseType,Map<group, column>>
     */
    @SuppressWarnings("unchecked")
    public static List<QueryContext> parseAliasToQueries(long alias, Class<?> clazz) {
        if (alias == 0 || clazz == null) {
            return ListUtils.EMPTY_LIST;
        }
        Map<Long, GroupContext> aliasMap = FieldAnnUtils.getGroupFromClazz(clazz);
        List<GroupContext> list = new ArrayList<>();
        if (isContainAction(alias)) {
            list.add(aliasMap.get(UserDimConsts.GROUP_ALIAS_ACTION));
        }
        if (isContainLabel(alias)) {
            list.add(aliasMap.get(UserDimConsts.GROUP_ALIAS_LABEL));
        }
        if (isContainCatePref(alias)) {
            list.add(aliasMap.get(UserDimConsts.GROUP_ALIAS_CATE_PREF));
        }
        if (isContainAttrPref(alias)) {
            list.add(aliasMap.get(UserDimConsts.GROUP_ALIAS_ATTR_PREF));
        }
        if (isContainIntents(alias)) {
            list.add(aliasMap.get(UserDimConsts.GROUP_ALIAS_INTENTS));
        }
        return FieldAnnUtils.generateQueries(list);
    }

    public static void copyProperties(Map<String, Map<String, String>> srcMap, UserDimFacade dist) {
        if (MapUtils.isEmpty(srcMap)) {
            return;
        }
        for (Entry<String, Map<String, String>> entry : srcMap.entrySet()) {
            String family = entry.getKey();
            Map<String, String> colvalue = entry.getValue();
            if (MapUtils.isEmpty(colvalue)) {
                continue;
            }
            if (UserDimConsts.GROUP_NAME_ACTION.equals(family)) {
                UserActionStats action = new UserActionStats();
                action.setBrowse3d(NumberUtils.toInt(colvalue.get("browse3d"), 0));
                action.setBrowse7d(NumberUtils.toInt(colvalue.get("browse7d"), 0));
                action.setBrowse90d(NumberUtils.toInt(colvalue.get("browse90d"), 0));
                action.setFavorite3d(NumberUtils.toInt(colvalue.get("favorite3d"), 0));
                action.setFavorite7d(NumberUtils.toInt(colvalue.get("favorite7d"), 0));
                action.setFavorite90d(NumberUtils.toInt(colvalue.get("favorite90d"), 0));
                action.setAddcart3d(NumberUtils.toInt(colvalue.get("addcart3d"), 0));
                action.setAddcart7d(NumberUtils.toInt(colvalue.get("addcart7d"), 0));
                action.setAddcart90d(NumberUtils.toInt(colvalue.get("addcart90d"), 0));
                action.setBought3d(NumberUtils.toInt(colvalue.get("bought3d"), 0));
                action.setBought7d(NumberUtils.toInt(colvalue.get("bought7d"), 0));
                action.setBought90d(NumberUtils.toInt(colvalue.get("bought90d"), 0));
                dist.setActionStats(action);
            } else if (UserDimConsts.GROUP_NAME_LABEL.equals(family)) {
                UserProfileLabel label = new UserProfileLabel();
                // TODO
                dist.setProfileLabel(label);
            } else if (UserDimConsts.GROUP_NAME_CATE_PREF.equals(family)) {
                UserCatePref cate = new UserCatePref();
                // TODO
                dist.setCatePref(cate);
            } else if (UserDimConsts.GROUP_NAME_ATTR_PREF.equals(family)) {
                UserAttrPref attr = new UserAttrPref();
                // TODO
                dist.setAttrPref(attr);
            } else if (UserDimConsts.GROUP_NAME_INTENTS.equals(family)) {
                UserIntents intents = new UserIntents();
                // TODO
                dist.setIntents(intents);
            }
        }
    }

    private static boolean isContainAction(long alias) {
        return (alias & UserDimConsts.GROUP_ALIAS_ACTION) != 0;
    }

    private static boolean isContainLabel(long alias) {
        return (alias & UserDimConsts.GROUP_ALIAS_LABEL) != 0;
    }

    private static boolean isContainCatePref(long alias) {
        return (alias & UserDimConsts.GROUP_ALIAS_CATE_PREF) != 0;
    }

    private static boolean isContainAttrPref(long alias) {
        return (alias & UserDimConsts.GROUP_ALIAS_ATTR_PREF) != 0;
    }

    private static boolean isContainIntents(long alias) {
        return (alias & UserDimConsts.GROUP_ALIAS_INTENTS) != 0;
    }

}
