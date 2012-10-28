package com.guigarage.vagrant.configuration;

import java.io.File;

public class VagrantFileTemplateConfiguration {

	private File localFile;
	
	private String pathInVagrantFolder;
	
	public VagrantFileTemplateConfiguration(File localFile, String pathInVagrantFolder) {
		this.localFile = localFile;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}
	
	public File getLocalFile() {
		return localFile;
	}
	
	public String getPathInVagrantFolder() {
		return pathInVagrantFolder;
	}
}
