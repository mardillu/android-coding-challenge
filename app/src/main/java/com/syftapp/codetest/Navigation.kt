package com.syftapp.codetest

import android.content.Context
import com.syftapp.codetest.postdetail.PostDetailActivity

class Navigation(private val context: Context) {

    fun navigateToPostDetail(postId: Int) {
        context.startActivity(PostDetailActivity.getActivityIntent(context, postId))
    }

}

