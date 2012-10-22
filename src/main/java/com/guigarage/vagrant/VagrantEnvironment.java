package com.guigarage.vagrant;

import java.net.URL;
import java.util.ArrayList;

import org.jruby.RubyArray;
import org.jruby.RubyBoolean;
import org.jruby.RubyObject;
import org.jruby.RubyString;

public class VagrantEnvironment {

	private RubyObject vagrantEnvironment;
	
	public VagrantEnvironment(RubyObject vagrantEnvironment) {
		this.vagrantEnvironment = vagrantEnvironment;
	}
	
	public void up() {
		vagrantEnvironment.callMethod("cli", RubyString.newString(vagrantEnvironment.getRuntime(), "up"));
	}

	public void addBox(String boxName, URL boxUrl) {
		vagrantEnvironment.callMethod("cli", RubyString.newString(vagrantEnvironment.getRuntime(), "add"), RubyString.newString(vagrantEnvironment.getRuntime(), boxName), RubyString.newString(vagrantEnvironment.getRuntime(), boxUrl.toString()));
	}
	
	public void init(String boxName) {
		vagrantEnvironment.callMethod("cli", RubyString.newString(vagrantEnvironment.getRuntime(), "init"), RubyString.newString(vagrantEnvironment.getRuntime(), boxName));
	}
	
	public boolean isMultiVmEnvironment() {
		return ((RubyBoolean) vagrantEnvironment.callMethod("multivm?")).isTrue();
	}
	
	public String getRootPath() {
		return ((RubyObject) vagrantEnvironment.callMethod("root_path")).toString();
	}
	
	public Iterable<String> getAllAvailableBoxes() {
		RubyArray boxes = (RubyArray) ((RubyObject) vagrantEnvironment.callMethod("boxes")).getInternalVariable("@boxes");
		ArrayList<String> ret = new ArrayList<>();
		for(Object box : boxes) {
			ret.add(((RubyObject) box).callMethod("name").toString());
		}
		return ret;
	}
	
	public Iterable<VagrantVm> getAllVms() {
		RubyArray o = (RubyArray) vagrantEnvironment.callMethod("vms_ordered");
		ArrayList<VagrantVm> vms = new ArrayList<>();
		for(Object vm : o) {
			vms.add(new VagrantVm((RubyObject) vm));
		}
		return vms;
	}
	
	public String getVagrantfileName() {
		return ((RubyObject) vagrantEnvironment.callMethod("vagrantfile_name")).toString();
	}
	
	public String getHomePath() {
		return ((RubyObject) vagrantEnvironment.callMethod("home_path")).toString();
	}
	
	public VagrantVm getPrimaryVm() {
		return new VagrantVm((RubyObject) vagrantEnvironment.callMethod("primary_vm"));
	}

	public void destroy() {
		for(VagrantVm vm : getAllVms()) {
			if(vm.isCreated()) {
				vm.destroy();
			}
		}
	}
}
