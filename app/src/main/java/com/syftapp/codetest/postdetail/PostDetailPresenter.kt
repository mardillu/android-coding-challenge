package com.syftapp.codetest.postdetail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

class PostDetailPresenter(private val getPostUseCase: GetPostUseCase) : KoinComponent {

    private val disposable = CompositeDisposable()

    private lateinit var view: PostDetailView

    fun bind(view: PostDetailView, postId: Int) {
        this.view = view
        disposable.add(loadPost(postId))
    }

    fun unbind() {
        if (!disposable.isDisposed) disposable.clear()
    }

    private fun loadPost(postId: Int) = getPostUseCase.execute(postId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.render(PostDetailScreenState.Loading) }
        .doAfterTerminate { view.render(PostDetailScreenState.FinishedLoading) }
        .doOnError { view.render(PostDetailScreenState.Error(it)) }
        .toSingle()
        .subscribe({
            view.render(PostDetailScreenState.DataAvailable(it))
        }, {
            view.render(PostDetailScreenState.Error(it))
        })
}
