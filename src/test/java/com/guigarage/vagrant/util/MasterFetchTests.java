package com.guigarage.vagrant.util;

import org.junit.Test;

import junit.framework.Assert;

public class MasterFetchTests {

	@Test
	public void fetchMasterConfigs() {
		try {
			String content = VagrantUtils.getInstance().getLucid32MasterContent();
			Assert.assertEquals(true, content != null);
			Assert.assertEquals(true, content.length() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		try {
			String content = VagrantUtils.getInstance().getLucid64MasterContent();
			Assert.assertEquals(true, content != null);
			Assert.assertEquals(true, content.length() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
