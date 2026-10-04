# CestClient

CestClient is a Minecraft hack client, open-source and having a bunch of features :)

## Requirements

- **Minecraft 26.2** (Fabric)
- **Fabric Loader 0.19.5 or newer**
- **Java 25**

> Minecraft switched to calendar versioning after 1.21 (`26.1`, `26.2`, ...), and from
> 26.1 onward the game ships unobfuscated. Fabric therefore dropped Yarn, so this mod
> is built against **Mojang's official mappings** (no `mappings` line in `build.gradle`).

## Building

```bash
JAVA_HOME=/path/to/jdk-25 ./gradlew build
```

The remapped mod jar lands in `build/libs/` and can be dropped into your `mods/` folder.

## Usage

You can use this hack client by...

Putting it in your mod folder you dingle berry..

## Menu

Open the ClickGUI with the **Right Shift** key (or run `@clickmenu`).

- **Left-click** a module to toggle it on or off.
- **Right-click** a module, then press a key, to bind it to a hotkey (press ESC to clear).
- **Drag** a category header to move the panel around.
- **ESC** closes the menu. The game keeps running while it is open.

The HUD in the top-left shows the watermark and every enabled module. The debug
overlay adds FPS, coordinates and more when enabled with `@debug`.

## Commands

Commands are typed into the chat with the `@` prefix. They are executed on your
client only - they are **never sent to the server**, so other players never see
them and they do not appear in your chat history.

| Command | Description |
| --- | --- |
| `@enable <module>` | Enables a module |
| `@disable <module>` | Disables a module |
| `@clickmenu` | Opens the ClickGUI |
| `@panic` | Disables every enabled module |
| `@debug` | Toggles the debug HUD overlay |
| `@help` | Lists every command |

Available modules: `Flight`, `AutoSprint`, `Fullbright`.

## Keybinds

| Key | Action |
| --- | --- |
| Right Shift | Open the ClickGUI |
| Delete | Panic (disable every module) |

Per-module hotkeys can be bound from the ClickGUI.

## FAQ

#### Something isn't working...

Open an issue request and I'll get back to you when I can.

#### Can I use this in my own project?

Sure go ahead...
