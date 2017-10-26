package com.ru.tgra.ourcraft.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class SincGraphic {

	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;
	private static int verticesPerPlot = 40;

	public static void create(int vertexPointer) {
		SincGraphic.vertexPointer = vertexPointer;
		//VERTEX ARRAY IS FILLED HERE
		//float[] array = new float[2*verticesPerCircle];

		double f = -4.0f;

		vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerPlot);

		for(int i = 0; i < verticesPerPlot; i++)
		{
			vertexBuffer.put(2*i, (float)f);
			if(f == 0.0) {
				vertexBuffer.put(2*i + 1, 1.0f);
			} else {
				vertexBuffer.put(2*i + 1, (float)(Math.sin(Math.PI * f) / (Math.PI * f)));
			}

			f += 8.0 / (double)verticesPerPlot;
		}

		vertexBuffer.rewind();
	}

	public static void drawPlot() {

		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);

		Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, 0, verticesPerPlot);

	}

}
