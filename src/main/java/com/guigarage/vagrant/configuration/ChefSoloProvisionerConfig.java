package com.guigarage.vagrant.configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class ChefSoloProvisionerConfig {

	private List<String> cookbookPathes;

	private List<String> cookbookPathesOnVm;

	private List<String> recipes;

	private String rolesPath;

	private List<String> roles;

	private URL recipeUrl;

	private String provisioningPath;

	private String dataBagsPath;

	private JSONObject jsonConfiguration;

	public ChefSoloProvisionerConfig(Iterable<String> cookbookPathes,
			Iterable<String> cookbookPathesOnVm, Iterable<String> recipes,
			String rolesPath, Iterable<String> roles, URL recipeUrl,
			String provisioningPath, String dataBagsPath,
			JSONObject jsonConfiguration) {
		this.rolesPath = rolesPath;
		this.recipeUrl = recipeUrl;
		this.provisioningPath = provisioningPath;
		this.dataBagsPath = dataBagsPath;
		this.jsonConfiguration = jsonConfiguration;
		
		this.cookbookPathes = new ArrayList<>();
		for(String cookbookPath : cookbookPathes) {
			this.cookbookPathes.add(cookbookPath);
		}
		this.cookbookPathesOnVm = new ArrayList<>();
		for(String cookbookPathOnVm : cookbookPathesOnVm) {
			this.cookbookPathesOnVm.add(cookbookPathOnVm);
		}
		this.recipes = new ArrayList<>();
		for(String recipe : recipes) {
			this.recipes.add(recipe);
		}
		this.roles = new ArrayList<>();
		for(String role : roles) {
			this.roles.add(role);
		}
	}

	public Iterable<String> getCookbookPathes() {
		return cookbookPathes;
	}

	public Iterable<String> getCookbookPathesOnVm() {
		return cookbookPathesOnVm;
	}

	public Iterable<String> getRecipes() {
		return recipes;
	}

	public String getRolesPath() {
		return rolesPath;
	}

	public Iterable<String> getRoles() {
		return roles;
	}

	public URL getRecipeUrl() {
		return recipeUrl;
	}

	public String getProvisioningPath() {
		return provisioningPath;
	}

	public String getDataBagsPath() {
		return dataBagsPath;
	}

	public JSONObject getJsonConfiguration() {
		return jsonConfiguration;
	}

}
