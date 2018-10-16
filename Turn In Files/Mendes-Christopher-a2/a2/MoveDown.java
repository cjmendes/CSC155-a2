package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveDown extends AbstractAction{

	private static MoveDown instance = new MoveDown();
	private Camera camera;
	
	public MoveDown () {
		super("Down");
	}
	
	public static MoveDown getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveDown();
	}

}