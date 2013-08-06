package com.guigarage.vagrant.configuration.builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;
import com.guigarage.vagrant.configuration.VagrantPortForwarding;
import com.guigarage.vagrant.configuration.VagrantSyncFolder;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

public class VagrantVmConfigBuilder {

    private List<VagrantPortForwarding> portForwardings;

    private List<VagrantSyncFolder> syncFolders;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	private String name;

	private String ip;
	
	private String boxName;
	
	private URL boxUrl;
	
	private String hostName;
	
	private boolean guiMode;

    private boolean privateNetwork;

    private String guest;

    private Map<String, String> modifyVm;

    public VagrantVmConfigBuilder() {
		portForwardings = new ArrayList<VagrantPortForwarding>();
        syncFolders = new ArrayList<VagrantSyncFolder>();
        modifyVm = new HashMap<String, String>();
	}
	
	public static VagrantVmConfigBuilder create() {
		return new VagrantVmConfigBuilder();
	}
	
	public VagrantVmConfigBuilder withPuppetProvisionerConfig(PuppetProvisionerConfig puppetProvisionerConfig) {
		this.puppetProvisionerConfig = puppetProvisionerConfig;
		return this;
	}
	
	public VagrantVmConfigBuilder withVagrantPortForwarding(VagrantPortForwarding portForwarding) {
		this.portForwardings.add(portForwarding);
		return this;
	}

    public VagrantVmConfigBuilder withVagrantSyncFolders(VagrantSyncFolder syncFolder) {
        this.syncFolders.add(syncFolder);
        return this;
    }
	
	public VagrantVmConfigBuilder withHostName(String hostName) {
		this.hostName = hostName;
		return this;
	}
	
	public VagrantVmConfigBuilder withGuiMode(boolean guiMode) {
		this.guiMode = guiMode;
		return this;
	}
	
	public VagrantVmConfigBuilder withName(String name) {
		this.name = name;
		return this;
	}

    public VagrantVmConfigBuilder withSynchHostOnlyIp(String ip) {
        this.ip = ip;
        return this;
    }

	public VagrantVmConfigBuilder withHostOnlyIp(String ip) {
		this.ip = ip;
		return this;
	}

    public VagrantVmConfigBuilder withPrivateNetworkIp(String ip) {
        this.ip = ip;
        this.privateNetwork = true;
        return this;
    }
	
	public VagrantVmConfigBuilder withLucid32Box() {
		this.boxName = "lucid32";
		try {
			this.boxUrl = new URL("http://files.vagrantup.com/lucid32.box");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	public VagrantVmConfigBuilder withLucid64Box() {
		this.boxName = "lucid64";
		try {
			this.boxUrl = new URL("http://files.vagrantup.com/lucid64.box");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	public VagrantVmConfigBuilder withBoxName(String boxName) {
		this.boxName = boxName;
		return this;
	}

    public VagrantVmConfigBuilder withVmGuest(String guest) {
        this.guest = guest;
        return this;
    }
	
	public VagrantVmConfigBuilder withBoxUrl(URL boxUrl) {
		this.boxUrl = boxUrl;
		return this;
	}

    public VagrantVmConfigBuilder withModifyVm(String name, String value) {
        this.modifyVm.put(name, value);
        return this;
    }

	public VagrantVmConfig build() {
		if(boxName == null) {
			throw new VagrantBuilderException("No boxName defined");
		}
		return new VagrantVmConfig(name, ip, hostName, boxName, boxUrl, guest, portForwardings, syncFolders, puppetProvisionerConfig, guiMode, privateNetwork, modifyVm);
	}
}
