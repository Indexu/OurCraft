package com.ru.tgra.ourcraft;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.objects.Block;
import com.ru.tgra.ourcraft.objects.GameObject;
import com.ru.tgra.ourcraft.objects.Player;
import com.ru.tgra.ourcraft.shapes.*;
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

	private Point2D center;

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            GameManager.player.jump();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        {
            GameManager.player.sprint();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.N))
        {
            GameManager.noclip = !GameManager.noclip;
            GameManager.player.resetGravity();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
        {
            GameManager.player.toggleTorch();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
        {
            GameManager.player.placeBlock();
        }
	}

	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (GameManager.mainMenu)
		{
			return;
		}

		LightManager.updateSunAngle(deltaTime);

		for (GameObject gameObject : GameManager.gameObjects)
		{
			gameObject.update(deltaTime);
		}

		if (GameManager.player.isHoldingTorch())
		{
			GameManager.torch.update(deltaTime);
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
	    GraphicsEnvironment.shader.useShader();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.graphics.setTitle("OurCraft | FPS: " + Gdx.graphics.getFramesPerSecond());

		if (GameManager.mainMenu)
		{
			drawMainMenu();

			return;
		}

		for (int viewNum = 0; viewNum < 1; viewNum++)
		{
            GraphicsEnvironment.setPerspectiveCamera();

			shader.setGlobalAmbience(LightManager.globalAmbiance);
			shader.setFogColor(LightManager.fogColor);

            GameManager.skybox.draw();

            GraphicsEnvironment.shader.setLight(LightManager.sun);
            GraphicsEnvironment.shader.setLight(LightManager.moon);

            if (GameManager.player.isHoldingTorch())
            {
                GameManager.torch.getLight().setOn(true);
                GraphicsEnvironment.shader.setLight(GameManager.torch.getLight());
            }
            else
            {
                GameManager.torch.getLight().setOn(false);
                GraphicsEnvironment.shader.setLight(GameManager.torch.getLight());
            }

//			GraphicsEnvironment.shader.setLight(LightManager.sun);
//
//            ModelMatrix.main.loadIdentityMatrix();
//            ModelMatrix.main.addTranslation(LightManager.sun.getPosition());
//            ModelMatrix.main.addScale(new Vector3D(5f, 5f, 5f));
//
//            GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
//
//            GraphicsEnvironment.shader.setMaterial(Settings.floorMaterial);
//
//            BoxGraphic.drawSolidCube(
//                    GraphicsEnvironment.shader,
//                    null,
//                    TextureManager.getCubeMapUVBuffer(),
//                    new CubeMask(),
//                    false
//            );
//
//            GraphicsEnvironment.shader.setLight(LightManager.moon);
//
//            ModelMatrix.main.loadIdentityMatrix();
//            ModelMatrix.main.addTranslation(LightManager.moon.getPosition());
//            ModelMatrix.main.addScale(new Vector3D(5f, 5f, 5f));
//
//            GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
//
//            GraphicsEnvironment.shader.setMaterial(Settings.spearMaterial);
//
//            BoxGraphic.drawSolidCube(
//                    GraphicsEnvironment.shader,
//                    null,
//                    TextureManager.getCubeMapUVBuffer(),
//                    new CubeMask(),
//                    false
//            );

            GameManager.drawCount = 0;

			GameManager.drawWorld();

			for (GameObject gameObject : GameManager.gameObjects)
			{
				gameObject.draw();
			}

			if (GameManager.player.isHoldingTorch())
            {
                GameManager.torch.draw();
            }

            ui();

			//System.out.print(" | Draw Count: " + GameManager.drawCount);
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

	private void ui()
    {
        GraphicsEnvironment.shader2D.useShader();

        GraphicsEnvironment.shader2D.OrthographicProjection2D(
            (int) GraphicsEnvironment.viewport.x,
            (int) GraphicsEnvironment.viewport.y,
            (int) GraphicsEnvironment.viewport.width,
            (int) GraphicsEnvironment.viewport.height
        );

        GraphicsEnvironment.shader2D.drawText(
            center,
            "|",
            Settings.crosshairColor,
            Enums.Fonts.ARIAL,
            Enums.Size.NORMAL
        );

        GraphicsEnvironment.shader2D.drawText(
            center,
            "---",
            Settings.crosshairColor,
            Enums.Fonts.ARIAL,
            Enums.Size.NORMAL
        );
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

        GameManager.player.getCamera().setPerspectiveProjection(Settings.playerFOV, GraphicsEnvironment.viewport.width / GraphicsEnvironment.viewport.height, Settings.nearPlane, Settings.farPlane);
        GraphicsEnvironment.UICamera.setPerspectiveProjection(Settings.playerFOV, GraphicsEnvironment.viewport.width / GraphicsEnvironment.viewport.height, 0.01f, 1f);

        setPoints();
	}

	private void setPoints()
    {
        center.setPoint(GraphicsEnvironment.viewport.width / 2, GraphicsEnvironment.viewport.height / 2);
    }

	private void init()
	{
		GraphicsEnvironment.init();
		GameManager.init();
		AudioManager.init();
		TextureManager.init();
		LightManager.init();
        initInput();

		shader = GraphicsEnvironment.shader;

		BoxGraphic.create();
		BoxHUDGraphic.create();
		SphereGraphic.create();
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

        center = new Point2D();

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
                        GameManager.player.getTargetBlock().destroy();
                    }
				}

				return false;
			}
        });
	}
}
