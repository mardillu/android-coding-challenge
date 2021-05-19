package com.syftapp.codetest.posts

import com.syftapp.codetest.data.model.domain.Post

sealed class PostScreenState {
    object Loading : PostScreenState()
    class DataAvailable(val posts: List<Post>) : PostScreenState()
    class PostSelected(val post: Post) : PostScreenState()
    class Error(val error: Throwable) : PostScreenState()
    object FinishedLoading : PostScreenState()
}