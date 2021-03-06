package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;

@SuppressWarnings("serial")
public class PanLeft extends AbstractAction{

	private static PanLeft instance = new PanLeft();
	private Camera camera;
	
	public PanLeft () {
		super("PanLeft");
	}
	
	public static PanLeft getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.panLeft();
	}

}