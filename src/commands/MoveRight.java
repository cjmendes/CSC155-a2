package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;

@SuppressWarnings("serial")
public class MoveRight extends AbstractAction{

	private static MoveRight instance = new MoveRight();
	private Camera camera;
	
	public MoveRight () {
		super("Right");
	}
	
	public static MoveRight getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveRight();
	}
}