package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


@SuppressWarnings("serial")
public class MoveUp extends AbstractAction{

	private static MoveUp instance = new MoveUp();
	private Camera camera;
	
	public MoveUp () {
		super("Up");
	}
	
	public static MoveUp getInstance() {
		return instance;
	}
	
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveUp();
	}

}