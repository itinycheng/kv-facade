package com.tiny.kv.api.model;

/**
 * product_collection, attr
 * 
 * @author tiny
 *
 */
public class ProdAttribute {

	private Long categoryId;

	private Long brandId;

	private Double currentPrice;

	private Integer currentStockNum;

	private Double priceRank;

	private Double maxPrice;

	private Double minPrice;

	private int competition;

	private Double pop;

	private Long relatedId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getCurrentStockNum() {
		return currentStockNum;
	}

	public void setCurrentStockNum(Integer currentStockNum) {
		this.currentStockNum = currentStockNum;
	}

	public Double getPriceRank() {
		return priceRank;
	}

	public void setPriceRank(Double priceRank) {
		this.priceRank = priceRank;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public int getCompetition() {
		return competition;
	}

	public void setCompetition(int competition) {
		this.competition = competition;
	}

	public Double getPop() {
		return pop;
	}

	public void setPop(Double pop) {
		this.pop = pop;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

}
