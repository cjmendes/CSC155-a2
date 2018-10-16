package a2;

import graphicslib3D.*;
import graphicslib3D.shape.*;

import java.awt.event.KeyEvent;
import java.nio.*;
import javax.swing.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.*;

import commands.DisplayAxis;
import commands.MoveBackward;
import commands.MoveDown;
import commands.MoveForward;
import commands.MoveLeft;
import commands.MoveRight;
import commands.MoveUp;
import commands.PanLeft;
import commands.PanRight;
import commands.PitchDown;
import commands.PitchUp;

import com.jogamp.common.nio.Buffers;

@SuppressWarnings("serial")
public class Starter extends JFrame implements GLEventListener {
	// Initial Variables
	private GLCanvas canvas;
	
	private GL4 gl;
	private int rendering_program;
	private int vao[] = new int[1];
	private int vbo[] = new int[11];
	private float sphLocX, sphLocY, sphLocZ;
	private boolean axisOn = true;
	
	//Textures
	private int earthTexture;
	private Texture joglEarthTexture;
	
	private int brickTexture;
	private Texture joglBrickTexture;
	
	private int sanicTexture;
	private Texture joglSanicTexture;
	
	private int redTexture;
	private Texture joglRedTexture;
	
	private int greenTexture;
	private Texture joglGreenTexture;
	
	private int blueTexture;
	private Texture joglBlueTexture;
	
	private Camera camera = new Camera(0, 0, 12);
	private ObjectCreation objC = new ObjectCreation(gl);
	private VertexReading vRead = new VertexReading(gl);
	
	private MatrixStack mvStack = new MatrixStack(20);

	private Sphere mySphere = new Sphere(24);

