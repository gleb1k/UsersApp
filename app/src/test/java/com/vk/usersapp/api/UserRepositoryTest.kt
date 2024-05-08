package com.vk.usersapp.api

import com.vk.usersapp.feature.feed.api.UsersApi
import com.vk.usersapp.feature.feed.api.UsersRepositoryImpl
import com.vk.usersapp.feature.feed.model.User
import com.vk.usersapp.feature.feed.model.UsersResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    @MockK
    private lateinit var usersApi: UsersApi

    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        usersRepositoryImpl = UsersRepositoryImpl(usersApi)
    }

    @Test
    fun whenGetUsersExpectedSuccess() {
        val expectedData: List<User> = mockk()

        coEvery {
            usersApi.getUsers(
                limit = 30,
                skip = 0
            )
        } returns UsersResponse(expectedData, 30, 0, 30)

        runTest {
            val result = usersRepositoryImpl.getUsers()

            assertEquals(expectedData, result)
        }
    }

    //как отловить кейс с отключенным интернетом?
    @Test
    fun whenGetUsersExpectedFail() {
        val expectedData: List<User> = listOf()

        coEvery {
            usersApi.getUsers(
                limit = 30,
                skip = 0
            )
        } returns UsersResponse(expectedData, 30, 0, 30)

        runTest {
            val result = usersRepositoryImpl.getUsers()

            assertEquals(expectedData, result)
        }
    }

    @Test
    fun whenSearchUsersExpectedSuccess() {
        val querySearch = GOOD_SEARCH_QUERY
        val expectedData: List<User> = mockk()

        coEvery {
            usersApi.searchUsers(
                query = querySearch,
                limit = 30,
                skip = 0
            )
        } returns UsersResponse(expectedData, 30, 0, 30)

        runTest {
            val result = usersRepositoryImpl.searchUsers(querySearch)

            assertEquals(expectedData, result)
        }
    }

    @Test
    fun whenSearchUsersExpectedFail() {
        val querySearch = BAD_SEARCH_QUERY
        val expectedData: List<User> = listOf()

        coEvery {
            usersApi.searchUsers(
                query = querySearch,
                limit = 30,
                skip = 0
            )
        } returns UsersResponse(expectedData, 30, 0, 30)

        runTest {
            val result = usersRepositoryImpl.searchUsers(querySearch)

            assertEquals(expectedData, result)
        }
    }

    companion object {
        const val GOOD_SEARCH_QUERY = "bob"
        const val BAD_SEARCH_QUERY = "123234324233"
    }

}