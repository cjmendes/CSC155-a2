package a2;

import java.io.File;
import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import graphicslib3D.Vertex3D;

public class ObjectCreation {
	
	private static GL4 gl;
	
	
	public ObjectCreation(GL4 gl) {
		ObjectCreation.gl = gl;
	}
	
	@SuppressWarnings("static-access")
	public void setupVertices(Vertex3D[] vertices, int[] indices, int[] vao, int[] vbo) {
		gl = (GL4) GLContext.getCurrentGL();
		
		float[] pvalues = new float[indices.length*3];
		float[] tvalues = new float[indices.length*2];
		float[] nvalues = new float[indices.length*3];
		
		for (int i=0; i<indices.length; i++) {
			pvalues[i*3] = (float) (vertices[indices[i]]).getX();
			pvalues[i*3+1] = (float) (vertices[indices[i]]).getY();
			pvalues[i*3+2] = (float) (vertices[indices[i]]).getZ();
			tvalues[i*2] = (float) (vertices[indices[i]]).getS();
			tvalues[i*2+1] = (float) (vertices[indices[i]]).getT();
			nvalues[i*3] = (float) (vertices[indices[i]]).getNormalX();
			nvalues[i*3+1]= (float)(vertices[indices[i]]).getNormalY();
			nvalues[i*3+2]=(float) (vertices[indices[i]]).getNormalZ();
		}
		
		float[] dpvalues = {
			-0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.0f, 0.5f, 0.0f, //front
			0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.0f, 0.5f, 0.0f, //right
			0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.0f, 0.5f, 0.0f, //back
			-0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, 0.0f, 0.5f, 0.0f, //left
			0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f,0.0f, -1.5f, 0.0f, //front t2
			0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.0f, -1.5f, 0.0f, //right t2
			-0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.0f, -1.5f, 0.0f, //back t2
			-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.0f, -1.5f, 0.0f //left t2
		};
		
		float[] dtvalues = {
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f
		};
		
		
		float[] xPositions = {
			0.0f, 0.0f, 0.0f, 
			100.0f, 0.0f, 0.0f, 
			0.0f, 0.05f, 0.0f	
		};
		
		float[] xTextures =	{
			0.0f, 0.0f, 1.0f, 
			0.0f, 1.0f, 1.0f	
		};
		
		float[] yPositions = {
			0.0f, 0.0f, 0.0f, 
			0.05f, 0.0f, 0.0f, 
			0.0f, 100.0f, 0.0f
		};
		
		float[] yTextures = {
			0.0f, 0.0f, 1.0f, 
			0.0f, 1.0f, 1.0f	
		};
		
		float[] zPositions = {
			0.0f, 0.0f, 0.0f, 
			0.0f, 0.0f, 100.0f, 
			0.0f, -0.05f, 0.0f
		};
		
		float[] zTextures =	{
			0.0f, 0.0f, 1.0f, 
			0.0f, 1.0f, 1.0f	
		};
		
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
		gl.glGenBuffers(vbo.length, vbo, 0);
		
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(pvalues);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[1]);
		FloatBuffer texBuf = Buffers.newDirectFloatBuffer(tvalues);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, texBuf.limit()*4, texBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[2]);
		FloatBuffer norBuf = Buffers.newDirectFloatBuffer(nvalues);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, norBuf.limit()*4,norBuf, gl.GL_STATIC_DRAW);
		
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[3]);
		FloatBuffer dvertBuf = Buffers.newDirectFloatBuffer(dpvalues);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, dvertBuf.limit()*4, dvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[4]);
		FloatBuffer dtexBuf = Buffers.newDirectFloatBuffer(dtvalues);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, dtexBuf.limit()*4, dtexBuf, gl.GL_STATIC_DRAW);
		
		// X Line
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[5]);
		FloatBuffer xvertBuf = Buffers.newDirectFloatBuffer(xPositions);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, xvertBuf.limit()*4, xvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[6]);
		FloatBuffer xtexBuf = Buffers.newDirectFloatBuffer(xTextures);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, xtexBuf.limit()*4, xtexBuf, gl.GL_STATIC_DRAW);
		
		// Y Line
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[7]);
		FloatBuffer yvertBuf = Buffers.newDirectFloatBuffer(yPositions);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, yvertBuf.limit()*4, yvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[8]);
		FloatBuffer ytexBuf = Buffers.newDirectFloatBuffer(yTextures);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, ytexBuf.limit()*4, ytexBuf, gl.GL_STATIC_DRAW);
		
		// Z Line
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[9]);
		FloatBuffer zvertBuf = Buffers.newDirectFloatBuffer(zPositions);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, zvertBuf.limit()*4, zvertBuf, gl.GL_STATIC_DRAW);

		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[10]);
		FloatBuffer ztexBuf = Buffers.newDirectFloatBuffer(zTextures);
		gl.glBufferData(gl.GL_ARRAY_BUFFER, ztexBuf.limit()*4, ztexBuf, gl.GL_STATIC_DRAW);
		
		
	}
	
	public Texture loadTexture(String textureFileName) {
		Texture tex = null;
		try { tex = TextureIO.newTexture(new File(textureFileName), false); }
		catch (Exception e) { e.printStackTrace(); }
		return tex;
	}
	
}
