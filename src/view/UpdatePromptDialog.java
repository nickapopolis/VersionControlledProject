package view;

import javax.naming.AuthenticationException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class UpdatePromptDialog extends JPanel{
	
	private static final long serialVersionUID = 8554517235402906363L;
	

	public UpdatePromptDialog(){
		this.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		JLabel label = new JLabel("A software update has been found, download this update?");
		add(label);
	}
	public boolean willUpdate() throws AuthenticationException{
		UsernamePasswordCredentialsProvider credentials = null;
		
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, this, "Software Update Found",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[1]);
		
		//clicked yes
		return option == 0;
	}
}