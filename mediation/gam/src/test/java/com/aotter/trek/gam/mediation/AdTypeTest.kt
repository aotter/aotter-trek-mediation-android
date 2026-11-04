package com.aotter.trek.gam.mediation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AdTypeTest {
    @Test
    fun adTypeOf_mapsServerPlacementTypes() {
        assertEquals("NATIVE", TrekGamDataKey.adTypeOf("NATIVE"))
        assertEquals("NATIVE_MULTIMEDIA", TrekGamDataKey.adTypeOf("SUPR_AD"))
        assertEquals("BANNER", TrekGamDataKey.adTypeOf("BANNER"))
        assertNull(TrekGamDataKey.adTypeOf("NATIVE_VIDEO"))
        assertNull(TrekGamDataKey.adTypeOf(""))
    }
}
