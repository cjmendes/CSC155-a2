package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;

@SuppressWarnings("serial")
public class PanRight extends AbstractAction{

	private static PanRight instance = new PanRight();
	private Camera camera;
	
	public PanRight () {
		super("PanRight");
	}
	
	public static PanRight getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.panRight();
	}

}