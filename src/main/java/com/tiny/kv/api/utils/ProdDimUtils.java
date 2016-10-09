package com.tiny.kv.api.utils;

import com.tiny.kv.api.model.ProdActionStats;
import com.tiny.kv.api.model.ProdAttribute;
import com.tiny.kv.api.model.ProdDimFacade;
import com.tiny.kv.model.GroupContext;
import com.tiny.kv.model.QueryContext;
import com.tiny.kv.utils.FieldAnnUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 用户纬度视图工具类
 * @author tiny
 *
 */
public class ProdDimUtils {

	/**
	 *
	 * @param alias
	 * @param clazz
	 * @return Map<databaseType,Map<group, column>>
     */
	@SuppressWarnings("unchecked")
	public static List<QueryContext> parseAliasToQueries(long alias, Class<?> clazz){
		if(alias == 0 || clazz == null){
			return ListUtils.EMPTY_LIST;
		}
		Map<Long, GroupContext> aliasMap = FieldAnnUtils.getGroupFromClazz(clazz);
		List<GroupContext> list = new ArrayList<>();
		if(isContainAction(alias)){
			list.add(aliasMap.get(ProdDimConsts.GROUP_ALIAS_ACTION));
		}
		if(isContainAttr(alias)){
			list.add(aliasMap.get(ProdDimConsts.GROUP_ALIAS_ATTR));
		}
		if(isContainRelated(alias)){
			list.add(aliasMap.get(ProdDimConsts.GROUP_ALIAS_RELATED));
		}
		if(isContainSimilar(alias)){
			list.add(aliasMap.get(ProdDimConsts.GROUP_ALIAS_SIMILAR));
		}
		return FieldAnnUtils.generateQueries(list);
	}
	
	/**
	 * 
	 * @param srcMap Map<family, Map<column, value>>
	 * @param dist bean
	 */
	public static void copyProperties(Map<String, Map<String, String>> srcMap, ProdDimFacade dist) {
		if(MapUtils.isEmpty(srcMap)){
			return;
		}
		for (Entry<String, Map<String, String>> entry : srcMap.entrySet()) {
			String family = entry.getKey();
			Map<String, String> colvalue = entry.getValue();
			if(StringUtils.isEmpty(family) || MapUtils.isEmpty(colvalue)){
				continue;
			}
			if(ProdDimConsts.GROUP_NAME_ACTION.equals(family)){
				ProdActionStats action = new ProdActionStats();
				action.setExps3d(NumberUtils.toInt(colvalue.get("exps3d"),0));
				action.setExps7d(NumberUtils.toInt(colvalue.get("exps7d"),0));
				action.setExps90d(NumberUtils.toInt(colvalue.get("exps90d"),0));
				action.setBrowse3d(NumberUtils.toInt(colvalue.get("browse3d"),0));
				action.setBrowse7d(NumberUtils.toInt(colvalue.get("browse7d"),0));
				action.setBrowse90d(NumberUtils.toInt(colvalue.get("browse90d"),0));
				action.setFavorite3d(NumberUtils.toInt(colvalue.get("favorite3d"),0));
				action.setFavorite7d(NumberUtils.toInt(colvalue.get("favorite7d"),0));
				action.setFavorite90d(NumberUtils.toInt(colvalue.get("favorite90d"),0));
				action.setAddcart3d(NumberUtils.toInt(colvalue.get("addcart3d"),0));
				action.setAddcart7d(NumberUtils.toInt(colvalue.get("addcart7d"),0));
				action.setAddcart90d(NumberUtils.toInt(colvalue.get("addcart90d"),0));
				action.setBought3d(NumberUtils.toInt(colvalue.get("bought3d"),0));
				action.setBought7d(NumberUtils.toInt(colvalue.get("bought7d"),0));
				action.setBought90d(NumberUtils.toInt(colvalue.get("bought90d"),0));
				dist.setActionStats(action);
			}else if(ProdDimConsts.GROUP_NAME_ATTR.equals(family)){
				ProdAttribute attr = new ProdAttribute();
				attr.setBrandId(NumberUtils.toLong(colvalue.get("brandId"),0));
				attr.setCategoryId(NumberUtils.toLong(colvalue.get("categoryId"),0));
				attr.setCompetition(NumberUtils.toInt(colvalue.get("competition"),0));
				attr.setCurrentPrice(NumberUtils.toDouble(colvalue.get("currentPrice"),0));
				attr.setCurrentStockNum(NumberUtils.toInt(colvalue.get("currentStockNum"),0));
				attr.setMaxPrice(NumberUtils.toDouble(colvalue.get("maxPrice"),0));
				attr.setMinPrice(NumberUtils.toDouble(colvalue.get("minPrice"),0));
				attr.setPop(NumberUtils.toDouble(colvalue.get("pop"),0));
				attr.setPriceRank(NumberUtils.toDouble(colvalue.get("priceRank"),0));
				attr.setRelatedId(NumberUtils.toLong(colvalue.get("relatedId"),0));
				dist.setAttr(attr);
			}else if(ProdDimConsts.GROUP_NAME_RELATED.startsWith(family)){
				// 相关
				List<Long> related = new ArrayList<>();
				String relatedStr = colvalue.get("related");
				String[] relatedArr = StringUtils.isEmpty(relatedStr) ? ArrayUtils.EMPTY_STRING_ARRAY : relatedStr.split(",");
				for (String str : relatedArr) {
					if(NumberUtils.isDigits(str)){
						related.add(NumberUtils.toLong(str,0));
					}
				}
				dist.setRelatedProdIds(related);
				// 相似
				List<Long> similar = new ArrayList<>();
				String similarStr = colvalue.get("similar");
				String[] similarArr = StringUtils.isEmpty(similarStr) ? ArrayUtils.EMPTY_STRING_ARRAY : similarStr.split(",");
				for (String str : similarArr) {
					if(NumberUtils.isDigits(str)){
						similar.add(NumberUtils.toLong(str,0));
					}
				}
				dist.setSimilarProdIds(similar);
			}
		}
	}

	private static boolean isContainAction(long alias) {
		return (alias & ProdDimConsts.GROUP_ALIAS_ACTION) != 0;
	}

	private static boolean isContainAttr(long alias) {
		return (alias & ProdDimConsts.GROUP_ALIAS_ATTR) != 0;
	}

	private static boolean isContainRelated(long alias) {
		return (alias & ProdDimConsts.GROUP_ALIAS_RELATED) != 0;
	}

	private static boolean isContainSimilar(long alias) {
		return (alias & ProdDimConsts.GROUP_ALIAS_SIMILAR) != 0;
	}
}
