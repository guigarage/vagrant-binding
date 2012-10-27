# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "ffi"
  s.version = "1.1.5"
  s.platform = "java"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["JRuby Project"]
  s.date = "2012-08-09"
  s.description = ""
  s.email = "ruby-ffi@groups.google.com"
  s.extra_rdoc_files = ["README.txt", "History.txt"]
  s.files = ["README.txt", "History.txt"]
  s.homepage = "http://wiki.github.com/ffi/ffi"
  s.rdoc_options = ["--main", "README.txt"]
  s.require_paths = ["lib"]
  s.rubyforge_project = "ffi"
  s.rubygems_version = "1.8.24"
  s.summary = "A Ruby foreign function interface"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
    else
    end
  else
  end
end
