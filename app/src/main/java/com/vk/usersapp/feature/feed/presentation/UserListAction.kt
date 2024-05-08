package com.vk.usersapp.feature.feed.presentation

import com.vk.usersapp.feature.feed.model.User

sealed interface UserListAction {
    data object Init : UserListAction
    data class QueryChanged(val query: String) : UserListAction
    data class Users(val users: List<User>) : UserListAction
    data class Error(val error: String) : UserListAction
    data class Loading(val isLoading: Boolean) : UserListAction
}