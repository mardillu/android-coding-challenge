package com.syftapp.codetest.data.model.mapper

import com.syftapp.codetest.data.model.domain.User

object UserMapper : Mappable<com.syftapp.codetest.data.model.api.User, User> {

    override fun map(api: com.syftapp.codetest.data.model.api.User): User {
        return User(
            id = api.id,
            name = api.name,
            username = api.username,
            email = api.email
        )
    }

}