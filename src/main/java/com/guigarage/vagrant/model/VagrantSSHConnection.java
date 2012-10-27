package com.guigarage.vagrant.model;

import org.jruby.RubyBoolean;
import org.jruby.RubyNumeric;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.util.VagrantException;

public class VagrantSSHConnection {

	private RubyObject vagrantSSH;

	public VagrantSSHConnection(RubyObject vagrantSSH) {
		this.vagrantSSH = vagrantSSH;
	}

	public boolean isReady() {
		try {
			return ((RubyBoolean) vagrantSSH.callMethod("ready?")).isTrue();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void upload(String localPath, String pathOnVM) {
		try {
			vagrantSSH.callMethod("upload",
					RubyString.newString(vagrantSSH.getRuntime(), localPath),
					RubyString.newString(vagrantSSH.getRuntime(), pathOnVM));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public int execute(String command, boolean sudo) {
		try {
			if (sudo) {
				RubyNumeric number = (RubyNumeric) vagrantSSH.callMethod(
						"sudo",
						RubyString.newString(vagrantSSH.getRuntime(), command));
				return (int) number.getLongValue();
			} else {
				RubyNumeric number = (RubyNumeric) vagrantSSH.callMethod(
						"execute",
						RubyString.newString(vagrantSSH.getRuntime(), command));
				return (int) number.getLongValue();
			}
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}
}
