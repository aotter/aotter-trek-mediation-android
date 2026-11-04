package com.aotter.trek.max.mediation

import com.aotter.net.trek.ads.TrekAdError
import com.applovin.mediation.adapter.MaxAdapterError
import org.junit.Assert.assertSame
import org.junit.Test

class AdErrorCodeTest {
    @Test
    fun maxAdapterErrorOf_reportsTrekNoFillAsNoFill() {
        assertSame(MaxAdapterError.NO_FILL, TrekMaxDataKey.maxAdapterErrorOf(TrekAdError.NO_AD))
        assertSame(MaxAdapterError.INTERNAL_ERROR, TrekMaxDataKey.maxAdapterErrorOf(TrekAdError.UNKNOWN))
        assertSame(MaxAdapterError.INTERNAL_ERROR, TrekMaxDataKey.maxAdapterErrorOf(TrekAdError.NOT_INITIALIZED))
        assertSame(MaxAdapterError.INTERNAL_ERROR, TrekMaxDataKey.maxAdapterErrorOf(TrekAdError.AD_PLACE_REQUIRED))
    }
}
