package com.guigarage.vagrant.junit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.VagrantException;

public class VagrantTestRule extends TestWatcher {

	private VagrantEnvironment environment;

	private File vagrantDir;

	public VagrantTestRule(VagrantEnvironmentConfig environmentConfig) {
		this(VagrantConfigurationUtilities
				.createVagrantFileContent(environmentConfig));
	}

	public VagrantTestRule(File vagrantFileMaster) {
		try {
			File tmpDir = FileUtils.getTempDirectory();
			vagrantDir = new File(tmpDir, "vagrant-"
					+ UUID.randomUUID().toString());
			
			String vagrantFileContent = FileUtils
					.readFileToString(vagrantFileMaster);
			
			init("Vagrantfile", vagrantFileContent);
		} catch (IOException e) {
			throw new VagrantException("Can't create Vagrantfolder!", e);
		}
	}

	public VagrantTestRule(String vagrantFileContent) {
		File tmpDir = FileUtils.getTempDirectory();
		vagrantDir = new File(tmpDir, "vagrant-" + UUID.randomUUID().toString());
		
		init("Vagrantfile", vagrantFileContent);
	}

	public VagrantTestRule(VagrantConfiguration configuration) {
		try {
		File tmpDir = FileUtils.getTempDirectory();
		vagrantDir = new File(tmpDir, "vagrant-" + UUID.randomUUID().toString());
		
		
		if(configuration.getFileTemplateConfigurations() != null) {
			for(VagrantFileTemplateConfiguration fileTemplate : configuration.getFileTemplateConfigurations()) {
				FileUtils.copyFile(fileTemplate.getLocalFile(), new File(vagrantDir, fileTemplate.getPathInVagrantFolder()));
			}
		}
		
		init("Vagrantfile", VagrantConfigurationUtilities
				.createVagrantFileContent(configuration.getEnvironmentConfig()));
		} catch (Exception e) {
			throw new VagrantException(e);
		}
	}
	
	private synchronized void init(String vagrantfileName,
			String vagrantfileContent) {
		File vagrantFile = new File(vagrantDir, vagrantfileName);
		try {
			FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		} catch (IOException e) {
			throw new VagrantException("Error while creating "
					+ this.getClass().getSimpleName(), e);
		}
		Vagrant vagrant = new Vagrant(true);
		environment = vagrant.createEnvironment(vagrantDir);
	}

	@Override
	protected synchronized void starting(Description description) {
		super.starting(description);
		environment.destroy();
		environment.up();
	}

	@Override
	protected synchronized void finished(Description description) {
		super.finished(description);
		environment.destroy();
		clean();
	}

	public VagrantEnvironment getEnvironment() {
		return environment;
	}

	private synchronized void clean() {
		try {
			FileUtils.forceDelete(vagrantDir);
		} catch (Exception e) {
			try {
				FileUtils.forceDeleteOnExit(vagrantDir);
			} catch (Exception e2) {
				throw new VagrantException("Can't clean Vagrantfolder", e2);
			}
		}
	}

}
