package com.mikyegresl.valostat.base.network.config

internal class NetworkConfigProviderImpl : NetworkConfigProvider {

    override fun getNetworkConfig(): NetworkConfig =
        NetworkConfig(
            apiUrl = "",
            isDebug = false
        )
}