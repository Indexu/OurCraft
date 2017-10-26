package com.ru.tgra.ourcraft.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class CoordFrameGraphic {

	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;

	public static void create(int vertexPointer) {
		CoordFrameGraphic.vertexPointer = vertexPointer;
		//VERTEX ARRAY IS FILLED HERE
		float[] array = {0.0f, 0.0f, 1.0f, 0.0f,
						1.0f, 0.0f, 0.8f, 0.2f,
						1.0f, 0.0f, 0.8f, -0.2f,
						0.0f, 0.0f, 0.0f, 1.0f,
						0.0f, 1.0f, -0.2f, 0.8f,
						0.0f, 1.0f, 0.2f, 0.8f,};

		vertexBuffer = BufferUtils.newFloatBuffer(24);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
	}

	public static void drawPlot() {

		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);

		Gdx.gl.glDrawArrays(GL20.GL_LINES, 0, 12);

	}

}
