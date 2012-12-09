package com.guigarage.vagrant.model;

import org.jruby.RubyBoolean;
import org.jruby.RubyNumeric;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.util.VagrantException;

/**
 * Wrapper for a Vagrant SSH connection. The class contains the JRuby object for the connections and forwards the method calls to it.
 * You can execute commands on the VM or upload files to it.
 * @author hendrikebbers
 *
 */
public class VagrantSSHConnection {

	private RubyObject vagrantSSH;

	/**
	 * Constructor for the SHH connection. Normally you do not need to create a connection on your own. Use {@link VagrantVm.createConnection()} to create a new SSH connection.
	 * @param vagrantSSH The Vagrant SSH connection object
	 */
	public VagrantSSHConnection(RubyObject vagrantSSH) {
		this.vagrantSSH = vagrantSSH;
	}

	/**
	 * Checks if the connection is ready. Normally you do not need this this methode because the connection should be always ready.
	 * @return true if the SSH connection is ready
	 */
	public boolean isReady() {
		try {
			return ((RubyBoolean) vagrantSSH.callMethod("ready?")).isTrue();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Uploads a file to the vm.
	 * @param localPath the locale path of the file
	 * @param pathOnVM the path on the vm where you want to upload the local file
	 */
	public void upload(String localPath, String pathOnVM) {
		try {
			vagrantSSH.callMethod("upload",
					RubyString.newString(vagrantSSH.getRuntime(), localPath),
					RubyString.newString(vagrantSSH.getRuntime(), pathOnVM));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Executes a command on the vm
	 * @param command the command you want to execute. for example "touch /file.tmp"
	 * @param sudo if true the command will be executed as sudo
	 * @return the returncode of the command
	 */
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
	public int execute(String command) {
		return execute(command, false);
	}
}
