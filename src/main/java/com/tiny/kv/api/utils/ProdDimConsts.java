package com.tiny.kv.api.utils;

public class ProdDimConsts {
	
	// group alias name
	public static final long GROUP_ALIAS_ACTION = 1 << 0;

	public static final long GROUP_ALIAS_ATTR = 1 << 1;
	
	public static final long GROUP_ALIAS_RELATED = 1 << 2;
	
	public static final long GROUP_ALIAS_SIMILAR = 1 << 3;
	
	// group real name
	public static final String GROUP_NAME_ACTION = "pa";

	public static final String GROUP_NAME_ATTR = "attr";
	
	public static final String GROUP_NAME_RELATED = "resi:related";
	
	public static final String GROUP_NAME_SIMILAR = "resi:similar";

}
