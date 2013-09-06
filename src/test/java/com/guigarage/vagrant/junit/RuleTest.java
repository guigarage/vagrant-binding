package com.guigarage.vagrant.junit;

import com.guigarage.vagrant.Vagrant;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.guigarage.vagrant.util.VagrantUtils;

public class RuleTest {

	@Rule
	public VagrantTestRule testRule = new VagrantTestRule(VagrantUtils.getInstance().getLucid32MasterContent());
	
	@Test
	public void testDummy() {
		try {
		Assert.assertEquals(true, testRule.getEnvironment().getAllVms().iterator().next().isRunning());
		} catch (Exception exception) {
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}
}
