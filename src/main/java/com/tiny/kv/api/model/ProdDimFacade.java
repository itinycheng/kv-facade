package com.tiny.kv.api.model;

import com.tiny.kv.annotation.Group;
import com.tiny.kv.annotation.StorageMedium;
import com.tiny.kv.annotation.Table;
import com.tiny.kv.api.utils.ProdDimConsts;

import java.util.List;

/**
 * <pre>
 * 用户维度所有的数据封装(底层kv表有可能多张)
 * 一个group封装成一个bean对象
 * 客户端通过传入一到多个groupAlias查询对应的数据
 * 		如 : method(basicAlias|actionAlias);
 * 		通过&提取要查询的列
 * &#64;author tiny
 * </pre>
 */
public class ProdDimFacade {

	private Long productId;

	@Table(name = "product_collection", type = StorageMedium.HBASE)
	@Group(name = ProdDimConsts.GROUP_NAME_ACTION, alias = ProdDimConsts.GROUP_ALIAS_ACTION)
	private ProdActionStats actionStats;

	@Table(name = "product_collection", type = StorageMedium.HBASE)
	@Group(name = ProdDimConsts.GROUP_NAME_ATTR, alias = ProdDimConsts.GROUP_ALIAS_ATTR)
	private ProdAttribute attr;

	@Table(name = "product_collection", type = StorageMedium.HBASE)
	@Group(name = ProdDimConsts.GROUP_NAME_RELATED, alias = ProdDimConsts.GROUP_ALIAS_RELATED)
	private List<Long> relatedProdIds;

	@Table(name = "product_collection", type = StorageMedium.HBASE)
	@Group(name = ProdDimConsts.GROUP_NAME_SIMILAR, alias = ProdDimConsts.GROUP_ALIAS_SIMILAR)
	private List<Long> similarProdIds;

	public ProdActionStats getActionStats() {
		return actionStats;
	}

	public void setActionStats(ProdActionStats actionStats) {
		this.actionStats = actionStats;
	}

	public List<Long> getRelatedProdIds() {
		return relatedProdIds;
	}

	public void setRelatedProdIds(List<Long> relatedProdIds) {
		this.relatedProdIds = relatedProdIds;
	}

	public List<Long> getSimilarProdIds() {
		return similarProdIds;
	}

	public void setSimilarProdIds(List<Long> similarProdIds) {
		this.similarProdIds = similarProdIds;
	}

	public ProdAttribute getAttr() {
		return attr;
	}

	public void setAttr(ProdAttribute attr) {
		this.attr = attr;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
