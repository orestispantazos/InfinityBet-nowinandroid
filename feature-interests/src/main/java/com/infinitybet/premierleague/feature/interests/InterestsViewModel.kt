package com.infinitybet.premierleague.feature.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinitybet.premierleague.core.data.combine
import com.infinitybet.premierleague.core.data.repository.AuthorsRepository
import com.infinitybet.premierleague.core.data.repository.PredictionsRepository
import com.infinitybet.premierleague.core.data.repository.TopicsRepository
import com.infinitybet.premierleague.core.model.data.FollowableAuthor
import com.infinitybet.premierleague.core.model.data.FollowablePrediction
import com.infinitybet.premierleague.core.model.data.FollowableTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val authorsRepository: AuthorsRepository,
    private val topicsRepository: TopicsRepository,
    private val predictionsRepository: PredictionsRepository,
) : ViewModel() {

    private val _tabState = MutableStateFlow(
        InterestsTabState(
            titles = listOf(
                R.string.interests_topics,
                R.string.interests_people,
                //R.string.interests_tips
            ),
            currentIndex = 0
        )
    )
    val tabState: StateFlow<InterestsTabState> = _tabState.asStateFlow()

    val uiState: StateFlow<InterestsUiState> = combine(
        authorsRepository.getAuthorsStream(),
        authorsRepository.getFollowedAuthorIdsStream(),
        topicsRepository.getTopicsStream(),
        topicsRepository.getFollowedTopicIdsStream(),
        predictionsRepository.getPredictionsStream(),
        predictionsRepository.getFollowedPredictionIdsStream(),
    ) { availableAuthors, followedAuthorIdsState, availableTopics, followedTopicIdsState, availablePredictions, followedPredictionIdsState ->

        InterestsUiState.Interests(
            authors = availableAuthors
                .map { author ->
                    FollowableAuthor(
                        author = author,
                        isFollowed = author.id in followedAuthorIdsState
                    )
                }
                .sortedBy { it.author.name },
            topics = availableTopics
                .map { topic ->
                    FollowableTopic(
                        topic = topic,
                        isFollowed = topic.id in followedTopicIdsState
                    )
                }
                .sortedBy { it.topic.name },
            predictions = availablePredictions
                .map { prediction ->
                    FollowablePrediction(
                        prediction = prediction,
                        isFollowed = prediction.id in followedPredictionIdsState,
                    )
                }
                .sortedBy { it.prediction.matchName },
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = InterestsUiState.Loading
        )

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            topicsRepository.toggleFollowedTopicId(followedTopicId, followed)
        }
    }

    fun followPrediction(followedPredictionId: String, followed: Boolean) {
        viewModelScope.launch {
            predictionsRepository.toggleFollowedPredictionId(followedPredictionId, followed)
        }
    }

    fun followAuthor(followedAuthorId: String, followed: Boolean) {
        viewModelScope.launch {
            authorsRepository.toggleFollowedAuthorId(followedAuthorId, followed)
        }
    }

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.currentIndex) {
            _tabState.update {
                it.copy(currentIndex = newIndex)
            }
        }
    }
}

data class InterestsTabState(
    val titles: List<Int>,
    val currentIndex: Int
)

sealed interface InterestsUiState {
    object Loading : InterestsUiState

    data class Interests(
        val authors: List<FollowableAuthor>,
        val topics: List<FollowableTopic>,
        val predictions: List<FollowablePrediction>,
    ) : InterestsUiState

    object Empty : InterestsUiState
}
