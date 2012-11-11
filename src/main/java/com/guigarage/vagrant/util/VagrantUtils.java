package com.guigarage.vagrant.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * Some general utilities for Vagrant.
 * @author hendrikebbers
 *
 */
public class VagrantUtils {

	private static VagrantUtils instance;

	private VagrantUtils() {
	}

	public synchronized static VagrantUtils getInstance() {
		if (instance == null) {
			instance = new VagrantUtils();
		}
		return instance;
	}

	/**
	 * Creates a URL for a ressource from classpath.
	 * @param path the path to the needed resource
	 * @return URL for the resource
	 * @throws IOException if the resource is not in the classpath
	 */
	public URL load(String path) throws IOException {
		URL url = ClassLoader.getSystemClassLoader().getResource(path);
		if (url == null) {
			// For use in JAR
			url = this.getClass().getResource(path);
		}
		if (url == null) {
			url = ClassLoader.getSystemResource(path);
		}
		if (url == null) {
			throw new IOException("Can't create URL for path " + path);
		}
		return url;
	}

	/**
	 * Returns a basic Vagrantfile that uses the lucid32 box as template
	 * @return the content of the Vagrantfile as String
	 */
	public String getLucid32MasterContent() {
		try {
			URL fileUrl = load("com/guigarage/vagrant/master/lucid32");
			return IOUtils.toString(fileUrl);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Returns a basic Vagrantfile that uses the lucid64 box as template
	 * @return the content of the Vagrantfile as String
	 */
	public String getLucid64MasterContent() {
		try {
			URL fileUrl = load("com/guigarage/vagrant/master/lucid64");
			return IOUtils.toString(fileUrl);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/**
	 * Returns the default URL for the lucid32 box. The box is hosted at vagrantup.com
	 * @return The URL for the box
	 */
	public URL getLucid32Url() {
		try {
			return new URL("http://files.vagrantup.com/lucid32.box");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns the default URL for the lucid64 box. The box is hosted at vagrantup.com
	 * @return The URL for the box
	 */
	public URL getLucid64Url() {
		try {
			return new URL("http://files.vagrantup.com/lucid64.box");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
