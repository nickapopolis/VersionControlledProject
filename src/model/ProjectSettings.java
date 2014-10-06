package model;


import java.io.File;
import java.util.Iterator;

import main.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import view.SettingsChangeWindow;


public abstract class ProjectSettings {
	
	JSONObject settings;
	String settingsRelativePath = "settings.json";
	
	
	public abstract void onSettingsInitialized();

	public ProjectSettings() throws Exception{
		settings = getSettings(settingsRelativePath);
		if(!hasRequiredFields(settings)){
			createSettings(settings);
		}
		
	}

	private JSONObject getSettings(String settingsPath) throws JSONException {
		String settingsJSONString = FileUtils.readFileIntoString(settingsRelativePath, true);
		JSONObject settings = new JSONObject(settingsJSONString);
		return settings;
	}

	private void createSettings(JSONObject settings) throws Exception {
		settings = new SettingsChangeWindow(settings).getSettings();
		onSettingsInitialized();
	}
	protected void saveSettings() throws JSONException{
		FileUtils.writeStringToFile(new File( "settings.json"),settings.toString(4));
	}
	private boolean hasRequiredFields(JSONObject settings) throws JSONException{
		Iterator<?> keys = settings.keys();
		while( keys.hasNext() ){
            String key = (String)keys.next();
            if(settings.getJSONObject(key).getString("required").equals("true") && settings.getJSONObject(key).getString("value").equals("")){
            	return false;
            }
		}
		return true;
	}
	public String get(String fieldName){
		String value = null;
		try {
			value = settings.getJSONObject(fieldName).getString("value");
		} catch (JSONException e) {
			System.err.println("Error getting field " + fieldName);
			e.printStackTrace();
		}
		return value;
		
	}
}