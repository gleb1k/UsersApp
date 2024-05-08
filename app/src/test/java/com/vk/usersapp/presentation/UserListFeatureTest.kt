package com.vk.usersapp.presentation

import com.vk.usersapp.MainDispatcherRule
import com.vk.usersapp.feature.feed.api.UsersRepositoryImpl
import com.vk.usersapp.feature.feed.model.User
import com.vk.usersapp.feature.feed.presentation.UserListAction
import com.vk.usersapp.feature.feed.presentation.UserListFeature
import com.vk.usersapp.feature.feed.presentation.UserListReducer
import com.vk.usersapp.feature.feed.presentation.UserListViewState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserListFeatureTest {

    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    private lateinit var userListFeature: UserListFeature

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userListFeature = UserListFeature(
            ioDispatcher = UnconfinedTestDispatcher(),
            defaultDispatcher = UnconfinedTestDispatcher(),
            reducer = UserListReducer(),
            usersRepositoryImpl = usersRepositoryImpl
        )
    }

    @Test
    fun whenLoadUserExpectedSuccess() {
        val expectedData: List<User> = mockk()

        runTest {
            userListFeature.submitAction(UserListAction.Init)
            val actualData =
                (userListFeature.viewStateFlow.value as UserListViewState.List).itemsList
            assertEquals(expectedData, actualData)
        }
    }

    @Test
    fun whenLoadUserExpectedFail() {

    }

    @Test
    fun whenSearchUsersExpectedSuccess() {

    }

    @Test
    fun whenSearchUsersExpectedFail() {

    }

}