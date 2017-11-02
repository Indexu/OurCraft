package com.ru.tgra.ourcraft;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.objects.GameObject;
import com.ru.tgra.ourcraft.shapes.*;
import com.ru.tgra.ourcraft.utilities.CollisionsUtil;

public class OurCraftGame extends ApplicationAdapter
{
	private float oldMouseX;
	private float oldMouseY;

	private Point2D center;
	private Point2D blockSelected;

    private Point2D mainMenuTitlePosition;
    private Point2D mainMenuMovePosition;
    private Point2D mainMenuMousePosition;
    private Point2D mainMenuLeftClickPosition;
    private Point2D mainMenuRightClickPosition;
    private Point2D mainMenuSelectPosition;
    private Point2D mainMenuWaitPosition;
    private Point2D mainMenuProgressBarPosition;
    private Point2D mainMenuProgressTextPosition;

    private String waitText;
    private String progressBar;
    private String progressText;

    private int step;

	@Override
	public void create ()
	{
		init();
	}

	private void mainMenuInput()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && GameManager.loaded)
		{
			GameManager.mainMenu = false;
            GameManager.createWorld();
		}
	}

	private void mainMenuCreateWorld()
    {
        switch (step)
        {
            case 0:
                GameManager.worldGenerator.generateOverworldHeightMap();
                progressText = "Smoothening heightmap";
                break;

            case 1:
                GameManager.worldGenerator.smoothenOverworldHeightMap();
                progressText = "Creating world block 3D array";
                break;

            case 2:
                GameManager.worldGenerator.createWorldBlockArray();
                progressText = "Offsetting overworld";
                break;

            case 3:
                GameManager.worldGenerator.offsetOverWorld();
                progressText = "Creating stone blocks";
                break;

            case 4:
                GameManager.worldGenerator.createStone();
                progressText = "Creating caverns";
                break;

            case 5:
                GameManager.worldGenerator.createCaverns();
                progressText = "Creating bedrock blocks";
                break;

            case 6:
                GameManager.worldGenerator.createBedrock();
                progressText = "Generating chunks";
                break;

            case 7:
                GameManager.worldGenerator.generateChunks();
                waitText = "Loading done";
                progressText = "Press ENTER to start";
                GameManager.loaded = true;
                break;

            default:
                return;
        }

        step++;

        updateProgressBar();
    }

    private void updateProgressBar()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        int i;

        for (i = 0; i < step*2; i++)
        {
            stringBuilder.append("#");
        }

        for (; i < 16; i++)
        {
            stringBuilder.append(" ");
        }

        stringBuilder.append("]");

        progressBar = stringBuilder.toString();
    }

	private void input()
	{
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY() - Gdx.graphics.getHeight();

		if (GameManager.mainMenu)
		{
			mainMenuInput();
            mainMenuCreateWorld();

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1))
        {
            GameManager.player.selectBlock(1);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2))
        {
            GameManager.player.selectBlock(2);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

	    if (GameManager.mainMenu)
        {
            drawMainMenu();
            return;
        }

	    GraphicsEnvironment.shader.useShader();

		Gdx.graphics.setTitle("OurCraft | FPS: " + Gdx.graphics.getFramesPerSecond());

		if (GameManager.mainMenu)
		{
			drawMainMenu();

			return;
		}

		for (int viewNum = 0; viewNum < 1; viewNum++)
		{
            GraphicsEnvironment.setPerspectiveCamera();

            GraphicsEnvironment.shader.setGlobalAmbience(LightManager.globalAmbiance);
            GraphicsEnvironment.shader.setFogColor(LightManager.fogColor);

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

        GraphicsEnvironment.shader2D.drawText(
            blockSelected,
            "Block: " + GameManager.player.getSelectedBlock().toString(),
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.NORMAL
        );
    }

    private void drawMainMenu()
    {
        GraphicsEnvironment.shader2D.useShader();

        GraphicsEnvironment.shader2D.OrthographicProjection2D(
                (int) GraphicsEnvironment.viewport.x,
                (int) GraphicsEnvironment.viewport.y,
                (int) GraphicsEnvironment.viewport.width,
                (int) GraphicsEnvironment.viewport.height
        );

        GraphicsEnvironment.shader2D.drawText(
                mainMenuTitlePosition,
                "OurCraft",
                Settings.textColor,
                Enums.Fonts.MINECRAFTER,
                Enums.Size.EXTRA_LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuMovePosition,
            "Use WASD to move",
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuMousePosition,
            "Use the mouse to look around",
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
                mainMenuLeftClickPosition,
            "Left click to destroy a block",
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuRightClickPosition,
            "Right click to place a block",
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuSelectPosition,
            "Number keys to select which block type to place",
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuWaitPosition,
            waitText,
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.NORMAL
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuProgressBarPosition,
            progressBar,
            Settings.textColor,
            Enums.Fonts.ROBOTO,
            Enums.Size.LARGE
        );

        GraphicsEnvironment.shader2D.drawText(
            mainMenuProgressTextPosition,
            progressText,
            Settings.textColor,
            Enums.Fonts.ROBOTO,
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

        if (GameManager.player != null)
        {
            GameManager.player.getCamera().setPerspectiveProjection(Settings.playerFOV, GraphicsEnvironment.viewport.width / GraphicsEnvironment.viewport.height, Settings.nearPlane, Settings.farPlane);
        }

        if (GraphicsEnvironment.UICamera != null)
        {
            GraphicsEnvironment.UICamera.setPerspectiveProjection(Settings.playerFOV, GraphicsEnvironment.viewport.width / GraphicsEnvironment.viewport.height, 0.01f, 1f);
        }
	}

	private void init()
	{
		GraphicsEnvironment.init();
		GameManager.init();
		AudioManager.init();
		TextureManager.init();
		LightManager.init();
        initInput();
        initPoints();

		BoxGraphic.create();
		BoxHUDGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

		oldMouseX = Gdx.input.getX();
		oldMouseY = Gdx.input.getY() - Gdx.graphics.getHeight();

		waitText = "Please wait while the game loads";
		progressText = "Generating heightmap";

        step = 0;

        updateProgressBar();
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

                if (button == Input.Buttons.RIGHT)
                {
                    GameManager.player.placeBlock();
                }

				return false;
			}
        });
	}

	private void initPoints()
    {
        float offsetSmall = Gdx.graphics.getHeight() / 12;
        float offsetLarge = Gdx.graphics.getHeight() / 8;

        center = new Point2D(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        blockSelected = new Point2D(Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 16);

        mainMenuTitlePosition = new Point2D(center);
        mainMenuTitlePosition.y += offsetLarge * 3f;

        mainMenuMovePosition = new Point2D(mainMenuTitlePosition);
        mainMenuMovePosition.y -= offsetLarge;

        mainMenuMousePosition = new Point2D(mainMenuMovePosition);
        mainMenuMousePosition.y -= offsetSmall;

        mainMenuLeftClickPosition = new Point2D(mainMenuMousePosition);
        mainMenuLeftClickPosition.y -= offsetSmall;

        mainMenuRightClickPosition = new Point2D(mainMenuLeftClickPosition);
        mainMenuRightClickPosition.y -= offsetSmall;

        mainMenuSelectPosition = new Point2D(mainMenuRightClickPosition);
        mainMenuSelectPosition.y -= offsetSmall;

        mainMenuWaitPosition = new Point2D(mainMenuSelectPosition);
        mainMenuWaitPosition.y -= offsetLarge;

        mainMenuProgressBarPosition = new Point2D(mainMenuWaitPosition);
        mainMenuProgressBarPosition.y -= offsetSmall;

        mainMenuProgressTextPosition = new Point2D(mainMenuProgressBarPosition);
        mainMenuProgressTextPosition.y -= offsetSmall;
    }
}
