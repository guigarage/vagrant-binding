package com.guigarage.vagrant.junit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantEnvironment;

public class VagrantTestRule extends TestWatcher {

	private VagrantEnvironment environment;

	private File vagrantDir;
	
	public VagrantTestRule(File vagrantFileMaster) throws IOException {
		this(FileUtils.readFileToString(vagrantFileMaster));
	}
	
	public VagrantTestRule(String vagrantFileContent) {
		File tmpDir = FileUtils.getTempDirectory();
		vagrantDir = new File(tmpDir, "vagrant-" + UUID.randomUUID().toString());
		init("Vagrantfile", vagrantFileContent);
	}

	private void init(String vagrantfileName, String vagrantfileContent) {
		File vagrantFile = new File(vagrantDir, vagrantfileName);
		try {
			FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		} catch (IOException e) {
			throw new RuntimeException("VagrantTestRule kann nicht erstellt werden!", e);
		}
		Vagrant vagrant = new Vagrant(true);
		environment = vagrant.createEnvironment(vagrantDir);
	}
	
	@Override
	protected void starting(Description description) {
		super.starting(description);
		environment.destroy();
		environment.up();
	}

	@Override
	protected void finished(Description description) {
		super.finished(description);
		environment.destroy();
		clean();
	}

	private void clean() {
		try {
			FileUtils.forceDelete(vagrantDir);
		} catch (Exception e) {
			try {
			FileUtils.forceDeleteOnExit(vagrantDir);
			} catch (Exception e2) {
				//Nix geht mehr...
			}
		}
	}

}
