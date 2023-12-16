// TODO: Update to match your plugin's package name.
package one.allme.android.rateme

import android.util.Log
import android.widget.Toast
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot

class GodotAndroidPlugin(godot: Godot): GodotPlugin(godot) {

//    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME
    override fun getPluginName() = "GodotAndroidRateme"

    @UsedByGodot
    private fun show() {
        runOnUiThread {
            Toast.makeText(activity, "Hello World", Toast.LENGTH_LONG).show()
            Log.v(pluginName, "Hello World")
        }
    }
}
