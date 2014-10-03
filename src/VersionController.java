import org.json.JSONException;
import org.json.JSONObject;

public class VersionController {

	JSONObject settings;
	String settingsRelativePath = "/settings.json";
	
	public VersionController() {

		try {
			settings = getSettings(settingsRelativePath);
		} catch (JSONException e) {
			createSettings();
		}

	}

	private JSONObject getSettings(String settingsPath) throws JSONException {
		String settingsJSONString = FileUtils.readFileIntoString(settingsRelativePath);
		JSONObject settings = new JSONObject(settingsJSONString);
		return settings;
	}

	@SuppressWarnings("serial")
	private void createSettings() {
		new SettingsChangeWindow() {
			@Override
			public void onSettingsChanged(JSONObject newSettings) {
				settings = newSettings;
				try {
					System.out.println(settings.get("GitProjectDirectory"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	public static void main(String args[]){
		new VersionController();
	}
}
