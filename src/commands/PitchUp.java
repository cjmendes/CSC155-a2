package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;

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