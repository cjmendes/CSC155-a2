package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class DisplayAxis extends AbstractAction{
	
	private Starter s;
	
	public DisplayAxis (Starter s) {
		super("Axis");
		this.s = s;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		s.displayAxis();
	}
}
