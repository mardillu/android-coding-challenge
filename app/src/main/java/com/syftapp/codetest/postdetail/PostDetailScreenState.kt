package com.syftapp.codetest.postdetail

import com.syftapp.codetest.data.model.domain.Post

sealed class PostDetailScreenState {
    object Loading : PostDetailScreenState()
    class DataAvailable(val post: Post) : PostDetailScreenState()
    class Error(val error: Throwable) : PostDetailScreenState()
    object FinishedLoading : PostDetailScreenState()
}
