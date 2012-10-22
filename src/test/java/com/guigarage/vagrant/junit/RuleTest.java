package com.guigarage.vagrant.junit;

import org.junit.Rule;
import org.junit.Test;

public class RuleTest {

	@Rule
	public VagrantTestRule testRule = new VagrantTestRule("");
	
	@Test
	public void testDummy() {
		System.out.println("Dummy-Test");
	}
}
