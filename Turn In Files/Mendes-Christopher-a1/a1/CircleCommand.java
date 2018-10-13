package a1;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class CircleCommand extends AbstractAction{

	private static Starter st;
	
	public CircleCommand(Starter st) {
		CircleCommand.st = st;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		st.setCircleCheck();
		
	}
	
}
