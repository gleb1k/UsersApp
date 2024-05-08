package com.vk.usersapp.feature.feed.presentation

import androidx.lifecycle.viewModelScope
import com.vk.usersapp.core.mvi.BaseViewModel
import com.vk.usersapp.core.mvi.MVIFeature
import com.vk.usersapp.core.utils.CoroutineDispatchers
import com.vk.usersapp.feature.feed.api.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

// MVI:
//         Action                 patch, state                  newState                            viewState
// View ------------> Feature -----------------> reducer ------------------------> Feature --------------------------> view
//          ^            |
//          |            |
//          | Action     |  sideEffect
//          |            |
//          |            v
//          |-------- Feature

class UserListFeature(
    private val dispatchers: CoroutineDispatchers,
    private val reducer: UserListReducer,
    private val usersRepository: UsersRepository
) : MVIFeature, BaseViewModel() {

    private val mutableViewStateFlow =
        MutableStateFlow<UserListViewState>(UserListViewState.Loading)
    val viewStateFlow: StateFlow<UserListViewState>
        get() = mutableViewStateFlow.asStateFlow()

    private var state: UserListState = UserListState()

    private val searchQueryPublisher = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        listenToSearchQuery()
        submitAction(UserListAction.Init)
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun listenToSearchQuery() {
        searchQueryPublisher
            .map { it.trim() }
            .distinctUntilChanged()
            .debounce(SEARCH_DEBOUNCE)
            .flowOn(dispatchers.default)
            .mapLatest(::searchUsersInternal)
            .launchIn(viewModelScope)
    }

    fun submitAction(action: UserListAction) {
        state = reducer.applyAction(action, state)

        val viewState = createViewState(state)
        mutableViewStateFlow.value = viewState

        when (action) {
            UserListAction.Init -> submitSideEffect(UserListSideEffect.LoadAllUsers)
            is UserListAction.QueryChanged -> sendQueryToSearchPublisher(query = action.query)
            is UserListAction.Users -> Unit
            is UserListAction.Error -> Unit
            is UserListAction.Loading -> Unit
        }
    }

    private fun createViewState(state: UserListState): UserListViewState {
        return when {
            state.isLoading -> UserListViewState.Loading
            !state.error.isNullOrBlank() -> UserListViewState.Error(state.error)
            else -> UserListViewState.List(state.items)
        }
    }

    private fun submitSideEffect(sideEffect: UserListSideEffect) {
        when (sideEffect) {
            is UserListSideEffect.LoadAllUsers -> getAllUsers()
            is UserListSideEffect.SearchUsers -> searchUsersInternal(sideEffect.query)
        }
    }

    private fun getAllUsers() {
        viewModelScope.launchSafe {
            submitAction(UserListAction.Loading(true))
            val users = withContext(dispatchers.io) {
                usersRepository.getUsers()
            }
            submitAction(UserListAction.Users(users))
        }
    }

    private fun sendQueryToSearchPublisher(query: String) {
        searchQueryPublisher.tryEmit(query)
    }

    private fun searchUsersInternal(query: String) {
        viewModelScope.launchSafe {
            submitAction(UserListAction.Loading(true))
            val users = withContext(dispatchers.io) {
                usersRepository.searchUsers(query)
            }
            submitAction(UserListAction.Users(users))
        }
    }

    override fun onException(exception: Throwable) {
        submitAction(UserListAction.Error(exception.message ?: "FATAL"))
        super.onException(exception)
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}