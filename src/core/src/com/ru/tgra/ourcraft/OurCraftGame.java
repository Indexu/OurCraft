package com.ru.tgra.ourcraft;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.ru.tgra.ourcraft.models.ModelMatrix;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;
import com.ru.tgra.ourcraft.objects.Block;
import com.ru.tgra.ourcraft.objects.GameObject;
import com.ru.tgra.ourcraft.objects.Player;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;
import com.ru.tgra.ourcraft.shapes.CoordFrameGraphic;
import com.ru.tgra.ourcraft.shapes.SincGraphic;
import com.ru.tgra.ourcraft.shapes.SphereGraphic;
import com.ru.tgra.ourcraft.utilities.CollisionsUtil;

public class OurCraftGame extends ApplicationAdapter
{
	private Shader shader;

	private Point3D mainMenuTitlePosition;
	private Point3D mainMenuMovePosition;
	private Point3D mainMenuMousePosition;
	private Point3D mainMenuOrbPosition;
	private Point3D mainMenuPlayPosition;
	private Point3D mainMenuMessagePosition;
	private float oldMouseX;
	private float oldMouseY;
	private float textTimer;
	private Cursor crosshair;

	@Override
	public void create ()
	{
		init();

		GameManager.mainMenu = false;
		GameManager.createWorld();
	}

	private void mainMenuInput()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			GameManager.mainMenu = false;
			GameManager.createWorld();
		}
	}

	private void input()
	{
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY() - Gdx.graphics.getHeight();

		if (GameManager.mainMenu)
		{
			mainMenuInput();

			return;
		}

		if (mouseX != oldMouseX)
		{
			GameManager.player.mouseLookYaw(oldMouseX - mouseX);
			oldMouseX = mouseX;
		}

		if (mouseY != oldMouseY)
		{
			GameManager.player.mouseLookPitch(oldMouseY - mouseY);
			oldMouseY = mouseY;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			GameManager.player.lookLeft();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			GameManager.player.lookRight();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			GameManager.player.lookUp();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			GameManager.player.lookDown();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			GameManager.player.moveLeft();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			GameManager.player.moveRight();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			GameManager.player.moveForward();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			GameManager.player.moveBack();
		}

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            GameManager.player.jump();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.N))
        {
            GameManager.noclip = !GameManager.noclip;
        }

