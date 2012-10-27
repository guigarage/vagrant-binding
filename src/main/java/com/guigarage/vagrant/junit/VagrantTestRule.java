package com.guigarage.vagrant.junit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantEnvironment;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;

public class VagrantTestRule extends TestWatcher {

	private VagrantEnvironment environment;

	private File vagrantDir;

	public VagrantTestRule(VagrantEnvironmentConfig environmentConfig) {
		this(VagrantConfigurationUtilities
				.createVagrantFileContent(environmentConfig));
	}

	public VagrantTestRule(File vagrantFileMaster) {
		try {
			String vagrantFileContent = FileUtils
					.readFileToString(vagrantFileMaster);
			File tmpDir = FileUtils.getTempDirectory();
			vagrantDir = new File(tmpDir, "vagrant-"
					+ UUID.randomUUID().toString());
			init("Vagrantfile", vagrantFileContent);
		} catch (IOException e) {
			throw new RuntimeException("Can't create Vagrantfolder!", e);
		}
	}

	public VagrantTestRule(String vagrantFileContent) {
		File tmpDir = FileUtils.getTempDirectory();
		vagrantDir = new File(tmpDir, "vagrant-" + UUID.randomUUID().toString());
		init("Vagrantfile", vagrantFileContent);
	}

	private synchronized void init(String vagrantfileName,
			String vagrantfileContent) {
		File vagrantFile = new File(vagrantDir, vagrantfileName);
		try {
			FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		} catch (IOException e) {
			throw new RuntimeException("Error while creating "
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
				throw new RuntimeException("Can't clean Vagrantfolder", e2);
			}
		}
	}

}
