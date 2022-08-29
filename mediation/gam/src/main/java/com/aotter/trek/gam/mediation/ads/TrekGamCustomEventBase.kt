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

    companion object{
        const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        const val SERVER_PARAMETER = "parameter"
        const val PLACE_UID = "placeUid"
        const val CLIENT_ID = "clientId"
    }

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

    }

}