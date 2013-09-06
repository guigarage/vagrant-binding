package com.guigarage.vagrant.junit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;
import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.VagrantException;

/**
 * A JUnit Rule that can be used by the {@link Rule} annotation. The {@link VagrantTestRule} will create a Vagrant environment before every test and destroys it after the test.
 * @author hendrikebbers
 *
 */
public class VagrantTestRule extends TestWatcher {

	private VagrantEnvironment environment;

    private Vagrant vagrant;

	private File vagrantDir;

	/**
	 * The VagrantTestRule will use the given {@link VagrantEnvironmentConfig} for the Vagrant environment that is wrapped around the tests.
	 * @param environmentConfig the configuration for the Vagrant enviroment.
	 */
	public VagrantTestRule(VagrantEnvironmentConfig environmentConfig) {
		this(VagrantConfigurationUtilities
				.createVagrantFileContent(environmentConfig));
	}

	/**
	 * The VagrantTestRule will use the given vagrantfile for the Vagrant environment that is wrapped around the tests.
	 * @param vagrantFileContent the content of the vagrantfile for the Vagrant enviroment.
	 */
	public VagrantTestRule(String vagrantFileContent) {
		File tmpDir = FileUtils.getTempDirectory();
		vagrantDir = new File(tmpDir, "vagrant-" + UUID.randomUUID().toString());
		
		init("Vagrantfile", vagrantFileContent);
	}

	/**
	 * The VagrantTestRule will use the given {@link VagrantConfiguration} for the Vagrant environment that is wrapped around the tests.
	 * @param configuration the configuration for the Vagrant enviroment.
	 */
	public VagrantTestRule(VagrantConfiguration configuration) {
		try {
		File tmpDir = FileUtils.getTempDirectory();
		vagrantDir = new File(tmpDir, "vagrant-" + UUID.randomUUID().toString());
		
		
		if(configuration.getFileTemplateConfigurations() != null) {
			for(VagrantFileTemplateConfiguration fileTemplate : configuration.getFileTemplateConfigurations()) {
				File fileInVagrantFolder = new File(vagrantDir, fileTemplate.getPathInVagrantFolder());
				if(fileTemplate.useLocalFile()) {
					FileUtils.copyFile(fileTemplate.getLocalFile(), fileInVagrantFolder);
				} else {
					FileUtils.copyURLToFile(fileTemplate.getUrlTemplate(), fileInVagrantFolder);
				}
			}
		}
		
		if(configuration.getFolderTemplateConfigurations() != null) {
			for(VagrantFolderTemplateConfiguration folderTemplate : configuration.getFolderTemplateConfigurations()) {
				File folderInVagrantFolder = new File(vagrantDir, folderTemplate.getPathInVagrantFolder());
				if(folderTemplate.useUriTemplate()) {
					FileUtils.copyDirectory(new File(folderTemplate.getUriTemplate()), folderInVagrantFolder);
				} else {
					FileUtils.copyDirectory(folderTemplate.getLocalFolder(), folderInVagrantFolder);
				}
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
		vagrant = new Vagrant(Vagrant.LogLevel.DEBUG);
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

	/**
	 * This gives you access to the VagrantEnvironment while the test is running. You can use it to execute some commands on the VMs etc.
	 * @return the created VagrantEnvironment for the current unittest
	 */
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
        if (vagrant != null) {
            vagrant.shutdown();
        }
	}

}
