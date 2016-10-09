package com.tiny.kv.api.model;

import com.tiny.kv.annotation.Group;
import com.tiny.kv.annotation.StorageMedium;
import com.tiny.kv.annotation.Table;
import com.tiny.kv.api.utils.UserDimConsts;

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
public class UserDimFacade {

	private String userId;

	@Table(name = "user_action_statistic", type = StorageMedium.HBASE)
	@Group(name = UserDimConsts.GROUP_NAME_ACTION, alias = UserDimConsts.GROUP_ALIAS_ACTION)
	private UserActionStats actionStats;

	@Table(name = "user_profile_base", type = StorageMedium.HBASE)
	@Group(name = UserDimConsts.GROUP_NAME_LABEL, alias = UserDimConsts.GROUP_ALIAS_LABEL)
	private UserProfileLabel profileLabel;

	@Table(name = "user_category_preference", type = StorageMedium.HBASE)
	@Group(name = UserDimConsts.GROUP_NAME_CATE_PREF, alias = UserDimConsts.GROUP_ALIAS_CATE_PREF)
	private UserCatePref catePref;

	@Table(name="user_attribute_preference", type = StorageMedium.HBASE)
	@Group(name = UserDimConsts.GROUP_NAME_ATTR_PREF, alias = UserDimConsts.GROUP_ALIAS_ATTR_PREF)
	private UserAttrPref attrPref;

	@Table(name="intents_seeds", type = StorageMedium.HBASE)
	@Group(name = UserDimConsts.GROUP_NAME_INTENTS, alias = UserDimConsts.GROUP_ALIAS_INTENTS)
	private UserIntents intents;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserActionStats getActionStats() {
		return actionStats;
	}

	public void setActionStats(UserActionStats actionStats) {
		this.actionStats = actionStats;
	}

	public UserProfileLabel getProfileLabel() {
		return profileLabel;
	}

	public void setProfileLabel(UserProfileLabel profileLabel) {
		this.profileLabel = profileLabel;
	}

	public UserCatePref getCatePref() {
		return catePref;
	}

	public void setCatePref(UserCatePref catePref) {
		this.catePref = catePref;
	}

	public UserAttrPref getAttrPref() {
		return attrPref;
	}

	public void setAttrPref(UserAttrPref attrPref) {
		this.attrPref = attrPref;
	}

	public UserIntents getIntents() {
		return intents;
	}

	public void setIntents(UserIntents intents) {
		this.intents = intents;
	}

}
