package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;

@SuppressWarnings("serial")
public class PitchDown extends AbstractAction{

	private static PitchDown instance = new PitchDown();
	private Camera camera;
	
	public PitchDown () {
		super("PitchDown");
	}
	
	public static PitchDown getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.pitchDown();
	}

}