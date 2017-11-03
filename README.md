# OurCraft

An OpenGL MineCraft clone written in Java and LibGDX.

## Features

* Large generated worlds
* Caves
* Fog
* Placing blocks
* Destroying blocks
* Noclip
* Sprinting
* Torch holding
* Skybox
* Day night cycle
* Music and SFX
* Main menu with a loading progress bar
* Settings file
    * Change it to however you wish (whackyness may ensue)

## Getting Started

In order to run OurCraft on your own machine, you will need to compile the source code.

You will have to set the main class to: **src/desktop/src/com.ru.tgra.ourcraft.desktop.DesktopLauncher**.

At this point you can compile the game, but not run it without crashing. You also have to set the working directory to the **src/core/assets** directory.

### Prerequisites

You will need Java 8 in order to compile the game.

## Controls

Use WASD to move around, SHIFT to sprint.

The mouse (or arrow keys) to look around.

Left click destroys a targeted block while right click places a block.

To select a block to place, use the number/numpad keys or scroll wheel.

Press T to toggle the torch.

Press N to toggle noclip mode.

The controls are also displayed on the main menu of the game.

### Development

We used JetBrains IntelliJ Idea as our IDE of choice coding this game.

The game uses OpenGL and we use LibGDX in order to access OpenGL functionality.

## Built With

* [IntelliJ Idea](https://www.jetbrains.com/idea/) - IDE
* [LibGDX](https://libgdx.badlogicgames.com/) - OpenGL library
* [Java](https://www.java.com/en/) - Language

## Authors

* [Christian A. Jacobsen](https://github.com/ChristianJacobsen/)
* [Hilmar Tryggvason](https://github.com/Indexu/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Special thanks to Kári Halldórsson for guiding us throughout this project and teaching us OpenGL.
* [Kurt Spencer](https://gist.github.com/KdotJPG/b1270127455a94ac5d19) - Open Simplex Noise Java implementation
* [Soy_yuma](http://acamara.es/blog/2012/02/keep-screen-aspect-ratio-with-different-resolutions-using-libgdx/) - Aspect ratio fix

### Audio

* [Gabriele Tosi](https://www.youtube.com/watch?v=MQWpXS_hJgA) - Relaxing piano music
* [FreeSound](https://freesound.org/) - Sound effects
    * [Destroy block](https://freesound.org/people/Chance4doom/sounds/394213/)
    * [Place block](https://freesound.org/people/IanStarGem/sounds/274775/)

### Fonts

* [MadPixel](https://www.dafont.com/minecrafter.font) - Minecrafter

### Textures

* [HighZpec](https://www.planetminecraft.com/texture_pack/111-default-upgrade-16x/) - Block textures
* [BZFusion](http://www.bzfusion.net/skymaps/sky_photo3.jpg) - Skysphere texture