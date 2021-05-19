package com.syftapp.codetest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syftapp.codetest.data.model.domain.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Single<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Int): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User): Completable
}