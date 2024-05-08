package com.vk.usersapp.feature.feed.presentation

class UserListReducer {
    fun applyAction(action: UserListAction, state: UserListState): UserListState {
        return when (action) {
            UserListAction.Init -> state.copy(isLoading = true)
            is UserListAction.QueryChanged -> state.copy(query = action.query)
            is UserListAction.Users -> state.copy(items = action.users, isLoading = false)
            is UserListAction.Error -> state.copy(error = action.error, isLoading = false)
            is UserListAction.Loading -> state.copy(isLoading = true, error = null)
        }
    }
}