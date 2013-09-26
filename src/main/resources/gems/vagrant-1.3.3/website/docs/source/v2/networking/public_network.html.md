---
page_title: "Public Networks - Networking"
sidebar_current: "networking-public"
---

# Public Networks

**Network identifier: `public_network`**

Public networks are less private than private networks, and the exact
meaning actually varies from [provider to provider](/v2/providers/index.html),
hence the ambiguous definition. The idea is that while
[private networks](/v2/networking/private_network.html) should never allow the
general public access to your machine, public networks can.

<div class="alert alert-info">
	<p>
		<strong>Confused?</strong> We kind of are, too. It is likely that
		public networks will be replaced by <code>:bridged</code> in a
		future release, since that is in general what should be done with
		public networks, and providers that don't support bridging generally
		don't have any other features that map to public networks either.
	</p>
</div>

## DHCP

The easiest way to use a public network is to allow the IP to be assigned
via DHCP. In this case, defining a public network is trivially easy:

```ruby
Vagrant.configure("2") do |config|
  config.vm.network "public_network"
end
```

When DHCP is used, the IP can be determined by using `vagrant ssh` to
SSH into the machine and using the appropriate command line tool to find
the IP, such as `ifconfig`.

## Default Network Interface

If more than one network interface is available on the host machine, Vagrant will
ask you to choose which interface the virtual machine should bridge to. A default
interface can be specified by adding a `:bridge` clause to the network definition.

```ruby
config.vm.network "public_network", :bridge => 'en1: Wi-Fi (AirPort)'
```

The string identifying the desired interface must exactly match the name of an
available interface. If it can't be found, Vagrant will ask you to pick
from a list of available network interfaces.
