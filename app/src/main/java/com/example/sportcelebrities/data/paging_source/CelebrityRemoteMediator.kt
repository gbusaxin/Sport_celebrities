package com.example.sportcelebrities.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sportcelebrities.data.local.AppDatabase
import com.example.sportcelebrities.data.remote.ApiService
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.model.CelebrityRemoteKey
import com.example.sportcelebrities.utils.Constants.STARTING_PAGE_INDEX
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
                val remoteKeys = getRemoteKeyToNearestToCurrentPosition(state = state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextPage
            }
        }

        //val pageIndex = state.anchorPosition ?: STARTING_PAGE_INDEX

        return try {
            val response = apiService.getAllCelebrity(page = page)
            if (response.isNotEmpty()) {
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        celebrityDao.deleteAllCelebrities()
                        celebrityRemoteKeyDao.deleteAllRemoteKeys()
                    }
                    val prevPage = if (page == STARTING_PAGE_INDEX) null else page - 1
                    val nextPage = if (response.isEmpty()) null else page + 1
                    val keys = response.map {
                        CelebrityRemoteKey(
                            id = it.id,
                            prevKey = prevPage,
                            nextKey = nextPage
                        )
                    }
                    celebrityRemoteKeyDao.addAllRemoteKey(keys)
                    celebrityDao.insertAllCelebrities(response)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Celebrity>
    ): CelebrityRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { celebrityRemoteKeyDao.getRemoteKey(id = it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Celebrity>
    ): CelebrityRemoteKey? {
        return state.pages.firstOrNull()
        {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            celebrityRemoteKeyDao.getRemoteKey(id = it.id)
        }
    }

    private suspend fun getRemoteKeyToNearestToCurrentPosition(
        state: PagingState<Int, Celebrity>
    ): CelebrityRemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let {
                celebrityRemoteKeyDao.getRemoteKey(id = it)
            }
        }
    }

}