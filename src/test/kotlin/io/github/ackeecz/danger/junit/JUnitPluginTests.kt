package io.github.ackeecz.danger.junit

import com.google.common.truth.Truth
import org.junit.Test
import java.io.File

class JUnitPluginTests {

    @Test
    fun `should report failures`() {
        // before
        val dangerContext = FakeDangerContext()
        JUnitPlugin.context = dangerContext
        // when
        JUnitPlugin.parse(File(ClassLoader.getSystemResource("test_failures.xml").toURI()))
        JUnitPlugin.report()
        // then
        Truth.assertThat(dangerContext.markdowns)
            .isNotEmpty()
    }

    @Test
    fun `should not report success`() {
        // before
        val dangerContext = FakeDangerContext()
        JUnitPlugin.context = dangerContext
        // when
        JUnitPlugin.parse(File(ClassLoader.getSystemResource("test_success.xml").toURI()))
        JUnitPlugin.report()
        // then
        Truth.assertThat(dangerContext.markdowns)
            .isEmpty()
    }
}