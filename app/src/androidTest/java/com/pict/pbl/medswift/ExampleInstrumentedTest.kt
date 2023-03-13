package com.pict.pbl.medswift

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.pict.pbl.medswift", appContext.packageName)
    }


    @Test
    fun checkSymptomsJsonParsing() {
        val reader = SymptomsJSONReader( InstrumentationRegistry.getInstrumentation().targetContext )
        val symptoms = reader.parseSymptoms()
        for( s in symptoms ) {
            println( s )
        }
    }

}