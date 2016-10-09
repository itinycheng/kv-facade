package com.tiny.kv.api.model;

import java.util.List;

/**
 * intents_seeds
 * 
 * @author tiny
 *
 */
public class UserIntents {

	private String ud;

	private String ut;

	private List<CateIntents> cateIntents;

	public static class CateIntents {

		private Long categoryId;

		private List<ProdIntents> observedSeedLists;

		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}

		public List<ProdIntents> getObservedSeedLists() {
			return observedSeedLists;
		}

		public void setObservedSeedLists(List<ProdIntents> observedSeedLists) {
			this.observedSeedLists = observedSeedLists;
		}

	}

	public static class ProdIntents {

		private Long prodId;

		private String reason;

		private Double score;

		private String updateTime;

		public Long getProdId() {
			return prodId;
		}

		public void setProdId(Long prodId) {
			this.prodId = prodId;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public Double getScore() {
			return score;
		}

		public void setScore(Double score) {
			this.score = score;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

	}

	public String getUd() {
		return ud;
	}

	public void setUd(String ud) {
		this.ud = ud;
	}

	public String getUt() {
		return ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

	public List<CateIntents> getCateIntents() {
		return cateIntents;
	}

	public void setCateIntents(List<CateIntents> cateIntents) {
		this.cateIntents = cateIntents;
	}

}
