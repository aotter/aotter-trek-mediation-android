package com.aotter.trek.admob.mediation.ads

import android.content.Context
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.trek.admob.mediation.BuildConfig
import com.aotter.trek.admob.mediation.extension.getVersion
import com.google.android.gms.ads.mediation.Adapter
import com.google.android.gms.ads.mediation.InitializationCompleteCallback
import com.google.android.gms.ads.mediation.MediationConfiguration
import com.google.android.gms.ads.mediation.VersionInfo
import org.json.JSONObject

abstract class TrekAdmobCustomEventBase : Adapter() {

    private var TAG: String = TrekAdmobCustomEventBase::class.java.simpleName

    companion object {
        private const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val CLIENT_ID = "clientId"
        private const val CLASS_NAME = "class_name"
    }

    private val adapters =
        listOf(
            TrekAdmobCustomEventNative::class.java.name,
            TrekAdmobCustomEventBanner::class.java.name
        )

    override fun getSDKVersionInfo(): VersionInfo {

        return BuildConfig.MEDIATION_VERSION.getVersion()

    }

    override fun getVersionInfo(): VersionInfo {

        return BuildConfig.MEDIATION_VERSION.getVersion()

    }

    override fun initialize(
        context: Context,
        initializationCompleteCallback: InitializationCompleteCallback,
        mediationConfigurations: MutableList<MediationConfiguration>
    ) {

        try {

            mediationConfigurations.forEach { mediationConfiguration ->

                val serverParameters = mediationConfiguration.serverParameters

                val adapterClass = serverParameters.getString(CLASS_NAME) ?: ""

                if (adapterClass in adapters) {

                    val serverParameter = JSONObject(
                        mediationConfiguration.serverParameters.getString(
                            SERVER_PARAMETER
                        ) ?: ""
                    )

                    val clientId = serverParameter.getString(CLIENT_ID)

                    if (clientId.isNullOrEmpty()) {
                        throw IllegalArgumentException(NEED_CLIENT_ID_TAG)
                    }

                    Log.i(TAG, "clientId : $clientId")

                    TrekAds.initialize(
                        context.applicationContext,
                        clientId
                    ) {
                        initializationCompleteCallback.onInitializationSucceeded()
                    }

                }

            }

        } catch (e: Exception) {

            initializationCompleteCallback.onInitializationFailed(e.toString())

        }

    }

}