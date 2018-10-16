package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;

@SuppressWarnings("serial")
public class MoveLeft extends AbstractAction{

	private static MoveLeft instance = new MoveLeft();
	private Camera camera;
	
	public MoveLeft () {
		super("Left");
	}
	
	public static MoveLeft getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveLeft();
	}

}