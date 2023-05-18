package com.mikyegresl.valostat.ui.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikyegresl.valostat.R

object Padding {

    val Dp0: Dp
        @Composable get() = 0.dp

    val Dp2: Dp
        @Composable get() = dimensionResource(id = R.dimen.space_tiny_less)

    val Dp4: Dp
        @Composable get() = dimensionResource(R.dimen.space_tiny)

    val Dp6: Dp
        @Composable get() = dimensionResource(id = R.dimen.space_small_less)

    val Dp8: Dp
        @Composable get() = dimensionResource(R.dimen.space_small)

    val Dp16: Dp
        @Composable get() = dimensionResource(R.dimen.space_medium)

    val Dp24: Dp
        @Composable get() = dimensionResource(R.dimen.space_large)

    val Dp32: Dp
        @Composable get() = dimensionResource(R.dimen.space_huge)

    val Dp40: Dp
        @Composable get() = dimensionResource(R.dimen.space_superb)
}
