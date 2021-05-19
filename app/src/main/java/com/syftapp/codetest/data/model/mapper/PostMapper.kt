package com.syftapp.codetest.data.model.mapper

import com.syftapp.codetest.data.model.domain.Post

object PostMapper : Mappable<com.syftapp.codetest.data.model.api.Post, Post> {

    override fun map(api: com.syftapp.codetest.data.model.api.Post): Post {
        return Post(
            id = api.id,
            userId = api.userId,
            title = api.title,
            body = api.body
        )
    }

}