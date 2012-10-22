package com.guigarage.vagrant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jruby.RubyObject;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;

public class Vagrant {

	private ScriptingContainer scriptingContainer;
	
	public Vagrant() {
		this(false);
	}
	
	public Vagrant(boolean debug) {
		scriptingContainer = new ScriptingContainer(
				LocalContextScope.SINGLETHREAD);
		if(debug) {
			debug();
		}
		scriptingContainer.runScriptlet("ENV['GEM_PATH']='/Users/hendrikebbers/Documents/workspace/vagrant-binding/jruby/'");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void debug() {
		Map currentEnv = scriptingContainer.getEnvironment();
		Map newEnv = new HashMap<>(currentEnv);
		newEnv.put("VAGRANT_LOG", "DEBUG");
		scriptingContainer.setEnvironment(newEnv);
	}
	
	public VagrantEnvironment createEnvironment() {
		RubyObject vagrantEnv = (RubyObject) scriptingContainer.runScriptlet("require 'rubygems'\n"
				+ "require 'vagrant'\n"
				+ "\n" + "return Vagrant::Environment.new");
		return new VagrantEnvironment(vagrantEnv);
	}
	
	public VagrantEnvironment createEnvironment(File path) {
		return null;
		//TODO: Hier mal überlegen wie die API am besten wär
	}
	
	public VagrantEnvironment createEnvironment(File path, String vagrantfileName) {
		return null;
		//TODO: Hier mal überlegen wie die API am besten wär
	}
	
	public static void main(String[] args) throws Exception {
		Vagrant vagrant = new Vagrant(true);
		VagrantEnvironment environment =  vagrant.createEnvironment();
		final VagrantVm vm = environment.getPrimaryVm();
		System.out.println(vm.getName());
		System.out.println(vm.getUuid());
		if(!vm.isRunning()) {
			vm.up();
		}
		vm.halt();
		VagrantSSHConnection connection = vm.createConnection();
		System.out.println(connection.isReady());
		connection.execute("pwd", false);
	}

}
