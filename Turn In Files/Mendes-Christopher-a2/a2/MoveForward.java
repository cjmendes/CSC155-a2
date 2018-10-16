package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveForward extends AbstractAction {
	
	private static MoveForward instance = new MoveForward();
	private Camera camera;
	
	public MoveForward () {
		super("Forward");
	}
	
	public static MoveForward getInstance() {
		return instance;
	}
	
	public void setCamera(Camera camera2) {
		this.camera = camera2;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveForward();
	}
}