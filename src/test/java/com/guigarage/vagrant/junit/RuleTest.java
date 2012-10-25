package com.guigarage.vagrant.junit;

import org.junit.Rule;
import org.junit.Test;

import com.guigarage.vagrant.VagrantUtils;

public class RuleTest {

	@Rule
	public VagrantTestRule testRule = new VagrantTestRule(VagrantUtils.getLucid32Master());
	
	@Test
	public void testDummy() {
		System.out.println("Dummy-Test");
	}
}
