package com.syftapp.codetest.data.repository

import com.syftapp.codetest.data.api.BlogApi
import com.syftapp.codetest.data.dao.CommentDao
import com.syftapp.codetest.data.dao.PostDao
import com.syftapp.codetest.data.dao.UserDao
import com.syftapp.codetest.data.model.domain.Comment
import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.data.model.domain.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.koin.core.KoinComponent

class BlogRepository(
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val userDao: UserDao,
    private val blogApi: BlogApi
) : KoinComponent, BlogDataProvider {

    override fun getUsers(): Single<List<User>> {
        return fetchData(
            local = { userDao.getAll() },
            remote = { blogApi.getUsers() },
            insert = { value -> userDao.insertAll(*value.toTypedArray()) }
        )
    }

    override fun getComments(): Single<List<Comment>> {
        return fetchData(
            local = { commentDao.getAll() },
            remote = { blogApi.getComments() },
            insert = { value -> commentDao.insertAll(*value.toTypedArray()) }
        )
    }

    override fun getPosts(page: Int, skipCache: Boolean): Single<List<Post>> {
        return fetchData(
            local = { postDao.getAll() },
            remote = { blogApi.getPosts(page) },
            insert = { value -> postDao.insertAll(*value.toTypedArray()) },
            skipCache = skipCache,
        )
    }

    fun getPost(postId: Int): Maybe<Post> {
        return postDao.get(postId)
    }

    /**
     * This function can be enhanced.
     * It is possible for posts to get modified on the server. When this happens, we should also let
     * that update reflect on the mobile. This implies that even if skipCache is set to false, we should still
     * make a request to the server just in case the cache is outdated. So we could hold on to the cache
     * in the meantime while we also check the server for updates
     */
    private fun <T> fetchData(
        local: () -> Single<List<T>>,
        remote: () -> Single<List<T>>,
        insert: (insertValue: List<T>) -> Completable,
        skipCache: Boolean = false,
    ): Single<List<T>> {

        return local.invoke()
            .flatMap {
                if (skipCache){
                    remote.invoke()
                        .map { value ->
                            insert.invoke(value).subscribe();
                            value
                        }
                } else {
                    if (it.isNotEmpty()) {
                        Single.just(it)
                    } else {
                        remote.invoke()
                            .map { value ->
                                insert.invoke(value).subscribe();
                                value
                            }
                    }
                }
            }
    }
}
