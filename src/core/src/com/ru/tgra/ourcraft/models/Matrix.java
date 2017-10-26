package com.ru.tgra.ourcraft.models;

import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Stack;

public class Matrix {

	public FloatBuffer matrix;
	Stack<FloatBuffer> matrixStack;
	float[] Mtmp;
	
	public Matrix() {
		matrix = BufferUtils.newFloatBuffer(16);
		matrixStack = new Stack<FloatBuffer>();
		Mtmp = new float[16];
	}

	public void loadIdentityMatrix() {

	// m0  m4  m8  m12
	// m1  m5  m9  m13
	// m2  m6  m10  m14
	// m3  m7  m11  m15

		matrix.put(0, 1.0f);
		matrix.put(1, 0.0f);
		matrix.put(2, 0.0f);
		matrix.put(3, 0.0f);
		matrix.put(4, 0.0f);
		matrix.put(5, 1.0f);
		matrix.put(6, 0.0f);
		matrix.put(7, 0.0f);
		matrix.put(8, 0.0f);
		matrix.put(9, 0.0f);
		matrix.put(10, 1.0f);
		matrix.put(11, 0.0f);
		matrix.put(12, 0.0f);
		matrix.put(13, 0.0f);
		matrix.put(14, 0.0f);
		matrix.put(15, 1.0f);
	}

	public void addTransformation(float[] M2) {
		this.Mtmp[0] = matrix.get(0)*M2[0] + matrix.get(4)*M2[1] + matrix.get(8)*M2[2] + matrix.get(12)*M2[3];
		this.Mtmp[1] = matrix.get(1)*M2[0] + matrix.get(5)*M2[1] + matrix.get(9)*M2[2] + matrix.get(13)*M2[3];
		this.Mtmp[2] = matrix.get(2)*M2[0] + matrix.get(6)*M2[1] + matrix.get(10)*M2[2] + matrix.get(14)*M2[3];
		this.Mtmp[3] = matrix.get(3)*M2[0] + matrix.get(7)*M2[1] + matrix.get(11)*M2[2] + matrix.get(15)*M2[3];
		this.Mtmp[4] = matrix.get(0)*M2[4] + matrix.get(4)*M2[5] + matrix.get(8)*M2[6] + matrix.get(12)*M2[7];
		this.Mtmp[5] = matrix.get(1)*M2[4] + matrix.get(5)*M2[5] + matrix.get(9)*M2[6] + matrix.get(13)*M2[7];
		this.Mtmp[6] = matrix.get(2)*M2[4] + matrix.get(6)*M2[5] + matrix.get(10)*M2[6] + matrix.get(14)*M2[7];
		this.Mtmp[7] = matrix.get(3)*M2[4] + matrix.get(7)*M2[5] + matrix.get(11)*M2[6] + matrix.get(15)*M2[7];
		this.Mtmp[8] = matrix.get(0)*M2[8] + matrix.get(4)*M2[9] + matrix.get(8)*M2[10] + matrix.get(12)*M2[11];
		this.Mtmp[9] = matrix.get(1)*M2[8] + matrix.get(5)*M2[9] + matrix.get(9)*M2[10] + matrix.get(13)*M2[11];
		this.Mtmp[10] = matrix.get(2)*M2[8] + matrix.get(6)*M2[9] + matrix.get(10)*M2[10] + matrix.get(14)*M2[11];
		this.Mtmp[11] = matrix.get(3)*M2[8] + matrix.get(7)*M2[9] + matrix.get(11)*M2[10] + matrix.get(15)*M2[11];
		this.Mtmp[12] = matrix.get(0)*M2[12] + matrix.get(4)*M2[13] + matrix.get(8)*M2[14] + matrix.get(12)*M2[15];
		this.Mtmp[13] = matrix.get(1)*M2[12] + matrix.get(5)*M2[13] + matrix.get(9)*M2[14] + matrix.get(13)*M2[15];
		this.Mtmp[14] = matrix.get(2)*M2[12] + matrix.get(6)*M2[13] + matrix.get(10)*M2[14] + matrix.get(14)*M2[15];
		this.Mtmp[15] = matrix.get(3)*M2[12] + matrix.get(7)*M2[13] + matrix.get(11)*M2[14] + matrix.get(15)*M2[15];

		matrix.put(0, this.Mtmp[0]);
		matrix.put(1, this.Mtmp[1]);
		matrix.put(2, this.Mtmp[2]);
		matrix.put(3, this.Mtmp[3]);
		matrix.put(4, this.Mtmp[4]);
		matrix.put(5, this.Mtmp[5]);
		matrix.put(6, this.Mtmp[6]);
		matrix.put(7, this.Mtmp[7]);
		matrix.put(8, this.Mtmp[8]);
		matrix.put(9, this.Mtmp[9]);
		matrix.put(10, this.Mtmp[10]);
		matrix.put(11, this.Mtmp[11]);
		matrix.put(12, this.Mtmp[12]);
		matrix.put(13, this.Mtmp[13]);
		matrix.put(14, this.Mtmp[14]);
		matrix.put(15, this.Mtmp[15]);
	}

