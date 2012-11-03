Vagrant-Binding
===============

This API is build on top of the OpenSource Tool <a title="Vagrant" href="http://vagrantup.com"><strong>Vagrant</strong></a>. Vagrant is a ruby based tool for virtual machine automation. With Vagrant you can set up complex virtual systems in only a few minutes. Every vm that is created with Vagrant based on a config-file that describes the configuration of the virtual machine and all installed software on it. You can configure a Ubuntu 64 bit vm with a running MySql server for example. With Vagrant-Binding you can put the automation on the next level.
Vagrant-Binding is OpenSource. It offers some nice builder APIs so that you can programmatically set up your vm. In addition to this Vagrant-Bindings gives you the opportunity to sync the lifecycle of your virtual machines with your JUnit Unit Tests. All you need is a <a title="VirtualBox" href="https://www.virtualbox.org">VirtualBox</a> installation on your system.
<h2>Getting started</h2>
I'm working on some samples for the use with Vagrant-Binding. You can find the project [here](https://github.com/guigarage/vagrant-binding-demos).

The simplest way to start building your virtual machines in Java is the Builder-API. With a few Builder-Classes you can set up a complete environment of different virtual machines at runtime. To run all the example you need a VirtualBox installation on your system. I suggest you to use version 4.1.16 because this is the version I am currently using. I Don't know if every version is working fine with Vagrant. The actual "Vagrant-Binding" snapshot bundles Vagrant version 1.0.5. So you don't need a additional Vagrant installation on your system.

Here is a short example that creates an Ubuntu 32bit vm:

    VagrantVmConfig vmConfig = VagrantVmConfigBuilder
        .create()
        .withLucid32Box()
        .withName("myVM")
        .build();

    VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfigBuilder
        .create()
        .withVagrantVmConfig(vmConfig)
        .build();

    Vagrant vagrant = new Vagrant(true);

    VagrantEnvironment environment = vagrant
        .createEnvironment(new File("my/locale/path"), environmentConfig);

    environment.up();

You can configure your vm by using a static ip and some port forwarding for example:

    VagrantPortForwarding portForwarding = new VagrantPortForwarding("custom", 7777, 1399);
    
    VagrantVmConfig vmConfig = VagrantVmConfigBuilder
        .create()
        .withLucid32Box()
        .withName("myVM").
        .withHostOnlyIp("192.168.50.4")
        .withVagrantPortForwarding(portForwarding).build();

The code creates a VmConfig with some special features. This VmConfig is put into a VagrantEnvironment. One Environment can capsulate as many virtual machines as you want. You can start the whole environment by calling "environment.up()". This creates and starts up every virtual machine that is defined in the environment within a fe minutes. If you want you can access every machine and start or stop it manually:

    for(VagrantVm vm : environment.getAllVms()) {
        vm.destroy();
    }

Each vm has a lifecycle. You can change the state of the lifecycle easily:

    vm.start();
    vm.suspend();
    vm.resume();
    vm.halt();
    vm.destroy();


To configure the software, which will be installed on the virtual machine you need a Puppet configuration script. <a title="Puppet" href="http://puppetlabs.com">Puppet</a> is a tool that automates the installation and administration of software. Each virtual machine that is created by Vagrant runs Puppet by default. So you only need a configuration script. Here is a simple example that edits the welcome message of a virtual machine:

    group { "puppet":
        ensure =&gt; "present",
    }

    File { owner =&gt; 0, group =&gt; 0, mode =&gt; 0644 }
    
    file { '/etc/motd':
        content =&gt; "Welcome to your Vagrant-built virtual machine!
        Managed by Puppet.\n"
    }

You can easily use your puppet scripts with Vagrant-Binding:

    PuppetProvisionerConfig puppetConfig = PuppetProvisionerConfigBuilder
        .create()
        .withManifestPath("path/to/puppetscript")
        .withManifestFile("config.pp")
        .build();

    VagrantVmConfig vmConfig = VagrantVmConfigBuilder
        .create()
        .withLucid32Box()
        .withPuppetProvisionerConfig(puppetConfig)
        .build();

After starting your virtual machine you can use SHH to connect on the machine. "Vagrant-Binding" provides a class for file upload and process execution over ssh. You can start your jobs on the virtual machine by simple using a code like this:

    VagrantSSHConnection connection = vm.createConnection();
    connection.execute("touch /path/to/any/file", true);

