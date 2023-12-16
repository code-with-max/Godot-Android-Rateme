// TODO: Update to match your plugin's package name.
package one.allme.android.rateme

import android.util.Log
import com.google.android.gms.common.ConnectionResult
//import android.widget.Toast
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot

class GodotAndroidPlugin(godot: Godot): GodotPlugin(godot) {

    companion object {
        val TAG: String = BuildConfig.GODOT_PLUGIN_NAME
    }

//    override fun getPluginName() = "GodotAndroidRateme"
    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME

    private val rateMeError = SignalInfo("error")
    private val rateMeCompleted = SignalInfo("completed")

    override fun getPluginSignals(): Set<SignalInfo> {
        Log.i(TAG, "Registering plugin signals")
        return setOf(
            rateMeError,
            rateMeCompleted,
        )
    }

    @UsedByGodot
    private fun show() {
//      Lets check Google play availability
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status: Int = googleApiAvailability.isGooglePlayServicesAvailable(activity!!)
        if (status != ConnectionResult.SUCCESS) {
            emitSignal(rateMeError.name, "GOOGLE_PLAY_UNAVAILABLE")
            Log.e(TAG, "GOOGLE_PLAY_UNAVAILABLE")
            return
        }

//      Run global rating if success
        val manager = ReviewManagerFactory.create(activity!!)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Got the Review object
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(activity!!, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                    emitSignal(rateMeCompleted.name)
                    Log.i(TAG, "GLOBAL_RATING_COMPLETED")
                }
            }
            else {
                // There was some problem
                emitSignal(rateMeError.name, "ERROR_DUE_GLOBAL_RATING")
                @ReviewErrorCode val reviewErrorCode = (task.exception as ReviewException).errorCode
                Log.e(TAG, "ERROR_DUE_GLOBAL_RATING")
                }
        }
//        runOnUiThread {
//            Toast.makeText(activity, "Global rating", Toast.LENGTH_LONG).show()
//            Log.v(pluginName, "Global Rating")
//        }
    }
}
