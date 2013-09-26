package com.guigarage.vagrant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jruby.RubyObject;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;
import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.model.VagrantEnvironment;

public class Vagrant {

	private ScriptingContainer scriptingContainer;

    public void shutdown() {
        if (scriptingContainer != null) {
            scriptingContainer.terminate();
        }
    }

    public enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR,
    }

	public Vagrant(LogLevel logLevel) {
		scriptingContainer = new ScriptingContainer(LocalContextScope.THREADSAFE, LocalVariableBehavior.PERSISTENT);

        Map currentEnv = scriptingContainer.getEnvironment();
        Map newEnv = new HashMap(currentEnv);
        newEnv.put("VAGRANT_LOG", logLevel.toString());

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
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(environmentConfig), null, null);
	}
	
	public VagrantEnvironment createEnvironment(File path, VagrantEnvironmentConfig environmentConfig, Iterable<VagrantFileTemplateConfiguration> fileTemplates) throws IOException {
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(environmentConfig), fileTemplates, null);
	}
	
	public VagrantEnvironment createEnvironment(File path, VagrantEnvironmentConfig environmentConfig, Iterable<VagrantFileTemplateConfiguration> fileTemplates, Iterable<VagrantFolderTemplateConfiguration> folderTemplates) throws IOException {
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(environmentConfig), fileTemplates, folderTemplates);
	}
	
	public VagrantEnvironment createEnvironment(File path, VagrantConfiguration configuration) throws IOException {
		return createEnvironment(path, VagrantConfigurationUtilities.createVagrantFileContent(configuration.getEnvironmentConfig()), configuration.getFileTemplateConfigurations(), configuration.getFolderTemplateConfigurations());
	}
	
	public VagrantEnvironment createEnvironment(File path, String vagrantfileContent, Iterable<VagrantFileTemplateConfiguration> fileTemplates, Iterable<VagrantFolderTemplateConfiguration> folderTemplates) throws IOException {
		path.mkdirs();
		File vagrantFile = new File(path, "Vagrantfile");
		if(!vagrantFile.exists()) {
			vagrantFile.createNewFile();
		}
		FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		if(fileTemplates != null) {
			for(VagrantFileTemplateConfiguration fileTemplate : fileTemplates) {
				File fileInVagrantFolder = new File(path, fileTemplate.getPathInVagrantFolder());
				if(fileInVagrantFolder.getParentFile() != null && !fileInVagrantFolder.getParentFile().exists()) {
					fileInVagrantFolder.getParentFile().mkdirs();
				}
				if(fileTemplate.useLocalFile()) {
					FileUtils.copyFile(fileTemplate.getLocalFile(), fileInVagrantFolder);
				} else {
					FileUtils.copyURLToFile(fileTemplate.getUrlTemplate(), fileInVagrantFolder);
				}
			}
		}
		if(folderTemplates != null) {
			for(VagrantFolderTemplateConfiguration folderTemplate : folderTemplates) {
				File folderInVagrantFolder = new File(path, folderTemplate.getPathInVagrantFolder());
				if(folderTemplate.useUriTemplate()) {
					FileUtils.copyDirectory(new File(folderTemplate.getUriTemplate()), folderInVagrantFolder);
				} else {
					FileUtils.copyDirectory(folderTemplate.getLocalFolder(), folderInVagrantFolder);
				}
			}
		}
		return createEnvironment(path);
	}	
}