	public void addTransformation(FloatBuffer M2) {
		this.Mtmp[0] = matrix.get(0)*M2.get(0) + matrix.get(4)*M2.get(1) + matrix.get(8)*M2.get(2) + matrix.get(12)*M2.get(3);
		this.Mtmp[1] = matrix.get(1)*M2.get(0) + matrix.get(5)*M2.get(1) + matrix.get(9)*M2.get(2) + matrix.get(13)*M2.get(3);
		this.Mtmp[2] = matrix.get(2)*M2.get(0) + matrix.get(6)*M2.get(1) + matrix.get(10)*M2.get(2) + matrix.get(14)*M2.get(3);
		this.Mtmp[3] = matrix.get(3)*M2.get(0) + matrix.get(7)*M2.get(1) + matrix.get(11)*M2.get(2) + matrix.get(15)*M2.get(3);
		this.Mtmp[4] = matrix.get(0)*M2.get(4) + matrix.get(4)*M2.get(5) + matrix.get(8)*M2.get(6) + matrix.get(12)*M2.get(7);
		this.Mtmp[5] = matrix.get(1)*M2.get(4) + matrix.get(5)*M2.get(5) + matrix.get(9)*M2.get(6) + matrix.get(13)*M2.get(7);
		this.Mtmp[6] = matrix.get(2)*M2.get(4) + matrix.get(6)*M2.get(5) + matrix.get(10)*M2.get(6) + matrix.get(14)*M2.get(7);
		this.Mtmp[7] = matrix.get(3)*M2.get(4) + matrix.get(7)*M2.get(5) + matrix.get(11)*M2.get(6) + matrix.get(15)*M2.get(7);
		this.Mtmp[8] = matrix.get(0)*M2.get(8) + matrix.get(4)*M2.get(9) + matrix.get(8)*M2.get(10) + matrix.get(12)*M2.get(11);
		this.Mtmp[9] = matrix.get(1)*M2.get(8) + matrix.get(5)*M2.get(9) + matrix.get(9)*M2.get(10) + matrix.get(13)*M2.get(11);
		this.Mtmp[10] = matrix.get(2)*M2.get(8) + matrix.get(6)*M2.get(9) + matrix.get(10)*M2.get(10) + matrix.get(14)*M2.get(11);
		this.Mtmp[11] = matrix.get(3)*M2.get(8) + matrix.get(7)*M2.get(9) + matrix.get(11)*M2.get(10) + matrix.get(15)*M2.get(11);
		this.Mtmp[12] = matrix.get(0)*M2.get(12) + matrix.get(4)*M2.get(13) + matrix.get(8)*M2.get(14) + matrix.get(12)*M2.get(15);
		this.Mtmp[13] = matrix.get(1)*M2.get(12) + matrix.get(5)*M2.get(13) + matrix.get(9)*M2.get(14) + matrix.get(13)*M2.get(15);
		this.Mtmp[14] = matrix.get(2)*M2.get(12) + matrix.get(6)*M2.get(13) + matrix.get(10)*M2.get(14) + matrix.get(14)*M2.get(15);
		this.Mtmp[15] = matrix.get(3)*M2.get(12) + matrix.get(7)*M2.get(13) + matrix.get(11)*M2.get(14) + matrix.get(15)*M2.get(15);

		matrix.put(0, this.Mtmp[0]);
		matrix.put(1, this.Mtmp[1]);
		matrix.put(2, this.Mtmp[2]);
		matrix.put(3, this.Mtmp[3]);
		matrix.put(4, this.Mtmp[4]);
		matrix.put(5, this.Mtmp[5]);
		matrix.put(6, this.Mtmp[6]);
		matrix.put(7, this.Mtmp[7]);
		matrix.put(8, this.Mtmp[8]);
		matrix.put(9, this.Mtmp[9]);
		matrix.put(10, this.Mtmp[10]);
		matrix.put(11, this.Mtmp[11]);
		matrix.put(12, this.Mtmp[12]);
		matrix.put(13, this.Mtmp[13]);
		matrix.put(14, this.Mtmp[14]);
		matrix.put(15, this.Mtmp[15]);
	}

	public void pushMatrix()
	{
		Mtmp[0] = matrix.get(0);
		Mtmp[1] = matrix.get(1);
		Mtmp[2] = matrix.get(2);
		Mtmp[3] = matrix.get(3);
		Mtmp[4] = matrix.get(4);
		Mtmp[5] = matrix.get(5);
		Mtmp[6] = matrix.get(6);
		Mtmp[7] = matrix.get(7);
		Mtmp[8] = matrix.get(8);
		Mtmp[9] = matrix.get(9);
		Mtmp[10] = matrix.get(10);
		Mtmp[11] = matrix.get(11);
		Mtmp[12] = matrix.get(12);
		Mtmp[13] = matrix.get(13);
		Mtmp[14] = matrix.get(14);
		Mtmp[15] = matrix.get(15);

		FloatBuffer tmp = BufferUtils.newFloatBuffer(16);
		tmp.put(Mtmp);
		tmp.rewind();
		matrixStack.push(tmp);
	}
	
	public void popMatrix()
	{
		FloatBuffer tmp = matrixStack.pop();
		matrix.put(0, tmp.get(0));
		matrix.put(1, tmp.get(1));
		matrix.put(2, tmp.get(2));
		matrix.put(3, tmp.get(3));
		matrix.put(4, tmp.get(4));
		matrix.put(5, tmp.get(5));
		matrix.put(6, tmp.get(6));
		matrix.put(7, tmp.get(7));
		matrix.put(8, tmp.get(8));
		matrix.put(9, tmp.get(9));
		matrix.put(10, tmp.get(10));
		matrix.put(11, tmp.get(11));
		matrix.put(12, tmp.get(12));
		matrix.put(13, tmp.get(13));
		matrix.put(14, tmp.get(14));
		matrix.put(15, tmp.get(15));
	}

	public FloatBuffer getMatrix()
	{
		return matrix;
	}

	@Override
	public String toString()
	{
		String s = "";

		for (int i = 0; i < 4; i++)
        {
            s += "|";

            for (int j = 0; j < 4; j++)
            {
                float num = matrix.get(j + (i * 4));
                String strNum = String.format("%.2f", num);
                s += strNum + "|";
            }

            s += "\n";

            for (int j = 0; j < 4; j++)
            {
                s += "-----";
            }

            s += "\n";
        }

        return s;
	}
}
