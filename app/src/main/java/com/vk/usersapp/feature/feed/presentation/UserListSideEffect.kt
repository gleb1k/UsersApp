package com.vk.usersapp.feature.feed.presentation

sealed interface UserListSideEffect {
    data object LoadAllUsers : UserListSideEffect
    data class SearchUsers(val query: String) : UserListSideEffect
}