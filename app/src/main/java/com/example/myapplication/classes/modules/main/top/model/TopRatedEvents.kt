package com.example.myapplication.classes.modules.main.top.model

import com.example.myapplication.classes.models.API.Movie
import com.example.myapplication.classes.models.firebase.UserMovieExtraInfo
import com.example.myapplication.classes.modules.main.now_playing.model.NowPlayingEvents


sealed class TopRatedEvents {
    object ShowAllList: TopRatedEvents()
    object ResetAll: TopRatedEvents()
    data class ShowSearchedList(val query: String): TopRatedEvents()
    data class AddPersonalMovie(val movie: Movie, val info: UserMovieExtraInfo?): TopRatedEvents()
    data class QuitPersonalMovie(val movie: Movie): TopRatedEvents()
    data class HasInPersonal(val movie: Movie, val info: UserMovieExtraInfo?): TopRatedEvents()
    data class CheckMovie(val movie: Movie): TopRatedEvents()
}
