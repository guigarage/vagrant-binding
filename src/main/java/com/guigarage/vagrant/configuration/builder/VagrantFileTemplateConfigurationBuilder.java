package com.guigarage.vagrant.configuration.builder;

import java.io.File;

import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;

public class VagrantFileTemplateConfigurationBuilder {

	private File localFile;

	private String pathInVagrantFolder;

	public VagrantFileTemplateConfigurationBuilder() {
	}

	public static VagrantFileTemplateConfigurationBuilder create() {
		return new VagrantFileTemplateConfigurationBuilder();
	}
	
	public VagrantFileTemplateConfigurationBuilder withLocalFile(File localFile) {
		this.localFile = localFile;
		return this;
	}
	
	public VagrantFileTemplateConfigurationBuilder withPathInVagrantFolder(String pathInVagrantFolder) {
		this.pathInVagrantFolder = pathInVagrantFolder;
		return this;
	}
	
	public VagrantFileTemplateConfiguration build() {
		return new VagrantFileTemplateConfiguration(localFile,
				pathInVagrantFolder);
	}
}
