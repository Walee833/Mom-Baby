package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.viewmodel.MomBabyViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Mom & Baby", appName)
    }

    @Test
    fun `test pregnancy week calculation and kick counter`() {
        val viewModel = MomBabyViewModel()
        val weekInfo = viewModel.getWeekInfo(24)
        assertEquals(24, weekInfo.week)
        assertEquals("Corn", weekInfo.fruitOrVeggie)
        assertEquals(2, weekInfo.trimester)

        val initialKicks = viewModel.kickCount.value
        viewModel.incrementKick()
        assertEquals(initialKicks + 1, viewModel.kickCount.value)

        viewModel.resetKicks()
        assertEquals(0, viewModel.kickCount.value)
    }

    @Test
    fun `test hydration water tracking`() {
        val viewModel = MomBabyViewModel()
        val initialWater = viewModel.waterGlasses.value
        viewModel.incrementWater()
        assertEquals(initialWater + 1, viewModel.waterGlasses.value)

        viewModel.decrementWater()
        assertEquals(initialWater, viewModel.waterGlasses.value)
    }
}
