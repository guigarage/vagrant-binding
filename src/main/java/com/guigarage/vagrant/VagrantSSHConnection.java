package com.guigarage.vagrant;

import org.jruby.RubyBoolean;
import org.jruby.RubyNumeric;
import org.jruby.RubyObject;
import org.jruby.RubyString;

public class VagrantSSHConnection {

	private RubyObject vagrantSSH;

	public VagrantSSHConnection(RubyObject vagrantSSH) {
		this.vagrantSSH = vagrantSSH;
	}

	public boolean isReady() {
		return ((RubyBoolean) vagrantSSH.callMethod("ready?")).isTrue();
	}

	public void upload(String localPath, String pathOnVM) {
		vagrantSSH.callMethod("upload", RubyString.newString(vagrantSSH.getRuntime(), localPath), RubyString.newString(vagrantSSH.getRuntime(), pathOnVM));
	}
	
	public int execute(String command, boolean sudo) {
		if (sudo) {
			RubyNumeric number = (RubyNumeric) vagrantSSH.callMethod("sudo", RubyString.newString(vagrantSSH.getRuntime(), command));
			return (int) number.getLongValue();
		} else {
			RubyNumeric number =  (RubyNumeric) vagrantSSH.callMethod("execute", RubyString.newString(vagrantSSH.getRuntime(), command));
			return (int) number.getLongValue();
		}
	}
}
