package com.example.sportcelebrities.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sportcelebrities.data.local.AppDatabase
import com.example.sportcelebrities.data.remote.ApiService
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.model.CelebrityRemoteKey
import com.example.sportcelebrities.utils.Constants.LAST_PAGE_INDEX
import com.example.sportcelebrities.utils.Constants.STAR_PAGE_INDEX
import javax.inject.Inject

@ExperimentalPagingApi
class CelebrityRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Celebrity>() {

    private val celebrityDao = appDatabase.getCelebrityDao()
    private val celebrityRemoteKeyDao = appDatabase.getCelebrityRemoteDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Celebrity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyToNearestToCurrentPosition(state)
                val s = remoteKeys?.nextKey?.minus(1) ?: 1
                Log.d("CHECK_REFRESH_STATE", s.toString())
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                Log.d("CHECK_PREPEND_STATE", "${remoteKeys?.prevKey ?: "null"}")
                val prevPage = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                Log.d("CHECK_APPEND_STATE", "${remoteKeys?.nextKey ?: "null"}")
                val nextPage = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextPage
            }
        }

        return try {
            val response = apiService.getAllCelebrity(page = page)
            if (!response.isNullOrEmpty()) {
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        celebrityDao.deleteAllCelebrities()
                        celebrityRemoteKeyDao.deleteAllRemoteKeys()
                    }
                    val prevPage = if (page == STAR_PAGE_INDEX) null else page - 1
                    Log.d("PREV_PAGE", prevPage.toString())
                    val nextPage =
                        if (response.isNullOrEmpty() ||
                            page == LAST_PAGE_INDEX) null else page + 1
                    Log.d("NEXT_PAGE", nextPage.toString())
                    val keys = response.map {
                        CelebrityRemoteKey(
                            id = it.id,
                            prevKey = prevPage,
                            nextKey = nextPage,
                            lastUpdate = System.currentTimeMillis()
                        )
                    }
                    Log.d("RESPONSE_PAGE", "Page is $page, response is ${response.map { it.id }}")
                    celebrityRemoteKeyDao.addAllRemoteKey(keys)
                    celebrityDao.insertAllCelebrities(response)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.isNullOrEmpty())
        } catch (e: Exception) {
            Log.d("CHECK_MediatorResultEr", e.toString())
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = celebrityRemoteKeyDao.getRemoteKey(id = 1)?.lastUpdate ?: 0L
        val cacheTimeout = 720

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) InitializeAction.SKIP_INITIAL_REFRESH
        else InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Celebrity>
    ): CelebrityRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                Log.d("CHECK_DOWNLOAD_LAST", it.id.toString())
                celebrityRemoteKeyDao.getRemoteKey(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Celebrity>
    ): CelebrityRemoteKey? {
        return state.pages.firstOrNull()
        {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            Log.d("CHECK_DOWNLOAD_FIRST", it.id.toString())
            celebrityRemoteKeyDao.getRemoteKey(id = it.id)
        }
    }

    private suspend fun getRemoteKeyToNearestToCurrentPosition(
        state: PagingState<Int, Celebrity>
    ): CelebrityRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                Log.d("CHECK_DOWNLOAD_NEAREST", id.toString())
                celebrityRemoteKeyDao.getRemoteKey(id = id)
            }
        }
    }

}