package com.guigarage.vagrant.model;

import org.jruby.RubyObject;
import org.jruby.RubySymbol;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.util.VagrantException;

public class VagrantVm {

	private RubyObject vagrantVm;

	public VagrantVm(RubyObject vagrantVm) {
		this.vagrantVm = vagrantVm;
	}

	public void up() {
		try {
			vagrantVm.callMethod("up");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void start() {
		try {
			vagrantVm.callMethod("start");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void halt() {
		try {
			vagrantVm.callMethod("halt");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void reload() {
		try {
			vagrantVm.callMethod("reload");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void destroy() {
		try {
			vagrantVm.callMethod("destroy");
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

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

	public boolean isRunning() {
		String state = getState();
		if (state.equals("running")) {
			return true;
		}
		return false;
	}

	public boolean isCreated() {
		String state = getState();
		if (state.equals("not_created")) {
			return false;
		}
		return true;
	}

	public boolean isPaused() {
		String state = getState();
		if (state.equals("saved")) {
			return true;
		}
		return false;
	}

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

	public String getName() {
		try {
			return ((RubyObject) vagrantVm.callMethod("name")).toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public VagrantSSHConnection createConnection() {
		try {
			return new VagrantSSHConnection(
					((RubyObject) vagrantVm.callMethod("channel")));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public String getUuid() {
		try {
			return ((RubyObject) vagrantVm.callMethod("uuid")).toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}
}
