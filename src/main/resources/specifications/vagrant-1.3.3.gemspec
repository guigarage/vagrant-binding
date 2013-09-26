# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "vagrant"
  s.version = "1.3.3"

  s.required_rubygems_version = Gem::Requirement.new(">= 1.3.6") if s.respond_to? :required_rubygems_version=
  s.authors = ["Mitchell Hashimoto", "John Bender"]
  s.date = "2013-09-18"
  s.description = "Vagrant is a tool for building and distributing virtualized development environments."
  s.email = ["mitchell.hashimoto@gmail.com", "john.m.bender@gmail.com"]
  s.executables = ["vagrant"]
  s.files = ["bin/vagrant"]
  s.homepage = "http://vagrantup.com"
  s.require_paths = ["lib"]
  s.rubyforge_project = "vagrant"
  s.rubygems_version = "1.8.23"
  s.summary = "Build and distribute virtualized development environments."

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<childprocess>, ["~> 0.3.7"])
      s.add_runtime_dependency(%q<erubis>, ["~> 2.7.0"])
      s.add_runtime_dependency(%q<i18n>, ["~> 0.6.0"])
      s.add_runtime_dependency(%q<log4r>, ["~> 1.1.9"])
      s.add_runtime_dependency(%q<net-ssh>, ["~> 2.6.6"])
      s.add_runtime_dependency(%q<net-scp>, ["~> 1.1.0"])
      s.add_development_dependency(%q<rake>, [">= 0"])
      s.add_development_dependency(%q<contest>, [">= 0.1.2"])
      s.add_development_dependency(%q<minitest>, ["~> 2.5.1"])
      s.add_development_dependency(%q<mocha>, [">= 0"])
      s.add_development_dependency(%q<rspec-core>, ["~> 2.11.0"])
      s.add_development_dependency(%q<rspec-expectations>, ["~> 2.11.0"])
      s.add_development_dependency(%q<rspec-mocks>, ["~> 2.11.0"])
    else
      s.add_dependency(%q<childprocess>, ["~> 0.3.7"])
      s.add_dependency(%q<erubis>, ["~> 2.7.0"])
      s.add_dependency(%q<i18n>, ["~> 0.6.0"])
      s.add_dependency(%q<log4r>, ["~> 1.1.9"])
      s.add_dependency(%q<net-ssh>, ["~> 2.6.6"])
      s.add_dependency(%q<net-scp>, ["~> 1.1.0"])
      s.add_dependency(%q<rake>, [">= 0"])
      s.add_dependency(%q<contest>, [">= 0.1.2"])
      s.add_dependency(%q<minitest>, ["~> 2.5.1"])
      s.add_dependency(%q<mocha>, [">= 0"])
      s.add_dependency(%q<rspec-core>, ["~> 2.11.0"])
      s.add_dependency(%q<rspec-expectations>, ["~> 2.11.0"])
      s.add_dependency(%q<rspec-mocks>, ["~> 2.11.0"])
    end
  else
    s.add_dependency(%q<childprocess>, ["~> 0.3.7"])
    s.add_dependency(%q<erubis>, ["~> 2.7.0"])
    s.add_dependency(%q<i18n>, ["~> 0.6.0"])
    s.add_dependency(%q<log4r>, ["~> 1.1.9"])
    s.add_dependency(%q<net-ssh>, ["~> 2.6.6"])
    s.add_dependency(%q<net-scp>, ["~> 1.1.0"])
    s.add_dependency(%q<rake>, [">= 0"])
    s.add_dependency(%q<contest>, [">= 0.1.2"])
    s.add_dependency(%q<minitest>, ["~> 2.5.1"])
    s.add_dependency(%q<mocha>, [">= 0"])
    s.add_dependency(%q<rspec-core>, ["~> 2.11.0"])
    s.add_dependency(%q<rspec-expectations>, ["~> 2.11.0"])
    s.add_dependency(%q<rspec-mocks>, ["~> 2.11.0"])
  end
end
