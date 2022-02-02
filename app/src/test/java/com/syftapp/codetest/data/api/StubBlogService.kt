package com.syftapp.codetest.data.api

import com.syftapp.codetest.data.model.api.*
import io.reactivex.Single

/**
 * Provides some dummy data that you would expect to get back from the [BlogService]
 */
class StubBlogService : BlogService {

    override fun getUsers(): Single<List<User>> {
        return Single.just(listOf(
            getUserWithId(1),
            getUserWithId(2)
        ))
    }

    override fun getComments(): Single<List<Comment>> {
        return Single.just(listOf(
            Comment(1, 1, "name 1", "email 1", "body 1"),
            Comment(1, 2, "name 1", "email 1", "body 2"),
            Comment(2, 3, "name 2", "email 2", "body 3")
        ))
    }

    override fun getPosts(page: Int, limit: Int): Single<List<Post>> {
        return Single.just(listOf(
            Post(1, 1, "title 1", "body 1"),
            Post(1, 2, "title 2", "body 2"),
            Post(2, 3, "title 3", "body 3"),
            Post(2, 4, "title 4", "body 4"),
            Post(2, 5, "title 5", "body 5")
        ))
    }

    private fun getUserWithId(id: Int): User {
        val stubCompany = Company("company", "catchphrase", "bs")
        val stubLatLng = LatLong("0", "0")
        val stubAddress = Address(
            "street",
            "suite",
            "city",
            "zipcode",
            stubLatLng
        )

        return User(
            id,
            "Ben Pearson",
            "benpearson",
            "fake@email1.com",
            stubAddress.copy(street = "street $id"),
            "123 123 123",
            "http://fake.site",
            stubCompany.copy(name = "company $id")
        )
    }
}