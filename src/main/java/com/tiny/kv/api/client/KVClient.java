package com.tiny.kv.api.client;

import com.tiny.kv.api.service.KVLowLevelService;
import com.tiny.kv.api.service.ProdDimService;
import com.tiny.kv.api.service.UserDimService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class KVClient {

	/**
	 * singleton instance
	 */
	private static KVClient INSTANCE;

	/**
	 * 客户端Spring的上下文
	 */
	private ApplicationContext applicationContext;

	/**
	 * 低层级的service
	 */
	private KVLowLevelService lowLevelService;

	/**
	 * 高层级的产品维度service
	 */
	private ProdDimService prodDimService;
	/**
	 * 高层级的用户维度service
	 */
	private UserDimService userDimService;

	/**
	 * 私有化constructor
	 */
	private KVClient() {
		init();
	}

	public static KVClient getInstance() {
		if (INSTANCE != null) {
			return INSTANCE;
		}
		synchronized (KVClient.class) {
			if (INSTANCE == null) {
				INSTANCE = new KVClient();
			}
			return INSTANCE;
		}
	}

	private void init() {
		try {
			applicationContext = new ClassPathXmlApplicationContext(
					"classpath:config/kv-service-deploy.xml");
		} catch (Exception e) {
			throw new RuntimeException("KVClient initialize exception.", e);
		}
	}

	public ProdDimService getProdDimService() {
		if(prodDimService == null){
			prodDimService = (ProdDimService) applicationContext.getBean(KVServiceName.KV_PRODUCT_DIM_SERVICE);
		}
		return prodDimService;
	}
	
	public UserDimService getUserDimService() {
		if(userDimService == null){
			userDimService = (UserDimService) applicationContext.getBean(KVServiceName.KV_USER_DIM_SERVICE);
		}
		return userDimService;
	}
	
	public KVLowLevelService getLowLevelService() {
		if(lowLevelService == null){
			lowLevelService = (KVLowLevelService) applicationContext.getBean(KVServiceName.KV_LOW_LEVEL_SERVICE);
		}
		return lowLevelService;
	}
	
	
}
