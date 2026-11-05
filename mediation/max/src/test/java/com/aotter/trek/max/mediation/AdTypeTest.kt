package com.aotter.trek.max.mediation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AdTypeTest {
    @Test
    fun adTypeOf_mapsServerPlacementTypes() {
        assertEquals("NATIVE", TrekMaxDataKey.adTypeOf("NATIVE"))
        assertEquals("NATIVE_MULTIMEDIA", TrekMaxDataKey.adTypeOf("SUPR_AD"))
        assertEquals("BANNER", TrekMaxDataKey.adTypeOf("BANNER"))
        assertNull(TrekMaxDataKey.adTypeOf("NATIVE_VIDEO"))
        assertNull(TrekMaxDataKey.adTypeOf(""))
    }
}
