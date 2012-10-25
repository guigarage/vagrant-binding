package com.guigarage.vagrant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
		scriptingContainer.runScriptlet("ENV['GEM_PATH']='/Users/hendrikebbers/Documents/github-repositories/vagrant-binding/jruby/'");
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
		RubyObject vagrantEnv = (RubyObject) scriptingContainer.runScriptlet("require 'rubygems'\n"
				+ "require 'vagrant'\n"
				+ "\n" + "return Vagrant::Environment.new(:cwd => '" + path.getAbsolutePath() + "')");
		return new VagrantEnvironment(vagrantEnv);
	}

	public VagrantEnvironment createEnvironment(File path, String vagrantfileContent) throws IOException {
		File vagrantFile = new File(path, "Vagrantfile");
		if(!vagrantFile.exists()) {
			vagrantFile.createNewFile();
		}
		FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		return createEnvironment(path);
	}	
	
	public static void main(String[] args) throws Exception {
		Vagrant vagrant = new Vagrant(true);
		VagrantEnvironment environment =  vagrant.createEnvironment();
		
		//TODO: Wie kann ich rausfinden ob ich das tun muss?????
//		environment.init("lucid64");
		
		final VagrantVm vm = environment.getPrimaryVm();
		System.out.println(vm.getName());
		System.out.println(vm.getIpAdress());
		System.out.println(vm.getUuid());
		if(!vm.isRunning()) {
			vm.up();
		}
		System.out.println(vm.getIpAdress());

		vm.halt();
		System.out.println(vm.getIpAdress());

		VagrantSSHConnection connection = vm.createConnection();
		System.out.println(connection.isReady());
		connection.execute("pwd", false);
	}

}
