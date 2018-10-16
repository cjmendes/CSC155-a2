package a2;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;

public class Camera {
	private double xLoc;
	private double yLoc;
	private double zLoc;
	float pitch = 0;
	float pan = 0;
	private Matrix3D viewMat;
	
	public Camera(double x, double y, double z) {
		this.xLoc = x;
		this.yLoc = y;
		this.zLoc = z;
		
		viewMat = new Matrix3D();
		computeView();
	}
	
	public void computeView() {
		Vector3D newLoc = new Vector3D(xLoc, yLoc, zLoc);
		float cosPitch = (float)Math.cos(Math.toRadians(pitch));
		float sinPitch = (float)Math.sin(Math.toRadians(pitch));
		float cosYaw = (float)Math.cos(Math.toRadians(pan));
		float sinYaw = (float)Math.sin(Math.toRadians(pan));
		
		Vector3D xAxis = new Vector3D(cosYaw, 0, -sinYaw);
		Vector3D yAxis = new Vector3D(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
		Vector3D zAxis = new Vector3D(sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw);
		double[] matArray = new double[] {
				xAxis.getX(), yAxis.getX(), zAxis.getX(), 0,
				xAxis.getY(), yAxis.getY(), zAxis.getY(), 0, 
				xAxis.getZ(), yAxis.getZ(), zAxis.getZ(), 0, 
				-(xAxis.dot(newLoc)), -(yAxis.dot(newLoc)), -(zAxis.dot(newLoc)), 1};
		viewMat.setValues(matArray);
	}
	
	public void moveForward() {
		this.zLoc = zLoc - 0.125;
		computeView();
	}
	
	public void moveBackward() {
		this.zLoc = zLoc + 0.125;
		computeView();
	}
	
	public void moveLeft() {
		this.xLoc = xLoc - 0.125;
		computeView();
	}
	
	public void moveRight() {
		this.xLoc = xLoc + 0.125;
		computeView();
	}
	
	public void moveUp() {
		this.yLoc = yLoc + 0.125;
		computeView();
	}
	
	public void moveDown() {
		this.yLoc = yLoc - 0.125;
		computeView();
	}
	
	public void panLeft() {
		this.pan++;
		computeView();
	}
	
	public void panRight() {
		this.pan--;
		computeView();
	}
	
	public void pitchUp() {
		this.pitch++;
		computeView();
	}
	
	public void pitchDown() {
		this.pitch--;
		computeView();
	}
	
	public Matrix3D getView() {
		return viewMat;
	}
	
}