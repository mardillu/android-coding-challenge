package com.syftapp.codetest.postdetail

import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.rules.RxSchedulerRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Maybe
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDetailPresenterTest {

    @get:Rule
    val rxRule = RxSchedulerRule()

    @MockK
    lateinit var getPostUseCase: GetPostUseCase
    @RelaxedMockK
    lateinit var view: PostDetailView

    private val anyPost = Post(1, 1, "title", "body")

    private val sut by lazy {
        PostDetailPresenter(getPostUseCase)
    }

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `binding loads data`() {
        every { getPostUseCase.execute(1) } returns Maybe.just(anyPost)
        val slot = slot<PostDetailScreenState.DataAvailable>()

        sut.bind(view, 1)

        verifyOrder {
            view.render(any<PostDetailScreenState.Loading>())
            view.render(capture(slot))
            view.render(any<PostDetailScreenState.FinishedLoading>())
        }
        assertEquals(anyPost, slot.captured.post)
    }

    @Test
    fun `error while binding sets view state`() {
        `check error state displayed using value`(Maybe.error(Throwable()))
    }

    @Test
    fun `empty value while binding sets view state`() {
        `check error state displayed using value`(Maybe.empty())
    }

    private fun `check error state displayed using value`(value: Maybe<Post>) {
        every { getPostUseCase.execute(1) } returns value

        sut.bind(view, 1)

        verifyOrder {
            view.render(any<PostDetailScreenState.Loading>())
            view.render(any<PostDetailScreenState.Error>())
            view.render(any<PostDetailScreenState.FinishedLoading>())
        }
    }
}