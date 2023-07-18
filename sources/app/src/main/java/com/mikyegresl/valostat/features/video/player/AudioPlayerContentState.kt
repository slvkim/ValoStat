package com.mikyegresl.valostat.features.video.player

sealed class AudioPlayerContentState {
    object AudioLoadingState: AudioPlayerContentState()
    object AudioPlayingState: AudioPlayerContentState()
    object AudioPausedState: AudioPlayerContentState()
    object AudioEndedState: AudioPlayerContentState()
}