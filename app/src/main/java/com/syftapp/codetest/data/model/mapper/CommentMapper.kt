package com.syftapp.codetest.data.model.mapper

import com.syftapp.codetest.data.model.domain.Comment

object CommentMapper : Mappable<com.syftapp.codetest.data.model.api.Comment, Comment> {

    override fun map(api: com.syftapp.codetest.data.model.api.Comment): Comment {
        return Comment(
            id = api.id,
            postId = api.postId,
            name = api.name,
            email = api.email,
            body = api.body
        )
    }

}