	public Starter() {

		setTitle("Assignment #2");
		setSize(1400, 1000);
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		getContentPane().add(canvas);
		setVisible(true);
		
		JPanel contentPane = (JPanel) this.getContentPane();
		
		MoveForward.getInstance().setCamera(camera);
		MoveBackward.getInstance().setCamera(camera);
		MoveLeft.getInstance().setCamera(camera);
		MoveRight.getInstance().setCamera(camera);
		MoveDown.getInstance().setCamera(camera);
		MoveUp.getInstance().setCamera(camera);
		PanLeft.getInstance().setCamera(camera);
		PanRight.getInstance().setCamera(camera);
		PitchUp.getInstance().setCamera(camera);
		PitchDown.getInstance().setCamera(camera);
		AbstractAction displayAxis = new DisplayAxis(this);
		
		InputMap imap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = contentPane.getActionMap();
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), KeyEvent.VK_W);
		amap.put(KeyEvent.VK_W, MoveForward.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), KeyEvent.VK_S);
		amap.put(KeyEvent.VK_S, MoveBackward.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), KeyEvent.VK_A);
		amap.put(KeyEvent.VK_A, MoveLeft.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), KeyEvent.VK_D);
		amap.put(KeyEvent.VK_D, MoveRight.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), KeyEvent.VK_E);
		amap.put(KeyEvent.VK_E, MoveDown.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), KeyEvent.VK_Q);
		amap.put(KeyEvent.VK_Q, MoveUp.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), KeyEvent.VK_LEFT);
		amap.put(KeyEvent.VK_LEFT, PanLeft.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), KeyEvent.VK_RIGHT);
		amap.put(KeyEvent.VK_RIGHT, PanRight.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), KeyEvent.VK_UP);
		amap.put(KeyEvent.VK_UP, PitchUp.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), KeyEvent.VK_DOWN);
		amap.put(KeyEvent.VK_DOWN, PitchDown.getInstance());
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), KeyEvent.VK_SPACE);
		amap.put(KeyEvent.VK_SPACE, displayAxis);
		
		this.requestFocus();
		FPSAnimator animator = new FPSAnimator(canvas, 30);
		animator.start();
	}

	public void display(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		
		float bkg[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);

		gl.glClear(GL_DEPTH_BUFFER_BIT);
		gl.glUseProgram(rendering_program);

		int mv_loc = gl.glGetUniformLocation(rendering_program, "mv_matrix");
		int proj_loc = gl.glGetUniformLocation(rendering_program, "proj_matrix");

		float aspect = (float) canvas.getWidth() / (float) canvas.getHeight();
		Matrix3D pMat = perspective(60.0f, aspect, 0.1f, 1000.0f);

		//Push view Matrix onto the stack
		mvStack.pushMatrix();
		mvStack.multMatrix(camera.getView());
		double amt = (double)(System.currentTimeMillis()) / 1000.0;
		
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
		
		if(axisOn) {
			//************ X Axis (Red) **************
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, redTexture);
		
			gl.glDrawArrays(GL_TRIANGLES, 0, 9);
			
			//************ y Axis (Green) **************
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[8]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, greenTexture);
		
			gl.glDrawArrays(GL_TRIANGLES, 0, 9);
			
			//************ Z Axis (Blue) **************
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, blueTexture);
		
			gl.glDrawArrays(GL_TRIANGLES, 0, 9);
		}
		
		//************ Sun (Sphere) **************
		mvStack.pushMatrix();
		mvStack.translate(sphLocX, sphLocY, sphLocZ);
		mvStack.pushMatrix();
		mvStack.rotate((System.currentTimeMillis() / 60.0), 0.0, 1.0, 0.0);
		mvStack.scale(1.5, 1.5, 1.5);
		
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
			//Vertices
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
			//Textures
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, sanicTexture);
			//Render
		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDrawArrays(GL_TRIANGLES, 0, mySphere.getIndices().length);
		mvStack.popMatrix();
		
		//************ Planet 1 (Diamond) **************
		mvStack.pushMatrix();
		mvStack.translate(Math.sin(amt) * 2.0f, 0.0f, Math.cos(amt) * 2.0f);
		mvStack.pushMatrix();
		mvStack.rotate((System.currentTimeMillis() / 10.0), 0.0, 1.0, 0.0);
		
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
			//Vertices
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
			//Textures
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, brickTexture);
			//Render
		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDrawArrays(GL_TRIANGLES, 0, 24);
		mvStack.popMatrix();
		
		//************ Planet 1 Moon (Diamond) **************
		mvStack.translate(Math.sin(amt) * 1.0f, Math.cos(amt) * 1.0f, 0.0f);
		mvStack.pushMatrix();
		mvStack.rotate((System.currentTimeMillis() / 500.0), 0.0, 1.0, 0.0);
		mvStack.scale(0.25,	0.25, 0.25);
		
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
			//Vertices
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
			//Textures
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, brickTexture);
			//Render
		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDrawArrays(GL_TRIANGLES, 0, 24);
		mvStack.popMatrix();
		
		//************ Planet 2 (Sphere) **************
		mvStack.translate(Math.sin(amt) * 2.0f * Math.sinh(1.0f), 0.0f, Math.cos(amt+10f) * 2.0f * Math.cosh(1.0f));
		mvStack.pushMatrix();
		mvStack.rotate((System.currentTimeMillis() / 20.0), 0.0, 1.0, 0.0);
		
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
			//Vertices
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
			//Textures
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, earthTexture);
			//Render
		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDrawArrays(GL_TRIANGLES, 0, mySphere.getIndices().length);
		mvStack.popMatrix();
		
		//************ Planet 2 Moon (Sphere) **************
		mvStack.translate(Math.sin(amt) * 2.0f, Math.cos(amt) * 2.0f, Math.cos(amt) * 3.0f);
		mvStack.pushMatrix();
		mvStack.rotate((System.currentTimeMillis() / 40.0), 0.0, 4.0, 0.0);
		mvStack.scale(0.25, 0.25, 0.25);
		
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
			//Vertices
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
			//Textures
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, earthTexture);
			//Render
		gl.glEnable(GL_CULL_FACE);
		gl.glFrontFace(GL_CCW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDrawArrays(GL_TRIANGLES, 0, mySphere.getIndices().length);
		mvStack.popMatrix();
		
		mvStack.popMatrix();
		mvStack.popMatrix();
		mvStack.popMatrix();
		
	}

	@SuppressWarnings("deprecation")
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		System.out.println("OpenGL Version: " + gl.glGetString(GL_VERSION));
		System.out.println("JOGL Version: " + Package.getPackage("com.jogamp.opengl").getImplementationVersion());
		System.out.println("Java Version: " + System.getProperty("java.version"));
		
		rendering_program = vRead.createShaderProgram();
		objC.setupVertices(mySphere.getVertices(), mySphere.getIndices(), vao, vbo);
		sphLocX = 0.0f; sphLocY = 0.0f; sphLocZ = 0.0f;
		
		joglEarthTexture = objC.loadTexture("src/assets/earth.jpg");
		earthTexture = joglEarthTexture.getTextureObject();
		
		joglBrickTexture = objC.loadTexture("src/assets/brick.jpg");
		brickTexture = joglBrickTexture.getTextureObject();
		
		joglSanicTexture = objC.loadTexture("src/assets/sanic.png");
		sanicTexture = joglSanicTexture.getTextureObject();
		
		joglRedTexture = objC.loadTexture("src/assets/red.png");
		redTexture = joglRedTexture.getTextureObject();
		
		joglGreenTexture = objC.loadTexture("src/assets/green.png");
		greenTexture = joglGreenTexture.getTextureObject();
		
		joglBlueTexture = objC.loadTexture("src/assets/blue.png");
		blueTexture = joglBlueTexture.getTextureObject();
	}
	
	public Matrix3D perspective(float fovy, float aspect, float n, float f)
	{	float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
		float A = q / aspect;
		float B = (n + f) / (n - f);
		float C = (2.0f * n * f) / (n - f);
		Matrix3D r = new Matrix3D();
		r.setElementAt(0,0,A);
		r.setElementAt(1,1,q);
		r.setElementAt(2,2,B);
		r.setElementAt(3,2,-1.0f);
		r.setElementAt(2,3,C);
		r.setElementAt(3,3,0.0f);
		return r;
	}
	
	public void displayAxis() {
		axisOn = (axisOn) ? false : true;
	}

	public static void main(String[] args) { 
		new Starter(); 
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}

}