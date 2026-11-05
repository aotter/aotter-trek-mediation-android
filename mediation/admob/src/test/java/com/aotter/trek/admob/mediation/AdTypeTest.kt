package com.aotter.trek.admob.mediation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AdTypeTest {
    @Test
    fun adTypeOf_mapsServerPlacementTypes() {
        assertEquals("NATIVE", TrekAdmobDataKey.adTypeOf("NATIVE"))
        assertEquals("NATIVE_MULTIMEDIA", TrekAdmobDataKey.adTypeOf("SUPR_AD"))
        assertEquals("BANNER", TrekAdmobDataKey.adTypeOf("BANNER"))
        assertNull(TrekAdmobDataKey.adTypeOf("NATIVE_VIDEO"))
        assertNull(TrekAdmobDataKey.adTypeOf(""))
    }
}
