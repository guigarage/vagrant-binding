---
page_title: "Puppet Agent - Provisioning"
sidebar_current: "provisioning-puppetagent"
---

# Puppet Agent Provisioner

**Provisioner name: `puppet_server`**

The Puppet agent provisioner allows you to provision the guest using
[Puppet](http://www.puppetlabs.com/puppet), specifically by
calling `puppet agent`, connecting to a Puppet master, and retrieving
the set of modules and manifests from there.

<div class="alert alert-warn">
	<p>
		<strong>Warning:</strong> If you're not familiar with Puppet and Vagrant already,
		I recommend starting with the <a href="/v2/provisioning/shell.html">shell
		provisioner</a>. However, if you're comfortable with Vagrant already, Vagrant
		is the best way to learn Puppet.
	</p>
</div>

## Specifying the Puppet Master

The quickest way to get started with the Puppet agent provisioner is to just
specify the location of the Puppet master:

```ruby
Vagrant.configure("2") do |config|
  config.vm.provision "puppet_server" do |puppet|
    puppet.puppet_server = "puppet.example.com"
  end
end
```

By default, Vagrant will look for the host named "puppet" on the
local domain of the guest machine.

## Configuring the Node Name

The node name that the agent registers as can be customized. Remember
this is important because Puppet uses the node name as part of the process
to compile the catalog the node will run.

The node name defaults to the hostname of the guest machine, but can
be customized using the Vagrantfile:

```ruby
Vagrant.configure("2") do |config|
  config.vm.provision "puppet_server" do |puppet|
    puppet.puppet_node = "node.example.com"
  end
end
```

## Additional Options

Puppet supports a lot of command-line flags. Basically any setting can
be overriden on the command line. To give you the most power and flexibility
possible with Puppet, Vagrant allows you to specify custom command line
flags to use:

```ruby
Vagrant.configure("2") do |config|
  config.vm.provision "puppet_server" do |puppet|
    puppet.options = "--verbose --debug"
  end
end
```
