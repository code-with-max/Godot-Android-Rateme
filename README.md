# Google Play In-App Reviews plugin for Godot 4.2+

The Google Play In-App Review plugin lets you prompt users to submit Play Store ratings and reviews without the inconvenience of leaving your app or game.

![Example 1](/assets/rew_screen_01.png) ![Example 2](/assets/rew_screen_02.png)

## Preparation for use

- Go to ![Addon release page](/releases)
- Download the version of the add-on according to the required version of Godot
- Unpack archive in /addons directory inside your project
- Goto: Progect -> Project Settings... -> Plugins
- Turn on the plugin:\
![turn on the plugin](/assets/page_plugin.png)

- Make sure you use Gradle Build in export presets:\
![Use Gradle Build](/assets/page_export.png)

- Plugin ready to go

## Usage

- Initialize the plugin:

```gdscript
# RateMe singleton
var _rate_me_addon


# Called when the node enters the scene tree for the first time.
func _ready():
 # RateMe init
 if Engine.has_singleton("GodotAndroidRateme"):
  # Get plugin
  _rate_me_addon = Engine.get_singleton("GodotAndroidRateme")
  # Connect signals (optional)
  _rate_me_addon.completed.connect(self._on_rateme_completed)
  _rate_me_addon.error.connect(self._on_rateme_error) # use err as string
 else:
  printerr("Couldn't find RateMe singleton")
```

- Call show() func at any time you need:

```gdscript
if _rate_me_addon:
  _rate_me_addon.show()
```
