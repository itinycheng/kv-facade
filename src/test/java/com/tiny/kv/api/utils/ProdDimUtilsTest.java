package com.tiny.kv.api.utils;

import com.alibaba.fastjson.JSON;
import com.tiny.kv.api.model.ProdDimFacade;
import com.tiny.kv.model.QueryContext;
import org.junit.Test;

import java.util.List;

/**
 * {@link ProdDimUtils}
 */
public class ProdDimUtilsTest {

    @Test
    public void parseAliasToQueries() {
        long alias = ProdDimConsts.GROUP_ALIAS_ACTION
                | ProdDimConsts.GROUP_ALIAS_ATTR
                | ProdDimConsts.GROUP_ALIAS_RELATED
                | ProdDimConsts.GROUP_ALIAS_SIMILAR;
        List<QueryContext> list = ProdDimUtils.parseAliasToQueries(alias, ProdDimFacade.class);
        System.out.println(JSON.toJSONString(list));
    }

}
