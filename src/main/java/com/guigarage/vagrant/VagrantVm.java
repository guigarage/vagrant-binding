package com.guigarage.vagrant;

import org.jruby.RubyHash;
import org.jruby.RubyObject;
import org.jruby.RubySymbol;

public class VagrantVm {

	private RubyObject vagrantVm;

	public VagrantVm(RubyObject vagrantVm) {
		this.vagrantVm = vagrantVm;
	}

	public void up() {
		vagrantVm.callMethod("up");
	}

	public void start() {
		vagrantVm.callMethod("start");
	}

	public void halt() {
		vagrantVm.callMethod("halt");
	}

	public void reload() {
		vagrantVm.callMethod("reload");
	}

	public void destroy() {
		vagrantVm.callMethod("destroy");
	}

	public void suspend() {
		vagrantVm.callMethod("suspend");
	}

	public void resume() {
		vagrantVm.callMethod("resume");
	}
	
	public boolean isRunning() {
		String state = getState();
		if(state.equals("running")) {
			return true;
		}
		return false;
	}
	
	public boolean isCreated() {
		String state = getState();
		if(state.equals("not_created")) {
			return false;
		}
		return true;
	}
	
	public boolean isPaused() {
		String state = getState();
		if(state.equals("saved")) {
			return true;
		}
		return false;
	}
	
	private String getState() {
		//not_created, aborted, poweroff, running, saved
		//not_created: VM ist aktuell nicht angelegt
		//aborted: VM wurde (hart) abgebrochen
		//poweroff: VM ist vorhanden aber runtergefahren
		//running: VM läuft
		//saved: VM wurde pausiert
		RubySymbol symbol = (RubySymbol) vagrantVm.callMethod("state");
		return symbol.asJavaString();
	}

	public String getName() {
		return ((RubyObject) vagrantVm.callMethod("name")).toString();
	}
	
	public VagrantSSHConnection createConnection() {
		return new VagrantSSHConnection(((RubyObject) vagrantVm.callMethod("channel")));
	}
	
	public String getUuid() {
		return ((RubyObject) vagrantVm.callMethod("uuid")).toString();
	}
}
