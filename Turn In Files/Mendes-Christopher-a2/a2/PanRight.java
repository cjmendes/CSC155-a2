package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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