package com.edge.inappupdateSample

import androidx.appcompat.widget.AppCompatButton
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var fakeAppUpdateManager: FakeAppUpdateManager

    @Before
    fun setup() {
        val appContext = androidx.test.InstrumentationRegistry.getTargetContext()
        fakeAppUpdateManager = FakeAppUpdateManager(appContext)
    }

    @Test
    fun testFlexibleUpdate_Completes() {
        // Setup flexible update.
        fakeAppUpdateManager.partiallyAllowedUpdateType = AppUpdateType.FLEXIBLE
        fakeAppUpdateManager.setUpdateAvailable(2)

        ActivityScenario.launch(FlexibleUpdateActivity::class.java)

        // Validate that flexible update is prompted to the user.
        assertTrue(fakeAppUpdateManager.isConfirmationDialogVisible)

        // Simulate user's and download behavior.
        fakeAppUpdateManager.userAcceptsUpdate()

        fakeAppUpdateManager.downloadStarts()

        fakeAppUpdateManager.downloadCompletes()



        // Validate that update is completed and app is restarted.
        assertTrue(fakeAppUpdateManager.isInstallSplashScreenVisible)

        fakeAppUpdateManager.installCompletes()
    }
}
