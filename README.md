# SkinChanger
### This is a proof of concept for changing the pixels of a skin dynamically in a spigot plugin.
### dependencies: [nicknamer api](https://github.com/jordoncodes/nicknamer-api)

### How to use:
1. Clone the repo and build using Gradle (`gradlew build`, or use intellij or similar)
2. A jarfile will be created in the `server/plugins` directory
3. Now, join the server and run `/mineskin` and your skin will be set to the skin in `plugins/ChangeSkin/skins/testSkin.png`
4. You can run `/alterskin <oldColor> <newColor>` and it replaces the old color with a new one.
5. Example: `/alterskin 0x0 0x00FF00` will replace all black pixels with green pixels.