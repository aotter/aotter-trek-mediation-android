package com.aotter.trek.gam.mediation.ads

import android.content.Context
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.trek.gam.mediation.BuildConfig
import com.aotter.trek.gam.mediation.extension.getVersion
import com.google.android.gms.ads.mediation.Adapter
import com.google.android.gms.ads.mediation.InitializationCompleteCallback
import com.google.android.gms.ads.mediation.MediationConfiguration
import com.google.android.gms.ads.mediation.VersionInfo
import org.json.JSONObject

abstract class TrekGamCustomEventBase : Adapter() {

    private var TAG: String = TrekGamCustomEventBase::class.java.simpleName

    companion object {
        private const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val CLIENT_ID = "clientId"
        private const val CLASS_NAME = "class_name"
    }

    private val adapters =
        listOf(
            TrekGamCustomEventNative::class.java.name,
            TrekGamCustomEventBanner::class.java.name
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
            Log.i("dsadsadsa", "${e.toString()}")
            initializationCompleteCallback.onInitializationFailed(e.toString())

        }

    }

}