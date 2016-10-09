package com.tiny.kv.utils;

import com.alibaba.fastjson.JSON;
import com.tiny.kv.api.model.ProdDimFacade;
import com.tiny.kv.model.GroupContext;
import org.junit.Test;

import java.util.Map;

/**
 * {@link FieldAnnUtils}
 *
 */
public class FieldAnnUtilsTest {
	
	@Test
	public void testGetGroupFromClazz(){
		Map<Long, GroupContext> map = FieldAnnUtils.getGroupFromClazz(ProdDimFacade.class);
		System.out.println(JSON.toJSONString(map));
	}
	
}
