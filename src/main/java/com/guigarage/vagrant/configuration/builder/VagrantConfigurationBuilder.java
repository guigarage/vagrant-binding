package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;
import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

public class VagrantConfigurationBuilder {

	private VagrantEnvironmentConfig environmentConfig;

	private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations;

	private List<VagrantFolderTemplateConfiguration> folderTemplateConfigurations;

	public VagrantConfigurationBuilder() {
		fileTemplateConfigurations = new ArrayList<VagrantFileTemplateConfiguration>();
		folderTemplateConfigurations = new ArrayList<VagrantFolderTemplateConfiguration>();
	}

	public static VagrantConfigurationBuilder create() {
		return new VagrantConfigurationBuilder();
	}

	public VagrantConfigurationBuilder withVagrantEnvironmentConfig(
			VagrantEnvironmentConfig environmentConfig) {
		this.environmentConfig = environmentConfig;
		return this;
	}

	public VagrantConfigurationBuilder withVagrantFileTemplateConfiguration(
			VagrantFileTemplateConfiguration fileTemplateConfiguration) {
		this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		return this;
	}

	public VagrantConfigurationBuilder withVagrantFolderTemplateConfiguration(
			VagrantFolderTemplateConfiguration folderTemplateConfiguration) {
		this.folderTemplateConfigurations.add(folderTemplateConfiguration);
		return this;
	}

	public VagrantConfiguration build() {
		if (environmentConfig == null) {
			throw new VagrantBuilderException(
					"No VagrantEnvironmentConfig defined");
		}
		return new VagrantConfiguration(environmentConfig,
				fileTemplateConfigurations, folderTemplateConfigurations);
	}
}
