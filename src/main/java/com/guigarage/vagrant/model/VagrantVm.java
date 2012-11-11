package com.guigarage.vagrant.model;

import org.jruby.RubyObject;
import org.jruby.RubySymbol;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.util.VagrantException;

/**
 * This class gives you acces to on VM. You can manage the lifecycle of this VM and acces the VM by SSH.
 * 
 * @author hendrikebbers
 *
 */
public class VagrantVm {

	private RubyObject vagrantVm;

	/**
	 * The {@link VagrantVm} is a wrapper for a Vagrant VM. The class contains the JRuby object for the connections and forwards the method calls to it. This constructor is used by the builder classes or the {@link VagrantEnvironment} class. You do not need to call it in your code
	 * @param vagrantVm The Vagrant VM connection object
	 */
	public VagrantVm(RubyObject vagrantVm) {
		this.vagrantVm = vagrantVm;
	}

	/**
	 * Creates & starts the VM
	 */
	public void up() {
		try {
			vagrantVm.callMethod("up");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Starts the VM
	 */
	public void start() {
		try {
			vagrantVm.callMethod("start");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Halts the VM
	 */
	public void halt() {
		try {
			vagrantVm.callMethod("halt");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Reloades the VM
	 */
	public void reload() {
		try {
			vagrantVm.callMethod("reload");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Destroyes the VM
	 */
	public void destroy() {
		try {
			vagrantVm.callMethod("destroy");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Supsends the VM
	 */
	public void suspend() {
		try {
			vagrantVm.callMethod("suspend");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void resume() {
		try {
			vagrantVm.callMethod("resume");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns true if the VM is running
	 * @return true if the VM is running
	 */
	public boolean isRunning() {
		String state = getState();
		if (state.equals("running")) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the VM is created.
	 * @return true if the VM is created.
	 */
	public boolean isCreated() {
		String state = getState();
		if (state.equals("not_created")) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the VM is paused.
	 * @return true if the VM is paused.
	 */
	public boolean isPaused() {
		String state = getState();
		if (state.equals("saved")) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the state of the VM. Known states are "not_created", "aborted", "poweroff", "running" and "saved"
	 * @return the state of the VM
	 */
	private String getState() {
		// not_created, aborted, poweroff, running, saved
		// not_created: VM ist aktuell nicht angelegt
		// aborted: VM wurde (hart) abgebrochen
		// poweroff: VM ist vorhanden aber runtergefahren
		// running: VM läuft
		// saved: VM wurde pausiert
		try {
			RubySymbol symbol = (RubySymbol) vagrantVm.callMethod("state");
			return symbol.asJavaString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns the name of the VM
	 * @return the name of the VM
	 */
	public String getName() {
		try {
			return ((RubyObject) vagrantVm.callMethod("name")).toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns a new SSH connection to this VM. you can use the connection for upload files or execute command
	 * @return a new SSH connection
	 */
	public VagrantSSHConnection createConnection() {
		try {
			return new VagrantSSHConnection(
					((RubyObject) vagrantVm.callMethod("channel")));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns the UUID that Vagrant uses internally
	 * @return the UUID of this VM
	 */
	public String getUuid() {
		try {
			return ((RubyObject) vagrantVm.callMethod("uuid")).toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}
}
