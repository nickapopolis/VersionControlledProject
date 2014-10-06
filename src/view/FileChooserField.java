package view;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;


public class FileChooserField extends JTextField implements MouseListener{
	
	public FileChooserField(){
		super();
		this.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent evt) {
		if (evt.getSource() == this) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.setText(chooser.getSelectedFile()
						.getAbsolutePath());
			}
		}

	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