//        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
//        {
//            System.out.println(" | MOUSE LEFT");
//
//            if (GameManager.player.getTargetBlock() != null)
//            {
//                GameManager.player.getTargetBlock().destroy();
//            }
//        }
	}

	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (GameManager.mainMenu)
		{
			return;
		}

		for (GameObject gameObject : GameManager.gameObjects)
		{
			gameObject.update(deltaTime);
		}

        //GameManager.gameObjects.removeIf(GameObject::isDestroyed);

		if (!GameManager.noclip)
        {
            CollisionsUtil.playerBlockCollisions(GameManager.player);
        }

        GameManager.removeBlocks();
	}

	private void display()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.graphics.setTitle("OurCraft | FPS: " + Gdx.graphics.getFramesPerSecond());

		if (GameManager.mainMenu)
		{
			drawMainMenu();

			return;
		}

		for (int viewNum = 0; viewNum < 2; viewNum++)
		{
			if (viewNum == 0)
			{
				Gdx.gl.glViewport
                (
                    (int) GraphicsEnvironment.viewport.x,
                    (int) GraphicsEnvironment.viewport.y,
                    (int) GraphicsEnvironment.viewport.width,
                    (int) GraphicsEnvironment.viewport.height
                );

//                Gdx.gl.glViewport
//                (
//                    0,
//                    0,
//                    Gdx.graphics.getWidth(),
//                    Gdx.graphics.getHeight()
//                );

				GameManager.player.getCamera().setPerspectiveProjection(Settings.playerFOV, GraphicsEnvironment.viewport.width / GraphicsEnvironment.viewport.height, 0.1f, 50.0f);
				shader.setViewMatrix(GameManager.player.getCamera().getViewMatrix());
				shader.setProjectionMatrix(GameManager.player.getCamera().getProjectionMatrix());
				shader.setEyePosition(GameManager.player.getCamera().eye);
			}
			else
			{
			    if (true)
                {
                    continue;
                }

				Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

                Gdx.gl.glViewport
                (
                        (int) (GraphicsEnvironment.viewport.width * 0.75f),
                        (int) (GraphicsEnvironment.viewport.height * 0.61f),
                        (int) (GraphicsEnvironment.viewport.width / 4.5f),
                        (int) (GraphicsEnvironment.viewport.height / 2.5f)
                );

				GameManager.minimapCamera.look(new Point3D(GameManager.player.getCamera().eye.x, 30.0f, GameManager.player.getCamera().eye.z), GameManager.player.getPosition(), new Vector3D(0, 0, 1));
				shader.setViewMatrix(GameManager.minimapCamera.getViewMatrix());
				shader.setProjectionMatrix(GameManager.minimapCamera.getProjectionMatrix());
			}

			shader.setGlobalAmbience(Settings.globalAmbience);

			if (viewNum == Settings.viewportIDPerspective)
			{
				GameManager.headLight.setPosition(GameManager.player.getPosition(), true);
				GameManager.headLight.setDirection(new Vector3D(-GameManager.player.getCamera().n.x, -GameManager.player.getCamera().n.y, -GameManager.player.getCamera().n.z));
			}
			else
			{
				GameManager.headLight.getPosition().y = 3f;
				GameManager.headLight.getDirection().add(new Vector3D(0f, -1f, 0f));
			}

			GraphicsEnvironment.shader.setLight(GameManager.headLight);

            GameManager.drawCount = 0;

			GameManager.drawWorld(viewNum);

			for (GameObject gameObject : GameManager.gameObjects)
			{
				gameObject.draw(viewNum);
			}

			System.out.print(" | Draw Count: " + GameManager.drawCount);
		}

	}

	private void drawMainMenu()
	{
//		GraphicsEnvironment.drawText(mainMenuTitlePosition, "Labyrinth", new Color(0.8f, 0.0f, 0.0f, 1.0f), 3);
//		GraphicsEnvironment.drawText(mainMenuMovePosition, "Use WASD to move", new Color(1.0f, 1.0f, 1.0f, 1.0f), 2);
//		GraphicsEnvironment.drawText(mainMenuMousePosition, "Mouse or arrow keys to look around", new Color(1.0f, 1.0f, 1.0f, 1.0f), 2);
//		GraphicsEnvironment.drawText(mainMenuOrbPosition, "Find the red orb", new Color(1.0f, 1.0f, 1.0f, 1.0f), 2);
//		GraphicsEnvironment.drawText(mainMenuPlayPosition, "Press ENTER to play", new Color(1.0f, 1.0f, 1.0f, 1.0f), 2);
//		GraphicsEnvironment.drawText(mainMenuMessagePosition, "Good luck and beware the above...", new Color(1.0f, 1.0f, 1.0f, 1.0f), 2);
	}

	@Override
	public void render ()
	{
        System.out.print("\r                                                                                            \r");
		input();
		update();
		display();
	}

	@Override
	public void resize(int width, int height)
	{
        // calculate new viewport
        float aspectRatio = (float) width / (float) height;
        float scale = 1f;
        float cropX = 0f;
        float cropY = 0f;

        if(Settings.aspectRatio < aspectRatio)
        {
            scale = (float) height / (float) Settings.virtualHeight;
            cropX = (width - Settings.virtualWidth * scale) / 2f;
        }
        else if(aspectRatio < Settings.aspectRatio)
        {
            scale = (float) width/ (float) Settings.virtualWidth;
            cropY = (height - Settings.virtualHeight * scale)/2f;
        }
        else
        {
            scale = (float) width / (float) Settings.virtualWidth;
        }

        float w = (float) Settings.virtualWidth * scale;
        float h = (float) Settings.virtualHeight * scale;

        GraphicsEnvironment.setViewport(cropX, cropY, w, h);
	}

	private void init()
	{
		GraphicsEnvironment.init();
		GameManager.init();
		AudioManager.init();
		TextureManager.init();
        initInput();

		shader = GraphicsEnvironment.shader;

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

//        crosshair = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("crosshairs/crosshair.png")), 32, 32);
//        Gdx.graphics.setCursor(crosshair);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		oldMouseX = Gdx.input.getX();
		oldMouseY = Gdx.input.getY() - Gdx.graphics.getHeight();
		textTimer = 0f;

		float offset = Gdx.graphics.getHeight() / 8;

		mainMenuTitlePosition = new Point3D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0f);
		mainMenuTitlePosition.y += offset * 2.5f;

		mainMenuMovePosition = new Point3D(mainMenuTitlePosition);
		mainMenuMovePosition.y -= offset;

		mainMenuMousePosition = new Point3D(mainMenuMovePosition);
		mainMenuMousePosition.y -= offset;

		mainMenuOrbPosition = new Point3D(mainMenuMousePosition);
		mainMenuOrbPosition.y -= offset;

		mainMenuPlayPosition = new Point3D(mainMenuOrbPosition);
		mainMenuPlayPosition.y -= offset;

		mainMenuMessagePosition = new Point3D(mainMenuPlayPosition);
		mainMenuMessagePosition.y -= offset;
	}

	private void initInput()
	{
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button)
            {
				if (button == Input.Buttons.LEFT)
				{
                    if (GameManager.player.getTargetBlock() != null)
                    {
                        Block.TargetFace face = GameManager.player.getTargetBlock().getTargetFace();
                        String f = "";

                        switch (face)
                        {
                            case TOP:
                                f = "TOP";
                                break;

                            case BOTTOM:
                                f = "BOTTOM";
                                break;

                            case SOUTH:
                                f = "SOUTH";
                                break;

                            case NORTH:
                                f = "NORTH";
                                break;

                            case EAST:
                                f = "EAST";
                                break;

                            case WEST:
                                f = "WEST";
                                break;

                            default:
                                break;
                        }

                        System.out.println(f);
                        GameManager.player.getTargetBlock().destroy();
                    }
				}

				return false;
			}
        });
	}
}
