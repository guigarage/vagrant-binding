package com.guigarage.vagrant.configuration;

import com.guigarage.vagrant.util.RandomString;

import java.net.URL;
import java.util.Map;

/**
 * Some utilities for the configuration of Vagrant environments. This class creates configurationfiles for Vagrant.
 * @author hendrikebbers
 *
 */
public class VagrantConfigurationUtilities {

    private VagrantConfigurationUtilities() {
	}

	public static String createVagrantFileContent(
			VagrantEnvironmentConfig config) {
		StringBuilder builder = new StringBuilder();
        builder.append("VAGRANTFILE_API_VERSION = \"2\"\n");
		builder.append("Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|").append("\n");
		
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
			vmName = RandomString.randomString(10);
		}
		builder.append(
				"config.vm.define :" + vmName + " do |" + vmName
						+ "_config|").append("\n");

		for (VagrantPortForwarding portForwarding : vmConfig
				.getPortForwardings()) {
			builder.append(createPortForwardingConfig(vmName + "_config", portForwarding));
		}

        if (!vmConfig.getCustomizeVm().isEmpty()) {
            builder.append("config.vm.provider :virtualbox do |vb|\n");
            for (String[] customization : vmConfig.getCustomizeVm()) {
                    builder.append(createCustomizedVmConfig(customization));
                }
            builder.append("end\n");
        }

        for (VagrantSyncFolder syncFolder : vmConfig
                .getSyncFolders()) {
            builder.append(createSyncFolderConfig(vmName + "_config", syncFolder));
        }

		builder.append(createBoxNameConfig(vmName + "_config", vmConfig.getBoxName()));

        if (vmConfig.getGuest() != null) {
            builder.append(createVmGuestConfig(vmName + "_config", vmConfig.getGuest()));
        }

		URL boxUrl = vmConfig.getBoxUrl();
		if (boxUrl != null) {
			builder.append(createBoxUrlConfig(vmName + "_config", boxUrl));
		}

		String ip = vmConfig.getIp();
		if (ip != null) {
            if (vmConfig.isPrivateNetwork()) {
                builder.append(createPrivateNetworkIpConfig(vmName + "_config", ip));
            } else {
                builder.append(createHostOnlyIpConfig(vmName + "_config", ip));
            }
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

        String bashProvisionScript = vmConfig.getBashProvisionScript();
        if (bashProvisionScript != null) {
            builder.append(createBashProvisionScriptConfig(vmName + "_config", bashProvisionScript));
        }

		builder.append("end").append("\n");
		return builder.toString();
	}

    private static String createCustomizedVmConfig(String[] entry) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t").append("vb.customize [");

        for (int i = 0; i < entry.length; i++) {
            if (entry[i].equals(":id")) {
                builder.append(entry[i]);
            } else {
                builder.append("\"").append(entry[i]).append("\"");
            }
            if (i < entry.length - 1) {
                builder.append(",");
            }
        }

        builder.append("]\n");
        return builder.toString();
    }

    private static String createVmGuestConfig(String vmConfigName, String guest) {
        StringBuilder builder = new StringBuilder();
        builder.append(
                vmConfigName + ".vm.guest = \"" + guest
                        + "\"").append("\n");
        return builder.toString();
    }

    private static String createSyncFolderConfig(String vmConfigName, VagrantSyncFolder syncFolder) {
        StringBuilder builder = new StringBuilder();
        builder.append(vmConfigName + ".vm.synced_folder ")
                .append("'" + syncFolder.getSource()).append("', ")
                .append("'" + syncFolder.getDestination()).append("', ")
                .append("disabled: " + syncFolder.isDisabled())
                .append("\n");
        return builder.toString();

    }

    private static String createPortForwardingConfig(String vmConfigName, VagrantPortForwarding portForwarding) {
		StringBuilder builder = new StringBuilder();
        builder.append(
                vmConfigName + ".vm.network :forwarded_port, guest: " + portForwarding.getGuestport()
                        + ", host: " + portForwarding.getHostport())
                .append("\n");
        return builder.toString();
	}

	private static String createBoxNameConfig(String vmConfigName, String boxName) {
		StringBuilder builder = new StringBuilder();
		builder.append(
				vmConfigName + ".vm.box = \"" + boxName
						+ "\"").append("\n");
		return builder.toString();
	}

    private static String createBashProvisionScriptConfig(String vmConfigName, String bashProvisionScript) {
        StringBuilder builder = new StringBuilder();
        builder.append(vmConfigName + ".vm.provision :shell, :path => \"" + bashProvisionScript + "\"").append("\n");
        return builder.toString();
    }


    private static String createHostNameConfig(String vmConfigName, String hostName) {
		StringBuilder builder = new StringBuilder();
		if (hostName != null) {
			builder.append(
					vmConfigName + ".vm.hostname = \"" + hostName + "\"")
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

    private static String createPrivateNetworkIpConfig(String vmConfigName, String ip) {
        StringBuilder builder = new StringBuilder();
        if (ip != null) {
            builder.append(
                    vmConfigName + ".vm.network :private_network, ip: \"" + ip + "\"")
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
