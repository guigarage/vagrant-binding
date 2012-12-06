package com.guigarage.vagrant.configuration.builder;

import java.io.File;
import java.net.URI;

import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

public class VagrantFolderTemplateConfigurationBuilder {

	private File localFolder;

	private String pathInVagrantFolder;
	
	private URI uriTemplate;

	public VagrantFolderTemplateConfigurationBuilder() {
	}

	public static VagrantFolderTemplateConfigurationBuilder create() {
		return new VagrantFolderTemplateConfigurationBuilder();
	}

	public VagrantFolderTemplateConfigurationBuilder withUrlTemplate(
			URI uriTemplate) {
		this.uriTemplate = uriTemplate;
		this.localFolder = null;
		return this;
	}
	
	public VagrantFolderTemplateConfigurationBuilder withLocalFolder(
			File localFolder) {
		this.localFolder = localFolder;
		this.uriTemplate = null;
		return this;
	}

	public VagrantFolderTemplateConfigurationBuilder withPathInVagrantFolder(
			String pathInVagrantFolder) {
		this.pathInVagrantFolder = pathInVagrantFolder;
		return this;
	}

	public VagrantFolderTemplateConfiguration build() {
		if(localFolder == null && uriTemplate == null) {
			throw new VagrantBuilderException("localFolder or uriTemplate need to be specified");
		}
		if(pathInVagrantFolder == null) {
			throw new VagrantBuilderException("pathInVagrantFolder need to be specified");
		}
		if (localFolder != null) {
			return new VagrantFolderTemplateConfiguration(localFolder,
					pathInVagrantFolder);
		} else {
			return new VagrantFolderTemplateConfiguration(uriTemplate,
					pathInVagrantFolder);
		}
	}
}