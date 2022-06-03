package com.infinitybet.premierleague.feature.author

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinitybet.premierleague.core.data.repository.AuthorsRepository
import com.infinitybet.premierleague.core.data.repository.NewsRepository
import com.infinitybet.premierleague.core.model.data.Author
import com.infinitybet.premierleague.core.model.data.FollowableAuthor
import com.infinitybet.premierleague.core.model.data.NewsResource
import com.infinitybet.premierleague.core.result.Result
import com.infinitybet.premierleague.core.result.asResult
import com.infinitybet.premierleague.feature.author.navigation.AuthorDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AuthorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authorsRepository: AuthorsRepository,
    newsRepository: NewsRepository
) : ViewModel() {

    private val authorId: String = checkNotNull(
        savedStateHandle[AuthorDestination.authorIdArg]
    )

    // Observe the followed authors, as they could change over time.
    private val followedAuthorIdsStream: Flow<Result<Set<String>>> =
        authorsRepository.getFollowedAuthorIdsStream().asResult()

    // Observe author information
    private val author: Flow<Result<Author>> = authorsRepository.getAuthorStream(
        id = authorId
    ).asResult()

    // Observe the News for this author
    private val newsStream: Flow<Result<List<NewsResource>>> =
        newsRepository.getNewsResourcesStream(
            filterAuthorIds = setOf(element = authorId),
            filterTopicIds = emptySet()
        ).asResult()

    val uiState: StateFlow<AuthorScreenUiState> =
        combine(
            followedAuthorIdsStream,
            author,
            newsStream
        ) { followedAuthorsResult, authorResult, newsResult ->
            val author: AuthorUiState =
                if (authorResult is Result.Success && followedAuthorsResult is Result.Success) {
                    val followed = followedAuthorsResult.data.contains(authorId)
                    AuthorUiState.Success(
                        followableAuthor = FollowableAuthor(
                            author = authorResult.data,
                            isFollowed = followed
                        )
                    )
                } else if (
                    authorResult is Result.Loading || followedAuthorsResult is Result.Loading
                ) {
                    AuthorUiState.Loading
                } else {
                    AuthorUiState.Error
                }

            val news: NewsUiState = when (newsResult) {
                is Result.Success -> NewsUiState.Success(newsResult.data)
                is Result.Loading -> NewsUiState.Loading
                is Result.Error -> NewsUiState.Error
            }

            AuthorScreenUiState(author, news)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AuthorScreenUiState(AuthorUiState.Loading, NewsUiState.Loading)
            )

    fun followAuthorToggle(followed: Boolean) {
        viewModelScope.launch {
            authorsRepository.toggleFollowedAuthorId(authorId, followed)
        }
    }
}

sealed interface AuthorUiState {
    data class Success(val followableAuthor: FollowableAuthor) : AuthorUiState
    object Error : AuthorUiState
    object Loading : AuthorUiState
}

sealed interface NewsUiState {
    data class Success(val news: List<NewsResource>) : NewsUiState
    object Error : NewsUiState
    object Loading : NewsUiState
}

data class AuthorScreenUiState(
    val authorState: AuthorUiState,
    val newsState: NewsUiState
)
