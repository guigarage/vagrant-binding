package com.guigarage.vagrant.configuration.builder;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;

import com.guigarage.vagrant.configuration.ChefSoloProvisionerConfig;

public class ChefSoloTests {

	@Test
	public void emptyConfiguration() {
		ChefSoloProvisionerConfig config = ChefSoloProvisionerConfigBuilder.create().build();
		Assert.assertEquals(null, config.getDataBagsPath());
		Assert.assertEquals(null, config.getProvisioningPath());
		Assert.assertEquals(null, config.getRolesPath());
		Assert.assertEquals(null, config.getJsonConfiguration());
		Assert.assertEquals(null, config.getRecipeUrl());
		
		Assert.assertEquals(false, config.getCookbookPathes().iterator().hasNext());
		Assert.assertEquals(false, config.getCookbookPathesOnVm().iterator().hasNext());
		Assert.assertEquals(false, config.getRecipes().iterator().hasNext());
		Assert.assertEquals(false, config.getRoles().iterator().hasNext());
	}
	
	@Test
	public void configurationWithDataBags() {
		String path = "path/to/data";
		ChefSoloProvisionerConfig config = ChefSoloProvisionerConfigBuilder.create().withDataBagsPath("path/to/data").build();
		Assert.assertEquals(path, config.getDataBagsPath());
		Assert.assertEquals(null, config.getProvisioningPath());
		Assert.assertEquals(null, config.getRolesPath());
		Assert.assertEquals(null, config.getJsonConfiguration());
		Assert.assertEquals(null, config.getRecipeUrl());
		
		Assert.assertEquals(false, config.getCookbookPathes().iterator().hasNext());
		Assert.assertEquals(false, config.getCookbookPathesOnVm().iterator().hasNext());
		Assert.assertEquals(false, config.getRecipes().iterator().hasNext());
		Assert.assertEquals(false, config.getRoles().iterator().hasNext());
	}
	
	@Test
	public void configurationWithProvisioningPath() {
		String path = "path/to/data";
		ChefSoloProvisionerConfig config = ChefSoloProvisionerConfigBuilder.create().withProvisioningPath("path/to/data").build();
		Assert.assertEquals(null, config.getDataBagsPath());
		Assert.assertEquals(path, config.getProvisioningPath());
		Assert.assertEquals(null, config.getRolesPath());
		Assert.assertEquals(null, config.getJsonConfiguration());
		Assert.assertEquals(null, config.getRecipeUrl());
		
		Assert.assertEquals(false, config.getCookbookPathes().iterator().hasNext());
		Assert.assertEquals(false, config.getCookbookPathesOnVm().iterator().hasNext());
		Assert.assertEquals(false, config.getRecipes().iterator().hasNext());
		Assert.assertEquals(false, config.getRoles().iterator().hasNext());
	}
	
	@Test
	public void configurationWithRolesPath() {
		String path = "path/to/data";
		ChefSoloProvisionerConfig config = ChefSoloProvisionerConfigBuilder.create().withRolesPath("path/to/data").build();
		Assert.assertEquals(null, config.getDataBagsPath());
		Assert.assertEquals(null, config.getProvisioningPath());
		Assert.assertEquals(path, config.getRolesPath());
		Assert.assertEquals(null, config.getJsonConfiguration());
		Assert.assertEquals(null, config.getRecipeUrl());
		
		Assert.assertEquals(false, config.getCookbookPathes().iterator().hasNext());
		Assert.assertEquals(false, config.getCookbookPathesOnVm().iterator().hasNext());
		Assert.assertEquals(false, config.getRecipes().iterator().hasNext());
		Assert.assertEquals(false, config.getRoles().iterator().hasNext());
	}
	
	@Test
	public void configurationWithRecipeUrl() throws MalformedURLException {
		String url = "http://files.vagrantup.com/getting_started/cookbooks.tar.gz";
		ChefSoloProvisionerConfig config = ChefSoloProvisionerConfigBuilder.create().withRecipeUrl(url).build();
		Assert.assertEquals(null, config.getDataBagsPath());
		Assert.assertEquals(null, config.getProvisioningPath());
		Assert.assertEquals(null, config.getRolesPath());
		Assert.assertEquals(null, config.getJsonConfiguration());
		Assert.assertEquals(new URL(url), config.getRecipeUrl());
		Assert.assertEquals(url, config.getRecipeUrl().toString());
		
		Assert.assertEquals(false, config.getCookbookPathes().iterator().hasNext());
		Assert.assertEquals(false, config.getCookbookPathesOnVm().iterator().hasNext());
		Assert.assertEquals(false, config.getRecipes().iterator().hasNext());
		Assert.assertEquals(false, config.getRoles().iterator().hasNext());
		
		config = ChefSoloProvisionerConfigBuilder.create().withRecipeUrl(new URL(url)).build();
		Assert.assertEquals(null, config.getDataBagsPath());
		Assert.assertEquals(null, config.getProvisioningPath());
		Assert.assertEquals(null, config.getRolesPath());
		Assert.assertEquals(null, config.getJsonConfiguration());
		Assert.assertEquals(new URL(url), config.getRecipeUrl());
		Assert.assertEquals(url, config.getRecipeUrl().toString());
		
		Assert.assertEquals(false, config.getCookbookPathes().iterator().hasNext());
		Assert.assertEquals(false, config.getCookbookPathesOnVm().iterator().hasNext());
		Assert.assertEquals(false, config.getRecipes().iterator().hasNext());
		Assert.assertEquals(false, config.getRoles().iterator().hasNext());
	}
}
