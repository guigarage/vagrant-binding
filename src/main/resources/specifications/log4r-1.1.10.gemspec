# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "log4r"
  s.version = "1.1.10"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Colby Gutierrez-Kraybill"]
  s.date = "2012-01-02"
  s.description = "See also: http://logging.apache.org/log4j"
  s.email = "colby@astro.berkeley.edu"
  s.homepage = "http://log4r.rubyforge.org"
  s.require_paths = ["lib"]
  s.rubygems_version = "1.8.23"
  s.summary = "Log4r, logging framework for ruby"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_development_dependency(%q<bundler>, [">= 1.0.0"])
      s.add_development_dependency(%q<rake>, ["~> 0.8.7"])
    else
      s.add_dependency(%q<bundler>, [">= 1.0.0"])
      s.add_dependency(%q<rake>, ["~> 0.8.7"])
    end
  else
    s.add_dependency(%q<bundler>, [">= 1.0.0"])
    s.add_dependency(%q<rake>, ["~> 0.8.7"])
  end
end
