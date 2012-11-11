package com.guigarage.vagrant.configuration;

import java.io.File;
import java.net.URL;

/**
 * A configuration for a Vagrant file template. The local file defined by this
 * template will copied into the folder of the Vagrant environment the VM is
 * running in.
 * 
 * @author hendrikebbers
 * 
 */
public class VagrantFileTemplateConfiguration {

	//TODO: Das kopieren der Dateien sollte in eine Methode in dieser Klasse augelagert werden die das VagrantEnvironment als übergabeparameter bekommt.
	
	private File localFile;

	private URL urlTemplate;

	private String pathInVagrantFolder;

	/**
	 * Creates a new {@link VagrantFileTemplateConfiguration} that uses a URL for the template file
	 * @param urlTemplate url of the template
	 * @param pathInVagrantFolder path in Vagrant folder where the template should be copied
	 */
	public VagrantFileTemplateConfiguration(URL urlTemplate,
			String pathInVagrantFolder) {
		this.urlTemplate = urlTemplate;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	/**
	 * Creates a new {@link VagrantFileTemplateConfiguration} that uses a local path for the template file
	 * @param localFile locale path of the template
	 * @param pathInVagrantFolder path in Vagrant folder where the template should be copied
	 */
	public VagrantFileTemplateConfiguration(File localFile,
			String pathInVagrantFolder) {
		this.localFile = localFile;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	/**
	 * You can use a locale path or a URL to define the local file. So you can
	 * also use any data / file from the internet or the classpath. This returns
	 * true if a URL is used for the local file.
	 * 
	 * @return true if a URL is used for the local file
	 */
	public boolean useUrlTemplate() {
		if (urlTemplate != null) {
			return true;
		}
		return false;
	}

	/**
	 * You can use a locale path or a URL to define the local file. So you can
	 * also use any data / file from the internet or the classpath. This returns
	 * true if a path is used for the local file.
	 * @return true if a path is used for the local file
	 */
	public boolean useLocalFile() {
		if (localFile != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the URL of the template file
	 * @return the URL of the template file
	 */
	public URL getUrlTemplate() {
		return urlTemplate;
	}

	/**
	 * Returns the locale path of the template file
	 * @return the locale path of the template file
	 */
	public File getLocalFile() {
		return localFile;
	}

	/**
	 * Returns the path inside the Vagrant folder where the template should be copied to.
	 * @return the path inside the Vagrant folder
	 */
	public String getPathInVagrantFolder() {
		return pathInVagrantFolder;
	}
}
