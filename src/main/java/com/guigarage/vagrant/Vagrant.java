package com.guigarage.vagrant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jruby.RubyObject;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;

import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;
import com.guigarage.vagrant.model.VagrantEnvironment;

public class Vagrant {

	private ScriptingContainer scriptingContainer;
	
	public Vagrant() {
		this(false);
	}
	
	public Vagrant(boolean debug) {
		scriptingContainer = new ScriptingContainer(
				LocalContextScope.SINGLETHREAD);
		if(debug) {
			debug();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void debug() {
		Map currentEnv = scriptingContainer.getEnvironment();
		Map newEnv = new HashMap<>(currentEnv);
		newEnv.put("VAGRANT_LOG", "DEBUG");
		scriptingContainer.setEnvironment(newEnv);
	}
	
	public VagrantEnvironment createEnvironment() {
		RubyObject vagrantEnv = (RubyObject) scriptingContainer.runScriptlet("require 'rubygems'\n"
				+ "require 'vagrant'\n"
				+ "\n" + "return Vagrant::Environment.new");
		return new VagrantEnvironment(vagrantEnv);
	}
	
	public VagrantEnvironment createEnvironment(File path) {
		RubyObject vagrantEnv = (RubyObject) scriptingContainer.runScriptlet("require 'rubygems'\n"
				+ "require 'vagrant'\n"
				+ "\n" + "return Vagrant::Environment.new(:cwd => '" + path.getAbsolutePath() + "')");
		return new VagrantEnvironment(vagrantEnv);
	}
	
	public VagrantEnvironment createEnvironment(File path, VagrantEnvironmentConfig environmentConfig) throws IOException {
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(environmentConfig), null);
	}
	
	public VagrantEnvironment createEnvironment(File path, VagrantEnvironmentConfig environmentConfig, Iterable<VagrantFileTemplateConfiguration> fileTemplates) throws IOException {
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(environmentConfig), fileTemplates);
	}
	
	public VagrantEnvironment createEnvironment(File path, VagrantConfiguration configuration) throws IOException {
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(configuration.getEnvironmentConfig()), configuration.getFileTemplateConfigurations());
	}
	
	public VagrantEnvironment createEnvironment(File path, String vagrantfileContent, Iterable<VagrantFileTemplateConfiguration> fileTemplates) throws IOException {
		path.mkdirs();
		File vagrantFile = new File(path, "Vagrantfile");
		if(!vagrantFile.exists()) {
			vagrantFile.createNewFile();
		}
		FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		if(fileTemplates != null) {
			for(VagrantFileTemplateConfiguration fileTemplate : fileTemplates) {
				FileUtils.copyFile(fileTemplate.getLocalFile(), new File(path, fileTemplate.getPathInVagrantFolder()));
			}
		}
		return createEnvironment(path);
	}	
}
