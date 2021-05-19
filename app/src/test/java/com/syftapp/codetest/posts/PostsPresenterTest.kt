package com.syftapp.codetest.posts

import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.rules.RxSchedulerRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostsPresenterTest {

    @get:Rule
    val rxRule = RxSchedulerRule()

    @MockK
    lateinit var getPostsUseCase: GetPostsUseCase
    @RelaxedMockK
    lateinit var view: PostsView

    private val anyPost = Post(1, 1, "title", "body")

    private val sut by lazy {
        PostsPresenter(getPostsUseCase)
    }

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `binding loads posts`() {
        every { getPostsUseCase.execute() } returns Single.just(listOf(anyPost))

        sut.bind(view)

        verifyOrder {
            view.render(any<PostScreenState.Loading>())
            view.render(any<PostScreenState.DataAvailable>())
            view.render(any<PostScreenState.FinishedLoading>())
        }
    }

    @Test
    fun `error on binding shows error state after loading`() {
        every { getPostsUseCase.execute() } returns Single.error(Throwable())

        sut.bind(view)

        verifyOrder {
            view.render(any<PostScreenState.Loading>())
            view.render(any<PostScreenState.Error>())
            view.render(any<PostScreenState.FinishedLoading>())
        }
    }
}