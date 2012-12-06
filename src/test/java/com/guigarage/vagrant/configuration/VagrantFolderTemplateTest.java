package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.configuration.builder.VagrantConfigurationBuilder;
import com.guigarage.vagrant.configuration.builder.VagrantEnvironmentConfigBuilder;
import com.guigarage.vagrant.configuration.builder.VagrantFolderTemplateConfigurationBuilder;
import com.guigarage.vagrant.configuration.builder.VagrantVmConfigBuilder;
import com.guigarage.vagrant.model.VagrantEnvironment;

public class VagrantFolderTemplateTest {

	@Test
	public void testFolderTemplates() {
		File localeFolder = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		File vagrantPath = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		try {
			localeFolder.mkdirs();
			File testFile = new File(localeFolder, "file.tmp");
			try {
				testFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				Assert.fail(e1.getMessage());
			}

			VagrantFolderTemplateConfiguration folderTemplateConfiguration = VagrantFolderTemplateConfigurationBuilder
					.create().withPathInVagrantFolder("testFolder")
					.withLocalFolder(localeFolder).build();
			VagrantVmConfig vmConfig = VagrantVmConfigBuilder.create()
					.withLucid32Box().build();
			VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfigBuilder
					.create().withVagrantVmConfig(vmConfig).build();
			VagrantConfiguration vagrantConfiguration = VagrantConfigurationBuilder
					.create()
					.withVagrantEnvironmentConfig(environmentConfig)
					.withVagrantFolderTemplateConfiguration(
							folderTemplateConfiguration).build();

			Vagrant vagrant = new Vagrant(true);
			VagrantEnvironment environment = null;
			try {
				environment = vagrant.createEnvironment(vagrantPath,
						vagrantConfiguration);
				File vagrantFolder = new File(environment.getRootPath());
				File createdFolder = new File(vagrantFolder, "testFolder");
				Assert.assertEquals(true, createdFolder.exists());
				Assert.assertEquals(true, createdFolder.isDirectory());
				File createdFile = new File(createdFolder, "file.tmp");
				Assert.assertEquals(true, createdFile.exists());
				Assert.assertEquals(true, createdFile.isFile());
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		} finally {
			FileUtils.deleteQuietly(localeFolder);
			FileUtils.deleteQuietly(vagrantPath);
		}
	}

	@Test
	public void testFolderUriTemplates() {
		File localeFolder = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		File vagrantPath = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		try {
			localeFolder.mkdirs();
			File testFile = new File(localeFolder, "file.tmp");
			try {
				testFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				Assert.fail(e1.getMessage());
			}

			VagrantFolderTemplateConfiguration folderTemplateConfiguration = VagrantFolderTemplateConfigurationBuilder
					.create().withPathInVagrantFolder("testFolder")
					.withUrlTemplate(localeFolder.toURI()).build();
			VagrantVmConfig vmConfig = VagrantVmConfigBuilder.create()
					.withLucid32Box().build();
			VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfigBuilder
					.create().withVagrantVmConfig(vmConfig).build();
			VagrantConfiguration vagrantConfiguration = VagrantConfigurationBuilder
					.create()
					.withVagrantEnvironmentConfig(environmentConfig)
					.withVagrantFolderTemplateConfiguration(
							folderTemplateConfiguration).build();

			Vagrant vagrant = new Vagrant(true);
			VagrantEnvironment environment = null;
			try {
				environment = vagrant.createEnvironment(vagrantPath,
						vagrantConfiguration);
				File vagrantFolder = new File(environment.getRootPath());
				File createdFolder = new File(vagrantFolder, "testFolder");
				Assert.assertEquals(true, createdFolder.exists());
				Assert.assertEquals(true, createdFolder.isDirectory());
				File createdFile = new File(createdFolder, "file.tmp");
				Assert.assertEquals(true, createdFile.exists());
				Assert.assertEquals(true, createdFile.isFile());
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		} finally {
			FileUtils.deleteQuietly(localeFolder);
			FileUtils.deleteQuietly(vagrantPath);
		}
	}
	
	@Test
	public void testWithoutFolderTemplates() {
		File vagrantPath = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		try {
			VagrantVmConfig vmConfig = VagrantVmConfigBuilder.create()
					.withLucid32Box().build();
			VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfigBuilder
					.create().withVagrantVmConfig(vmConfig).build();
			VagrantConfiguration vagrantConfiguration = VagrantConfigurationBuilder
					.create()
					.withVagrantEnvironmentConfig(environmentConfig)
					.build();

			Vagrant vagrant = new Vagrant(true);
			try {
				vagrant.createEnvironment(vagrantPath,
						vagrantConfiguration);
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		} finally {
			FileUtils.deleteQuietly(vagrantPath);
		}
	}
	
	@Test
	public void testBuilderWithoutFolderTemplates() {
		try {
			VagrantFolderTemplateConfigurationBuilder
					.create().withPathInVagrantFolder("testFolder")
					.withLocalFolder(null).build();
			Assert.fail();
		} catch (Exception e) {
		}
	}
}
