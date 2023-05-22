package com.mikyegresl.valostat.providers

import com.mikyegresl.valostat.base.config.AppConfigProvider
import com.mikyegresl.valostat.base.config.ValoStatConfigProvider
import com.mikyegresl.valostat.base.network.config.NetworkConfig
import com.mikyegresl.valostat.base.network.config.NetworkConfigProvider

internal class NetworkConfigProviderImpl(
    private val valoStatConfigProvider: ValoStatConfigProvider,
    private val appConfigProvider: AppConfigProvider
) : NetworkConfigProvider {
    override fun getNetworkConfig(): NetworkConfig =
        NetworkConfig(
            apiUrl = valoStatConfigProvider.apiUrl,
            videoApiUrl = valoStatConfigProvider.videoApiUrl,
            isDebug = appConfigProvider.isDebug
        )
}