<h2>Using Vagrant-Binding for real sandbox testing</h2>
Vagrant-Binding offers a special <a title="JUnit Rule" href="http://www.junit.org/node/580">@Rule for JUnit</a>. By using this Rule you can capsule each of your tests with a fully vm lifecycle. Let us assume we have the following Unit Test:

    @Test
    public void testJdbc() {
        System.out.println("Test starts");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager
                .getConnection("jdbc:mysql://192.168.50.4/testapp?"
                + "user=dbuser&amp;password=dbuser");

            Statement statement = connection.createStatement();
            String table = "CREATE TABLE mytable (data_entry VARCHAR(254))";
            statement.executeUpdate(table);
            statement.close();
    
            for(int i=0; i &lt; 100; i++) {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO mytable VALUES(\"" + UUID.randomUUID().toString() + "\")");
                statement.close();
            }
            
            statement = connection.createStatement();
            ResultSet resultSet = statement
                .executeQuery("SELECT COUNT(*) FROM mytable");
            resultSet.next();
            Assert.assertEquals(100, resultSet.getInt(1));
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

The test creates the table "mytable", adds 100 rows into it and checks the rowcount. To run this test successfully you need a machine with the hard ip "192.168.50.4" and a MySQL Server. You need a database "testapp" on the server too. On this database there must not be a table called "mytable". So you can only run this Unit Test one time because it doesn't drop the table "mytable" at the end. You can expand the test and drop the table at the end but what will happen in case of an error? Every time the test starts automatically you do not know the state of the server, database and table. Another problem is that you can't run the test parallel.
With Vagrant-Binding you can create your database server as a vm on the fly. All you need to do is adding a <code>VagrantTestRule</code> to your test class:

    @Rule
    public VagrantTestRule testRule = new VagrantTestRule(createConfig());

The Rule needs a configuration that is easily created:

    public static VagrantConfiguration createConfig() {
        PuppetProvisionerConfig puppetConfig = PuppetProvisionerConfigBuilder
            .create()
            .withManifestPath("/path/to/manifests")
            .withManifestFile("dbserver.pp")
            .withDebug(true)
            .build();

        VagrantVmConfig vmConfig = VagrantVmConfigBuilder
            .create()
            .withName("mysqlvm")
            .withHostOnlyIp("192.168.50.4")
            .withBoxName("lucid32")
            .withPuppetProvisionerConfig(puppetConfig)
            .build();
        
        VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfigBuilder
            .create()
            .withVagrantVmConfig(vmConfig)
            .build();
        
        VagrantFileTemplateConfiguration fileTemplateConfiguration = VagrantFileTemplateConfigurationBuilder
            .create()
            .withLocalFile(new File("/path/to/my.cnf"))
            .withPathInVagrantFolder("files/my.cnf")
            .build();
        
        VagrantConfiguration configuration = VagrantConfigurationBuilder
            .create()
            .withVagrantEnvironmentConfig(environmentConfig)
            .withVagrantFileTemplateConfiguration(fileTemplateConfiguration)
            .build();

        return configuration;
    }


The "VagrantTestRule" syncs every single test with the livecycle of the Vagrant environment. Each vm that is defined in the environments starts before the UnitTests runs and stops after the test:

<a href="http://www.guigarage.com/wordpress/wp-content/uploads/2012/11/unitTest.png"><img class="aligncenter size-full wp-image-333" title="unitTest" src="http://www.guigarage.com/wordpress/wp-content/uploads/2012/11/unitTest.png" alt="" width="721" height="308" /></a>

You can access the environment and execute commands on the virtual machines by SSH for example while the test is running by calling "testRule.getEnvironment()".

I'm using a Puppet script that I found on <a title="vagrant-puppet-demo" href="https://github.com/moolsan/vagrant-puppet-demo">github</a> to configure the database server vm with Puppet.

I will host a project with some demos and tutorials within the next days on github. You need to download and install the Vagrant-Binding project as maven dependency because it is not available at Maven Central Repository at the moment.
<h2>Current state of the project and some limitations</h2>
The API is in a very early state. I haven't even tried everything until today and there are only a few Unit Tests. There is currently no existing javadoc and only some simple examples. So please do not use this API in production - at the moment :)
There is a <a title="jruby bug" href="https://github.com/jarib/childprocess/issues/26">jruby bug</a> on windows 64 bit so that Vagrant-Binding is actually not working there.
<h2>Further reading</h2>
Vagrant has a very good <a title="Vagrant" href="http://vagrantup.com/v1/docs/getting-started/index.html">quickstart</a> and <a title="Vagrant" href="http://vagrantup.com/v1/docs/index.html">documentation</a>. All of the features I implemented until today are strongly adopted from Vagrant. There are are lot of <a href="http://www.javacodegeeks.com/2012/08/introduction-to-puppet-for-vagrant-users.html">good</a> <a title="example" href="https://github.com/moolsan/vagrant-puppet-demo">Puppet</a> <a title="example" href="http://www.tomcatexpert.com/blog/2010/04/29/deploying-tomcat-applications-puppet">examples</a> <a href="http://www.javacodegeeks.com/2012/06/serving-files-with-puppet-standalone-in.html">out</a> <a href="http://blog.codecentric.de/en/2012/02/automated-virtual-test-environments-with-vagrant-and-puppet/">there</a>. I read <a title="Amazon" href="http://www.amazon.de/Pulling-Strings-With-Puppet-Configuration/dp/1590599780/ref=sr_1_7?ie=UTF8&amp;qid=1351633216&amp;sr=8-7">"Pulling Strings With Puppet"</a> that helped me a lot.
I will write some tutorial and post about Vagrant-Binding at my [blog](http://www.guigarage.com) too.