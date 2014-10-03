import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class SettingsChangeWindow extends JFrame{
	private static final long serialVersionUID = 2595767056547702380L;
	
	public abstract void onSettingsChanged(JSONObject newSettings);
	
	JPanel panel;
	JTextField fieldGitProjectDirectory;
	JButton buttonSave;
	JFrame frame = this;
	public SettingsChangeWindow(){
		
		addControls();
		pack();
		setVisible(true);
		
	}
	private void addControls(){
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);
		
		JLabel labelGitProjectDirectory = new JLabel("Git Project Directory");
		c.gridx = 0;
		c.weightx = 0.5;
		panel.add(labelGitProjectDirectory, c);
		
		fieldGitProjectDirectory = new FileChooserField();
		c.gridx = 1;
		c.weightx = 0.5;
		c.ipadx = 200;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(fieldGitProjectDirectory, c);
		
		buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//return the settings
					JSONObject settings = getSettings();
					onSettingsChanged(settings);
					frame.setVisible(false);
					frame.dispose();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(buttonSave, c);
		
		add(panel);
	}
	public JSONObject getSettings() throws JSONException{
		JSONObject settings = new JSONObject();
		settings.put("GitProjectDirectory", fieldGitProjectDirectory.getText());
		return settings;
	}
}
