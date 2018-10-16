package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PitchUp extends AbstractAction{

	private static PitchUp instance = new PitchUp();
	private Camera camera;
	
	public PitchUp () {
		super("PitchUp");
	}
	
	public static PitchUp getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.pitchUp();
	}

}