package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.AuthenticationException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;


public class SettingsChangeWindow extends JPanel{
	private static final long serialVersionUID = 2595767056547702380L;
	
	HashMap<String, JTextField> textFields;
	JButton buttonSave;
	JSONObject settingsFields;
	
	public SettingsChangeWindow(JSONObject settingsFields) throws JSONException{
		textFields= new HashMap<String, JTextField>();
		this.settingsFields = settingsFields;
		addControls();
		
	}
	private void addControls() throws JSONException{
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		c.insets = new Insets(3,3,3,3);
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		
		Iterator<?> keys = settingsFields.keys();
		
		//create fields from list in settings
		while( keys.hasNext() ){
            String key = (String)keys.next();
            JSONObject fieldJSONObj =  settingsFields.getJSONObject(key);
        
            JLabel label = new JLabel(fieldJSONObj.getString("label"));
            c.ipadx = 0;
            c.gridx = 0;
			add(label, c);
			
			JTextField field;
			if(fieldJSONObj.getString("type").equals("filePath")){
				field = new FileChooserField();
			}else{
				field = new JTextField();
			}
			field.setText( fieldJSONObj.getString("value"));
			
			c.ipadx = 400;
			c.gridx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(field, c);
			textFields.put(key, field);
			c.gridy++;
		}
	}
	public JSONObject getSettings() throws Exception{
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, this, "Installation Path Required",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[1]);
		if (option == 0) // pressing OK button
		{
			Iterator<?> keys = settingsFields.keys();
			while( keys.hasNext() ){
	            String key = (String)keys.next();
	            JSONObject fieldJSONObj = settingsFields.getJSONObject(key);
	            String fieldText = textFields.get(key).getText();
	            fieldJSONObj.put("value", fieldText);
			}
		}
		else{
			throw new Exception("Installation Cancelled");
		}
		
		
		return settingsFields;
	}
}
