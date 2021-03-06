package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveBackward extends AbstractAction{

	private static MoveBackward instance = new MoveBackward();
	private Camera camera;
	
	public MoveBackward () {
		super("Backward");
	}
	
	public static MoveBackward getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveBackward();
	}
}
