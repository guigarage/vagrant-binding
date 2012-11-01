package com.guigarage.vagrant.configuration;

import java.io.File;
import java.net.URL;

public class VagrantFileTemplateConfiguration {

	private File localFile;

	private URL urlTemplate;
	
	private String pathInVagrantFolder;
	
	public VagrantFileTemplateConfiguration(URL urlTemplate, String pathInVagrantFolder) {
		this.urlTemplate = urlTemplate;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}
	
	public VagrantFileTemplateConfiguration(File localFile, String pathInVagrantFolder) {
		this.localFile = localFile;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}
	
	public boolean useUrlTemplate() {
		if(urlTemplate != null) {
			return true;
		}
		return false;
	}
	
	public boolean useLocalFile() {
		if(localFile != null) {
			return true;
		}
		return false;
	}
	
	public URL getUrlTemplate() {
		return urlTemplate;
	}
	
	public File getLocalFile() {
		return localFile;
	}
	
	public String getPathInVagrantFolder() {
		return pathInVagrantFolder;
	}
}
