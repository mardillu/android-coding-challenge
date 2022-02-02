package com.syftapp.codetest.posts

import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.util.DOWNLOAD_PAGE_SIZE
import com.syftapp.codetest.util.NUM_POSTS_TO_DOWNLOAD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

class PostsPresenter(private val getPostsUseCase: GetPostsUseCase) : KoinComponent {

    private var currentPage = 1
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: PostsView

    fun bind(view: PostsView) {
        this.view = view
        compositeDisposable.add(loadPosts())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun showDetails(post: Post) {
        view.render(PostScreenState.PostSelected(post))
    }

    private fun loadPosts(page: Int = 1, skipCache: Boolean = false) = getPostsUseCase.execute(page, skipCache)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.render(PostScreenState.Loading) }
        .doAfterTerminate { view.render(PostScreenState.FinishedLoading) }
        .subscribe(
            { view.render(PostScreenState.DataAvailable(it)) },
            { view.render(PostScreenState.Error(it)) }
        )


    /**
     * Loads the next page of the posts from network, if you choose to skip the cache
     * The assumption here is that there are exactly 100 posts on the server.
     *
     * Sadly, if there are more than 100 posts, we cannot get the rest
     * If on the other hand, there are less than 100 posts, say 10, for example
     * We will end up paging several times unnecessarily.
     *
     * Perhaps, instead of an array, the endpoint could return a JSON object that contains a posts
     * array and a field that tells us how many post are remaining on the server
     */
    fun loadMorePosts() {
        val isLastPage = currentPage * DOWNLOAD_PAGE_SIZE >= NUM_POSTS_TO_DOWNLOAD
        if (isLastPage)
            return
        loadPosts(currentPage++, true)
    }
}