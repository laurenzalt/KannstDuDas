package at.fhj.kannstdudas.infrastructure.util

import android.content.Context
import javax.inject.Inject

/**
 * at.fhj.kannstdudas.infrastructure.util
 * Created by Noah Dimmer on 24/06/2024
 */

class ResourceProvider @Inject constructor(private val context: Context) {

    fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}
