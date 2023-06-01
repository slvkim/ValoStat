package com.mikyegresl.valostat.features.player.exoplayer

sealed class VideoPlayerSizeStrategy(
    open val widthPx: Int,
    open val heightPx: Int
) {

    class VideoPlayerFixedSizeCropStrategy(
        override val widthPx: Int,
        override val heightPx: Int,
        val videoContentRatio: Float = DEFAULT_RATIO
    ) : VideoPlayerSizeStrategy(widthPx, heightPx)

    class VideoPlayerSizePickedByRatioStrategy(
        override val widthPx: Int,
        val videoContentRatio: Float = DEFAULT_RATIO,
        override val heightPx: Int = (videoContentRatio * widthPx).toInt()
    ) : VideoPlayerSizeStrategy(widthPx, heightPx)

    companion object {
        const val DEFAULT_RATIO = 4f / 3f
    }
}