package a2;

import graphicslib3D.*;
import graphicslib3D.shape.*;

import java.nio.*;
import javax.swing.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.common.nio.Buffers;

@SuppressWarnings("serial")
public class Starter extends JFrame implements GLEventListener {
	// Initial Variables
	private GLCanvas canvas;
	private GL4 gl;
	private int rendering_program;
	private int vao[] = new int[1];
	private int vbo[] = new int[6];
	private float cameraX, cameraY, cameraZ;
	private float sphLocX, sphLocY, sphLocZ;
	private float diaLocX, diaLocY, diaLocZ;
	private int earthTexture;
	private Texture joglEarthTexture;
	private int brickTexture;
	private Texture joglBrickTexture;
	
	private Camera myCamera = new Camera();
	private ObjectCreation objC = new ObjectCreation(gl);
	private VertexReading vRead = new VertexReading(gl);
	
	private MatrixStack mvStack = new MatrixStack(20);

	private Sphere mySphere = new Sphere(24);

	public Starter() {
		commands.ColorCommand colorCommand = new commands.ColorCommand(this);
		
		// get the content pane of the JFrame (this)
		JComponent contentPane = (JComponent) this.getContentPane();
		// get the "focus is in the window" input map for the content pane
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = contentPane.getInputMap(mapName);
		// create a keystroke object to represent the "c" key
		KeyStroke cKey = KeyStroke.getKeyStroke('c');
		// put the "cKey" keystroke object into the content pane’s "when focus is
		// in the window" input map under the identifier name "color“
		imap.put(cKey, "color");
		// get the action map for the content pane
		ActionMap amap = contentPane.getActionMap();
		// put the "myCommand" command object into the content pane's action map
		amap.put("color", colorCommand);
		//have the JFrame request keyboard focus
		this.requestFocus();
		
		
		setTitle("Assignment #2");
		setSize(800, 800);
		//setLocation(200, 200);
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		getContentPane().add(canvas);
		setVisible(true);
		FPSAnimator animator = new FPSAnimator(canvas, 30);
		animator.start();
	}

	public void display(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		
		float bkg[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);

		gl.glUseProgram(rendering_program);

		int mv_loc = gl.glGetUniformLocation(rendering_program, "mv_matrix");
		int proj_loc = gl.glGetUniformLocation(rendering_program, "proj_matrix");

		float aspect = (float) canvas.getWidth() / (float) canvas.getHeight();
		Matrix3D pMat = perspective(60.0f, aspect, 0.1f, 1000.0f);

		//Push view Matrix onto the stack
		mvStack.pushMatrix();
		mvStack.translate(-cameraX, -cameraY, -cameraZ);
		double amt = (double)(System.currentTimeMillis()) / 1000.0;
		
		
		//************ Sphere = Sun **************
		mvStack.pushMatrix();
		mvStack.translate(sphLocX, sphLocY, sphLocZ);
		mvStack.pushMatrix();
		mvStack.rotate((System.currentTimeMillis() / 60.0), 0.0, 1.0, 0.0);
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
		
		//************ Diamond = Planet **************
		mvStack.pushMatrix();
		mvStack.translate(Math.sin(amt) * 4.0f, 0.0f, Math.cos(amt) * 4.0f);
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
		
		//************ Sphere = Planet 2 **************
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
		cameraX = 0.0f; cameraY = 0.0f; cameraZ = 12.0f;
		sphLocX = 0.0f; sphLocY = 0.0f; sphLocZ = 0.0f;
		diaLocX = 0.0f; diaLocY = 0.0f; diaLocZ = 0.0f;
		
		joglEarthTexture = objC.loadTexture("src/assets/earth.jpg");
		earthTexture = joglEarthTexture.getTextureObject();
		
		joglBrickTexture = objC.loadTexture("src/assets/brick.jpg");
		brickTexture = joglBrickTexture.getTextureObject();
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

	public static void main(String[] args) { 
		new Starter(); 
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}

}