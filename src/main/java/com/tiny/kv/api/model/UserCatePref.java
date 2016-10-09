package com.tiny.kv.api.model;

import java.util.Map;

/**
 * user_category_preference
 * @author tiny
 *
 */
public class UserCatePref {

	private Map<Long, Double> level1;

	private Map<Long, Double> level2;

	private Map<Long, Double> level3;

	private Map<Long, Double> level4;

	public Map<Long, Double> getLevel1() {
		return level1;
	}

	public void setLevel1(Map<Long, Double> level1) {
		this.level1 = level1;
	}

	public Map<Long, Double> getLevel2() {
		return level2;
	}

	public void setLevel2(Map<Long, Double> level2) {
		this.level2 = level2;
	}

	public Map<Long, Double> getLevel3() {
		return level3;
	}

	public void setLevel3(Map<Long, Double> level3) {
		this.level3 = level3;
	}

	public Map<Long, Double> getLevel4() {
		return level4;
	}

	public void setLevel4(Map<Long, Double> level4) {
		this.level4 = level4;
	}

}
