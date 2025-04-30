package com.example.pawconnect.repository

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

object NotificationPreferences {

    private const val PREFS_NAME = "notification_prefs"
    private const val KEY_SEGUIMIENTO_SOLICITUDES = "seguimiento_solicitudes"
    private const val KEY_AVISO_NUEVAS_MASCOTAS = "aviso_nuevas_mascotas"
    private const val KEY_INFO_MASCOTAS = "info_mascotas"
    private const val KEY_ENVIO_ENCUESTA = "envio_encuesta"
    private const val KEY_ACTUALIZACIONES_DISPONIBLES = "actualizaciones_disponibles"

    private fun getSharedPreferences(context: Context) = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveNotificationSettings(
        context: Context,
        seguimientoSolicitudes: Boolean,
        avisoNuevasMascotas: Boolean,
        infoMascotas: Boolean,
        envioEncuesta: Boolean,
        actualizacionesDisponibles: Boolean
    ) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_SEGUIMIENTO_SOLICITUDES, seguimientoSolicitudes)
        editor.putBoolean(KEY_AVISO_NUEVAS_MASCOTAS, avisoNuevasMascotas)
        editor.putBoolean(KEY_INFO_MASCOTAS, infoMascotas)
        editor.putBoolean(KEY_ENVIO_ENCUESTA, envioEncuesta)
        editor.putBoolean(KEY_ACTUALIZACIONES_DISPONIBLES, actualizacionesDisponibles)
        editor.apply()
    }

    fun loadNotificationSettings(context: Context): NotificationSettings {
        val prefs = getSharedPreferences(context)
        return NotificationSettings(
            seguimientoSolicitudes = prefs.getBoolean(KEY_SEGUIMIENTO_SOLICITUDES, false),
            avisoNuevasMascotas = prefs.getBoolean(KEY_AVISO_NUEVAS_MASCOTAS, false),
            infoMascotas = prefs.getBoolean(KEY_INFO_MASCOTAS, false),
            envioEncuesta = prefs.getBoolean(KEY_ENVIO_ENCUESTA, false),
            actualizacionesDisponibles = prefs.getBoolean(KEY_ACTUALIZACIONES_DISPONIBLES, false)
        )
    }

    data class NotificationSettings(
        val seguimientoSolicitudes: Boolean,
        val avisoNuevasMascotas: Boolean,
        val infoMascotas: Boolean,
        val envioEncuesta: Boolean,
        val actualizacionesDisponibles: Boolean
    )
}
