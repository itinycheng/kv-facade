package com.tiny.kv.api.client;

import com.tiny.kv.api.model.ProdDimFacade;
import com.tiny.kv.api.model.UserDimFacade;
import com.tiny.kv.api.service.KVLowLevelService;
import com.tiny.kv.api.service.ProdDimService;
import com.tiny.kv.api.service.UserDimService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

public class KVClientTest {

    private ProdDimService prodDimService;

    private UserDimService userDimService;

    private KVLowLevelService lowLevelService;

    @Before
    public void init() {
        KVClient instance = KVClient.getInstance();
        prodDimService = instance.getProdDimService();
        userDimService = instance.getUserDimService();
        lowLevelService = instance.getLowLevelService();
    }

    @Test
    public void testQueryProdData() throws Exception {
        ProdDimFacade data = prodDimService.queryProdData(11L, 1L);
        System.out.println(data);
    }

    @Test
    public void testQueryUserData() throws Exception {
        UserDimFacade data = userDimService.queryUserData("uid", 1L);
        System.out.println(data);
    }

    @Test
    public void testQueryData() throws Exception {
        Map<String, Map<String, String>> data = lowLevelService.queryData("tableName", "key", new ArrayList<String>());
        System.out.println(data);
    }
}
