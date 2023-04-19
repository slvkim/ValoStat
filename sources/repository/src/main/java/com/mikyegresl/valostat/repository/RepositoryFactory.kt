package com.mikyegresl.valostat.repository



object RepositoriesFactory {

    fun getPearsonChannelsRepository(
        remoteDataSource: PearsonChannelsRemoteDataSource,
        localDataSource: ChannelsLocalDataSource,
        authProvider: AuthProvider,
        gson: Gson
    ): PearsonChannelsRepository = ChannelsRepositoryImpl(
        remoteDataSource,
        localDataSource,
        authProvider,
        gson
    )

    fun getBoclipsTokenRepository(
        remoteDataSource: BoclipsVideoTokenRemoteDataSource
    ): BoclipsVideoTokenRepository = BoclipsVideoTokenRepositoryImpl(
        remoteDataSource,
    )

    fun getBoclipsInfoRepository(
        remoteDataSource: BoclipsVideoInfoRemoteDataSource,
        tokenRemoteDataSource: BoclipsVideoTokenRemoteDataSource
    ): BoclipsVideoInfoRepository = BoclipsVideoInfoRepositoryImpl(
        remoteDataSource,
        tokenRemoteDataSource
    )

    fun getChannelsBookmarksRepository(
        localDataSource: ChannelsBookmarksStorage,
        authProvider: AuthProvider
    ): ChannelsBookmarksRepository = ChannelsBookmarksRepositoryImpl(
        localDataSource,
        authProvider
    )

    fun getChannelContentOptionsRepository(
        localDataSource: ContentOptionsStorage,
        authProvider: AuthProvider,
    ): ChannelContentOptionsRepository = ChannelContentOptionsRepositoryImpl(
        localDataSource,
        authProvider,
    )

    fun getAssetCountViewingRepository(
        localStorage: ChannelsAssetViewsCounterStorage,
        remoteDataSource: AssetViewsCounterRemoteDataSource
    ): AssetCountViewingRepository = AssetCountViewingRepositoryImpl(
        localStorage, remoteDataSource
    )

    fun getAssetCountViewingGapDurationRepository(
        localStorage: ChannelsAssetViewsCounterStorage
    ): AssetCountViewingGapDurationRepository = AssetCountViewingGapDurationRepositoryImpl(
        localStorage
    )
}
