package com.mikyegresl.valostat.base.model.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//"Normal article"
//"External Link"
@Parcelize
enum class ArticleTypeDto: Parcelable {
    NORMAL_ARTICLE,
    YOUTUBE_VIDEO,
    EXTERNAL_LINK,
    UNDEFINED;
}