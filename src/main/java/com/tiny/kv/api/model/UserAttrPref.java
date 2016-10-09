package com.tiny.kv.api.model;

import java.util.Map;

/**
 * user_attribute_preference
 * 
 * @author tiny
 *
 */
public class UserAttrPref {

	private Long categoryId;

	/**
	 * <pre>
	 * item =>
	 * "20219" : {
	 * 	"attributePref" : 0.15580340333216458,
	 * 	"attributeValNum" : 4,
	 * 	"attributeValues" : {
	 * 		"133138" : {
	 * 			"attributeValuePref" : 0.07302022393237963
	 * 		},
	 * 		"133162" : {
	 * 			"attributeValuePref" : 0.020670541900625873
	 * 		}
	 * 	},
	 *  "weightSum" : 12.336731897129008
	 * }
	 * </pre>
	 *
	 */
	private Map<String, AttrPrefItem> attriItems;

	public static class AttrPrefItem {

		private Double attributePref;

		private Integer attributeValNum;

		private Map<String, AttrValItem> attributeValues;

		private Double weightSum;

		public Map<String, AttrValItem> getAttributeValues() {
			return attributeValues;
		}

		public void setAttributeValues(Map<String, AttrValItem> attributeValues) {
			this.attributeValues = attributeValues;
		}

		public Double getAttributePref() {
			return attributePref;
		}

		public void setAttributePref(Double attributePref) {
			this.attributePref = attributePref;
		}

		public Integer getAttributeValNum() {
			return attributeValNum;
		}

		public void setAttributeValNum(Integer attributeValNum) {
			this.attributeValNum = attributeValNum;
		}

		public Double getWeightSum() {
			return weightSum;
		}

		public void setWeightSum(Double weightSum) {
			this.weightSum = weightSum;
		}

	}

	public static class AttrValItem {

		private Double attributeValuePref;

		public Double getAttributeValuePref() {
			return attributeValuePref;
		}

		public void setAttributeValuePref(Double attributeValuePref) {
			this.attributeValuePref = attributeValuePref;
		}

	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Map<String, AttrPrefItem> getAttriItems() {
		return attriItems;
	}

	public void setAttriItems(Map<String, AttrPrefItem> attriItems) {
		this.attriItems = attriItems;
	}

}
