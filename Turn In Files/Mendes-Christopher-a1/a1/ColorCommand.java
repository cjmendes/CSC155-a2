package a1;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class ColorCommand extends AbstractAction{
	
	private static Starter st;
	
	public ColorCommand(Starter st) {
		ColorCommand.st = st;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		st.switchColorPub();
	}
	
}