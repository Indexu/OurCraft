package com.ru.tgra.ourcraft.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class SphereGraphic
{
    private static class SphereInfo
    {
        public FloatBuffer vertexBuffer;
        public FloatBuffer normalBuffer;
        public int stacks;
        public int slices;
        public int vertexCount;
    }

    private static SphereInfo polySphere;
    private static SphereInfo sphere;
    private static SphereInfo spear;

    private static int vertexPointer;
    private static int normalPointer;

    public static void create(int vertexPointer, int normalPointer) {
        SphereGraphic.vertexPointer = vertexPointer;
        SphereGraphic.normalPointer = normalPointer;
        //VERTEX ARRAY IS FILLED HERE
        //float[] array = new float[2*verticesPerCircle];

        sphere = new SphereInfo();
        sphere.stacks = 12;
        sphere.slices = 24;
        create(sphere);

        polySphere = new SphereInfo();
        polySphere.stacks = 6;
        polySphere.slices = 12;
        create(polySphere);

        spear = new SphereInfo();
        spear.stacks = 3;
        spear.slices = 24;
        create(spear);
    }

    public static void drawSolidSphere() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, sphere.vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, sphere.normalBuffer);

        for(int i = 0; i < sphere.vertexCount; i += (sphere.slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, i, (sphere.slices+1)*2);
        }
    }

    public static void drawOutlineSphere() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, sphere.vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, sphere.normalBuffer);

        for(int i = 0; i < sphere.vertexCount; i += (sphere.slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, i, (sphere.slices+1)*2);
        }
    }

    public static void drawSolidPolySphere() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, polySphere.vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, polySphere.normalBuffer);

        for(int i = 0; i < polySphere.vertexCount; i += (polySphere.slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, i, (polySphere.slices+1)*2);
        }
    }

    public static void drawOutlinePolySphere() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, polySphere.vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, polySphere.normalBuffer);

        for(int i = 0; i < polySphere.vertexCount; i += (polySphere.slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, i, (polySphere.slices+1)*2);
        }
    }

    public static void drawSolidSpear() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, spear.vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, spear.normalBuffer);

        for(int i = 0; i < spear.vertexCount; i += (spear.slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, i, (spear.slices+1)*2);
        }
    }

    public static void drawOutlineSpear() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, spear.vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, spear.normalBuffer);

        for(int i = 0; i < spear.vertexCount; i += (spear.slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, i, (spear.slices+1)*2);
        }
    }

    private static void create(SphereInfo info)
    {
        info.vertexCount = 0;
        float[] array = new float[(info.stacks)*(info.slices+1)*6];
        float stackInterval = (float)Math.PI / (float)info.stacks;
        float sliceInterval = 2.0f*(float)Math.PI / (float)info.slices;
        float stackAngle, sliceAngle;
        for(int stackCount = 0; stackCount < info.stacks; stackCount++)
        {
            stackAngle = stackCount * stackInterval;
            for(int sliceCount = 0; sliceCount < info.slices+1; sliceCount++)
            {
                sliceAngle = sliceCount * sliceInterval;
                array[info.vertexCount*3] = 	 (float)Math.sin(stackAngle) * (float)Math.cos(sliceAngle);
                array[info.vertexCount*3 + 1] = (float)Math.cos(stackAngle);
                array[info.vertexCount*3 + 2] = (float)Math.sin(stackAngle) * (float)Math.sin(sliceAngle);

                array[info.vertexCount*3 + 3] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.cos(sliceAngle);
                array[info.vertexCount*3 + 4] = (float)Math.cos(stackAngle + stackInterval);
                array[info.vertexCount*3 + 5] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.sin(sliceAngle);

                info.vertexCount += 2;
            }
        }

        info.vertexBuffer = BufferUtils.newFloatBuffer(info.vertexCount*3);
        info.vertexBuffer.put(array);
        info.vertexBuffer.rewind();
        info.normalBuffer = BufferUtils.newFloatBuffer(info.vertexCount*3);
        info.normalBuffer.put(array);
        info.normalBuffer.rewind();
    }
}
