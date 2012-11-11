package com.guigarage.vagrant.configuration.builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.guigarage.vagrant.configuration.ChefSoloProvisionerConfig;

public class ChefSoloProvisionerConfigBuilder {
	
	private List<String> cookbookPathes;

	private List<String> cookbookPathesOnVm;

	private List<String> recipes;

	private String rolesPath;

	private List<String> roles;

	private URL recipeUrl;

	private String provisioningPath;

	private String dataBagsPath;

	private JSONObject jsonConfiguration;
	
	public ChefSoloProvisionerConfigBuilder() {
		cookbookPathes = new ArrayList<>();
		cookbookPathesOnVm = new ArrayList<>();
		recipes = new ArrayList<>();
		roles = new ArrayList<>();
	}
	
	public static ChefSoloProvisionerConfigBuilder create() {
		return new ChefSoloProvisionerConfigBuilder();
	}
	
	public ChefSoloProvisionerConfigBuilder withCookbookPath(String cookbookPath) {
		this.cookbookPathes.add(cookbookPath);
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withCookbookPathOnVm(String cookbookPathOnVm) {
		this.cookbookPathesOnVm.add(cookbookPathOnVm);
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withRecipe(String recipe) {
		this.recipes.add(recipe);
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withRolesPath(String rolesPath) {
		this.rolesPath = rolesPath;
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withRole(String role) {
		this.roles.add(role);
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withRecipeUrl(URL recipeUrl) {
		this.recipeUrl = recipeUrl;
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withRecipeUrl(String recipeUrl) throws MalformedURLException {
		this.recipeUrl = new URL(recipeUrl);
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withProvisioningPath(String provisioningPath) {
		this.provisioningPath = provisioningPath;
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withDataBagsPath(String dataBagsPath) {
		this.dataBagsPath = dataBagsPath;
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withJsonConfiguration(JSONObject jsonConfiguration) {
		this.jsonConfiguration = jsonConfiguration;
		return this;
	}
	
	public ChefSoloProvisionerConfigBuilder withJsonConfiguration(String jsonConfiguration) throws JSONException {
		this.jsonConfiguration = new JSONObject(jsonConfiguration);
		return this;
	}

	public ChefSoloProvisionerConfig build() {
		return new ChefSoloProvisionerConfig(cookbookPathes, cookbookPathesOnVm, recipes, rolesPath, roles, recipeUrl, provisioningPath, dataBagsPath, jsonConfiguration);
	}
}
