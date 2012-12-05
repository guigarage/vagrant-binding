package com.guigarage.vagrant.configuration.builder;

import java.io.File;

import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

public class VagrantFolderTemplateConfigurationBuilder {

	private File localFolder;

	private String pathInVagrantFolder;

	public VagrantFolderTemplateConfigurationBuilder() {
	}

	public static VagrantFolderTemplateConfigurationBuilder create() {
		return new VagrantFolderTemplateConfigurationBuilder();
	}

	public VagrantFolderTemplateConfigurationBuilder withLocalFolder(
			File localFolder) {
		this.localFolder = localFolder;
		return this;
	}

	public VagrantFolderTemplateConfigurationBuilder withPathInVagrantFolder(
			String pathInVagrantFolder) {
		this.pathInVagrantFolder = pathInVagrantFolder;
		return this;
	}

	public VagrantFolderTemplateConfiguration build() {
		if(localFolder == null) {
			throw new VagrantBuilderException("localFolder need to be specified");
		}
		if(pathInVagrantFolder == null) {
			throw new VagrantBuilderException("pathInVagrantFolder need to be specified");
		}
		return new VagrantFolderTemplateConfiguration(localFolder,
				pathInVagrantFolder);
	}
}