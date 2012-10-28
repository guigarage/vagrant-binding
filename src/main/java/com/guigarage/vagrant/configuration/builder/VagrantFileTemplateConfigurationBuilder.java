package com.guigarage.vagrant.configuration.builder;

import java.io.File;

import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;

public class VagrantFileTemplateConfigurationBuilder {

	private File localFile;

	private String pathInVagrantFolder;

	public VagrantFileTemplateConfigurationBuilder() {
	}

	public VagrantFileTemplateConfigurationBuilder withLocalFile(File localFile) {
		this.localFile = localFile;
		return this;
	}
	
	public VagrantFileTemplateConfigurationBuilder withPathInVagrantFolder(String pathInVagrantFolder) {
		this.pathInVagrantFolder = pathInVagrantFolder;
		return this;
	}
	
	public VagrantFileTemplateConfiguration create() {
		return new VagrantFileTemplateConfiguration(localFile,
				pathInVagrantFolder);
	}
}
