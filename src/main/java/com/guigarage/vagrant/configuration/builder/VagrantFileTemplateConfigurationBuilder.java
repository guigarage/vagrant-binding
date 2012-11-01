package com.guigarage.vagrant.configuration.builder;

import java.io.File;
import java.net.URL;

import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;

public class VagrantFileTemplateConfigurationBuilder {

	private File localFile;

	private URL urlTemplate;

	private String pathInVagrantFolder;

	public VagrantFileTemplateConfigurationBuilder() {
	}

	public static VagrantFileTemplateConfigurationBuilder create() {
		return new VagrantFileTemplateConfigurationBuilder();
	}

	public VagrantFileTemplateConfigurationBuilder withUrlTemplate(
			URL urlTemplate) {
		this.urlTemplate = urlTemplate;
		this.localFile = null;
		return this;
	}

	public VagrantFileTemplateConfigurationBuilder withLocalFile(File localFile) {
		this.localFile = localFile;
		this.urlTemplate = null;
		return this;
	}

	public VagrantFileTemplateConfigurationBuilder withPathInVagrantFolder(
			String pathInVagrantFolder) {
		this.pathInVagrantFolder = pathInVagrantFolder;
		return this;
	}

	public VagrantFileTemplateConfiguration build() {
		if (localFile != null) {
			return new VagrantFileTemplateConfiguration(localFile,
					pathInVagrantFolder);
		} else {
			return new VagrantFileTemplateConfiguration(urlTemplate,
					pathInVagrantFolder);
		}
	}
}
