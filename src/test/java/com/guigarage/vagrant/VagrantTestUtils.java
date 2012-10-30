package com.guigarage.vagrant;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

public class VagrantTestUtils {

	public static File createTempDir() {
		File mainTempDir = FileUtils.getTempDirectory();
		File tempDir = new File(mainTempDir, "vagrant-"
				+ UUID.randomUUID().toString());
		tempDir.mkdirs();
		return tempDir;
	}
}
