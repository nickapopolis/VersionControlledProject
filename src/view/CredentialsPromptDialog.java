package view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.naming.AuthenticationException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class CredentialsPromptDialog extends JPanel{
	
	private static final long serialVersionUID = 8554517235402906363L;
	
	JLabel labelUserName;
	JTextField fieldUserName;
	
	JLabel labelPassword;
	JPasswordField fieldPassword;
	
	public CredentialsPromptDialog(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		c.insets = new Insets(3,3,3,3);
		
		c.gridy = 0;
		c.gridx = 0;
		labelUserName = new JLabel("GitHub Username");
		add(labelUserName, c);
		
		c.gridx = 1;
		c.ipadx = 400;
		fieldUserName = new JTextField();
		add(fieldUserName, c);
		
		c.gridy = 1;
		c.gridx = 0;
		c.ipadx = 0;
		labelPassword = new JLabel("Password");
		add(labelPassword, c);
		
		c.gridx = 1;
		c.ipadx = 400;
		fieldPassword = new JPasswordField();
		add(fieldPassword, c);
	}
	public UsernamePasswordCredentialsProvider getCredentials() throws AuthenticationException{
		UsernamePasswordCredentialsProvider credentials = null;
		
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, this, "GitHub Credentials Required",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[1]);
		if (option == 0) // pressing OK button
		{
			credentials = new UsernamePasswordCredentialsProvider(fieldUserName.getText(), fieldPassword.getPassword());
			
		}else{
			throw new AuthenticationException("Authentication details are required for this operation.");
		}
		return credentials;
	}
}