package com.tiny.kv.api.utils;

public class UserDimConsts {
	
	// group alias name
	public static final long GROUP_ALIAS_ACTION = 1 << 0;

	public static final long GROUP_ALIAS_LABEL = 1 << 1;
	
	public static final long GROUP_ALIAS_CATE_PREF = 1 << 2;
	
	public static final long GROUP_ALIAS_ATTR_PREF = 1 << 3;
	
	public static final long GROUP_ALIAS_INTENTS = 1 << 4;
	
	// group real name
	public static final String GROUP_NAME_ACTION = "ua";

	public static final String GROUP_NAME_LABEL = "bi";
	
	public static final String GROUP_NAME_CATE_PREF = "catepref";
	
	public static final String GROUP_NAME_ATTR_PREF = "attripref:{categoryid}";
	
	public static final String GROUP_NAME_INTENTS = "int";
		
}
