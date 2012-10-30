package com.guigarage.vagrant.model;

import java.net.URL;
import java.util.ArrayList;

import org.jruby.RubyArray;
import org.jruby.RubyBoolean;
import org.jruby.RubyNil;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.util.VagrantException;

public class VagrantEnvironment {

	private RubyObject vagrantEnvironment;

	public VagrantEnvironment(RubyObject vagrantEnvironment) {
		this.vagrantEnvironment = vagrantEnvironment;
	}

	public void up() {
		try {
			vagrantEnvironment
					.callMethod(
							"cli",
							RubyString.newString(
									vagrantEnvironment.getRuntime(), "up"));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void addBox(String boxName, URL boxUrl) {
		try {
			vagrantEnvironment.callMethod("cli", RubyString.newString(
					vagrantEnvironment.getRuntime(), "add"), RubyString
					.newString(vagrantEnvironment.getRuntime(), boxName),
					RubyString.newString(vagrantEnvironment.getRuntime(),
							boxUrl.toString()));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void removeBox(String boxName) {
		try {
			RubyArray boxes = (RubyArray) ((RubyObject) vagrantEnvironment
					.callMethod("boxes")).getInternalVariable("@boxes");
			for (Object box : boxes) {
				String name = ((RubyObject) box).callMethod("name").toString();
				if(name.equals(boxName)) {
					((RubyObject) box).callMethod("destroy");
				}
			}
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}
	
	public String getBoxesPath() {
		try {
			return ((RubyObject) vagrantEnvironment.callMethod("boxes_path"))
					.toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}
	
	public void init(String boxName) {
		try {
			vagrantEnvironment.callMethod("cli", RubyString.newString(
					vagrantEnvironment.getRuntime(), "init"), RubyString
					.newString(vagrantEnvironment.getRuntime(), boxName));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public boolean isMultiVmEnvironment() {
		try {
			return ((RubyBoolean) vagrantEnvironment.callMethod("multivm?"))
					.isTrue();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public String getRootPath() {
		try {
			return ((RubyObject) vagrantEnvironment.callMethod("root_path"))
					.toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public Iterable<String> getAllAvailableBoxes() {
		try {
			RubyArray boxes = (RubyArray) ((RubyObject) vagrantEnvironment
					.callMethod("boxes")).getInternalVariable("@boxes");
			ArrayList<String> ret = new ArrayList<>();
			for (Object box : boxes) {
				ret.add(((RubyObject) box).callMethod("name").toString());
			}
			return ret;
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public Iterable<VagrantVm> getAllVms() {
		try {
			RubyArray o = (RubyArray) vagrantEnvironment
					.callMethod("vms_ordered");
			ArrayList<VagrantVm> vms = new ArrayList<>();
			for (Object vm : o) {
				vms.add(new VagrantVm((RubyObject) vm));
			}
			return vms;
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public String getVagrantfileName() {
		try {
			return ((RubyObject) vagrantEnvironment
					.callMethod("vagrantfile_name")).toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public String getHomePath() {
		try {
			return ((RubyObject) vagrantEnvironment.callMethod("home_path"))
					.toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public VagrantVm getPrimaryVm() {
		try {
			RubyObject rubyVm = (RubyObject) vagrantEnvironment.callMethod("primary_vm");
			if(rubyVm == null || rubyVm instanceof RubyNil) {
				throw new VagrantException("No primary vm found. Maybe there is no vm defined in your configuration or you are working with a multi vm environment.");
			}
			return new VagrantVm(rubyVm);
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public void destroy() {
		for (VagrantVm vm : getAllVms()) {
			if (vm.isCreated()) {
				vm.destroy();
			}
		}
	}
}
