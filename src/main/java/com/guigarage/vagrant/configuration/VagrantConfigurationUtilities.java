package com.guigarage.vagrant.configuration;

import java.net.URL;
import java.util.UUID;

public class VagrantConfigurationUtilities {

	private VagrantConfigurationUtilities() {
	}

	public static String createVagrantFileContent(
			VagrantEnvironmentConfig config) {
		StringBuilder builder = new StringBuilder();
		builder.append("Vagrant::Config.run do |config|").append("\n");
		
		//TODO: Wenn nicht MultiVM kann man hier auch ein einfaches Config-File erstellen und so primaryVm etc. nutzen...
		
		for (VagrantVmConfig vmConfig : config.getVmConfigs()) {
			builder.append(createVmInMultiEnvConfig(vmConfig));
		}
		builder.append("end").append("\n");
		return builder.toString();
	}

	private static String createVmInMultiEnvConfig(VagrantVmConfig vmConfig) {
		StringBuilder builder = new StringBuilder();
		String vmName = vmConfig.getName();
		if (vmName == null) {
			vmName = UUID.randomUUID().toString();
		}
		builder.append(
				"config.vm.define :" + vmName + " do |" + vmName
						+ "_config|").append("\n");

		for (VagrantPortForwarding portForwarding : vmConfig
				.getPortForwardings()) {
			builder.append(createPortForwardingConfig(vmName + "_config", portForwarding));
		}
		builder.append(createBoxNameConfig(vmName + "_config", vmConfig.getBoxName()));

		URL boxUrl = vmConfig.getBoxUrl();
		if (boxUrl != null) {
			builder.append(createBoxUrlConfig(vmName + "_config", boxUrl));
		}

		String ip = vmConfig.getIp();
		if (ip != null) {
			builder.append(createHostOnlyIpConfig(vmName + "_config", ip));
		}

		boolean guiMode = vmConfig.isGuiMode();
		if(guiMode) {
			builder.append(createGuiModeConfig(vmName + "_config"));
		}
		
		String hostName = vmConfig.getHostName();
		if(hostName != null) {
			builder.append(createHostNameConfig(vmName + "_config", hostName));
		}
		
		PuppetProvisionerConfig puppetProvisionerConfig = vmConfig
				.getPuppetProvisionerConfig();
		if (puppetProvisionerConfig != null) {
			builder.append(createPuppetProvisionerConfig(
					vmName + "_config", puppetProvisionerConfig));
		}
		builder.append("end").append("\n");
		return builder.toString();
	}
	
	private static String createPortForwardingConfig(String vmConfigName, VagrantPortForwarding portForwarding) {
		StringBuilder builder = new StringBuilder();
		String portForwardingName = portForwarding.getName();
		if (portForwardingName != null) {
			builder.append(
					vmConfigName + ".vm.forward_port \""
							+ portForwardingName + "\", "
							+ portForwarding.getGuestport() + ", "
							+ portForwarding.getHostport())
					.append("\n");
		} else {
			builder.append(
					vmConfigName + ".vm.forward_port "
							+ portForwarding.getGuestport() + ", "
							+ portForwarding.getHostport())
					.append("\n");
		}
		return builder.toString();
	}
	
	private static String createBoxNameConfig(String vmConfigName, String boxName) {
		StringBuilder builder = new StringBuilder();
		builder.append(
				vmConfigName + ".vm.box = \"" + boxName
						+ "\"").append("\n");
		return builder.toString();
	}
	
	private static String createHostNameConfig(String vmConfigName, String hostName) {
		StringBuilder builder = new StringBuilder();
		if (hostName != null) {
			builder.append(
					vmConfigName + ".vm.host_name = \"" + hostName + ".local\"")
					.append("\n");
		}
		return builder.toString();
	}
	
	
	private static String createBoxUrlConfig(String vmConfigName, URL boxUrl) {
		StringBuilder builder = new StringBuilder();
		if (boxUrl != null) {
			builder.append(
					vmConfigName + ".vm.box_url = \"" + boxUrl + "\"")
					.append("\n");
		}
		return builder.toString();
	}
	
	private static String createHostOnlyIpConfig(String vmConfigName, String ip) {
		StringBuilder builder = new StringBuilder();
		if (ip != null) {
			builder.append(
					vmConfigName + ".vm.network :hostonly, \"" + ip + "\"")
					.append("\n");
		}
		return builder.toString();
	}
	
	private static String createGuiModeConfig(String vmConfigName) {
		StringBuilder builder = new StringBuilder();
			builder.append(
					vmConfigName + ".vm.boot_mode = :gui")
					.append("\n");
		return builder.toString();
	}

	private static String createPuppetProvisionerConfig(String vmConfigName,
			PuppetProvisionerConfig puppetProvisionerConfig) {
		StringBuilder builder = new StringBuilder();
		if (puppetProvisionerConfig != null) {
			builder.append(vmConfigName + ".vm.provision :puppet do |puppet|")
					.append("\n");
			builder.append(
					"puppet.manifests_path = \""
							+ puppetProvisionerConfig.getManifestsPath() + "\"")
					.append("\n");
			builder.append(
					"puppet.manifest_file  = \""
							+ puppetProvisionerConfig.getManifestFile() + "\"")
					.append("\n");
			
			String modulesPath = puppetProvisionerConfig.getModulesPath();
			if(modulesPath != null) {
				builder.append(
						"puppet.module_path  = \""
								+ modulesPath + "\"")
						.append("\n");
			}
			
			boolean debug = puppetProvisionerConfig.isDebug();
			if(debug) {
				builder.append(
						"puppet.options  = \"--verbose --debug\"")
						.append("\n");
			}
			
			builder.append("end").append("\n");
		}
		return builder.toString();
	}
}
