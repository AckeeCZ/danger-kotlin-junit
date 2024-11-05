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
        Truth.assertThat(dangerContext.markdowns.first().toString())
            .isEqualTo(
                """
                    Violation(message=### Tests: 
    
                    #### cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest
                    
                    | File | Name |
                    | ---- | ---- |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail with topics section of max topics and no expanded button |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail without similar articles |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail with similar articles |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | display not downloaded info text when saved article and article dont have text |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail with topics section expanded |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail without image |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail with topics section and expanded button |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail without topics section |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should show article detail with topics section of less than max topics and no expanded button |
                    | cz.flashsport.saved.screens.articledetail.detail.ArticleDetailControllerTest | should handle empty message and type parameters |
                    
                    
                    , file=null, line=null)
                """.trimIndent()
            )
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