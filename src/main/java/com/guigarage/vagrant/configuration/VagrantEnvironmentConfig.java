package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the configuration of a Vagrant environmant.
 * @author hendrikebbers
 *
 */
public class VagrantEnvironmentConfig {

	private List<VagrantVmConfig> vmConfigs;
	
	public VagrantEnvironmentConfig(Iterable<VagrantVmConfig> vmConfigs) {
		this.vmConfigs = new ArrayList<VagrantVmConfig>();
		if(vmConfigs != null) {
			for(VagrantVmConfig vagrantVmConfig : vmConfigs) {
				this.vmConfigs.add(vagrantVmConfig);
			}
		}
	}
	
	/**
	 * Returns all {@link VagrantVmConfig}
	 * @return all {@link VagrantVmConfig}
	 */
	public Iterable<VagrantVmConfig> getVmConfigs() {
		return vmConfigs;
	}
	
	/**
	 * Returns true if this configuration describes a multi VM environment. A multi VM environment manages more than one VM.
	 * @return true if this configuration describes a multi VM environment
	 */
	public boolean isMultiVmEnvironment() {
		if(vmConfigs.size() > 1) {
			return true;
		}
		return false;
	}
}
