package com.example.sportcelebrities.data.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sportcelebrities.data.remote.ApiService
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.utils.Constants
import com.example.sportcelebrities.utils.Constants.STAR_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchCelebritySource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Celebrity>() {
    override fun getRefreshKey(state: PagingState<Int, Celebrity>): Int? {
        return null //state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Celebrity> {
        return try {
            val pageIndex = params.key ?: STAR_PAGE_INDEX
            val response =
                apiService.getAllCelebrity(pageIndex) // fun searching is not supported by the backend server
            if (!response.isNullOrEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (pageIndex == STAR_PAGE_INDEX) null else pageIndex - 1,
                    nextKey = if (response.isNullOrEmpty() ||
                        pageIndex == Constants.LAST_PAGE_INDEX
                    ) null else pageIndex + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            Log.d("EXCEPTION_SOURCE", " Exception -> ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("EXCEPTION_SOURCE", " HttpException -> ${e.message}")
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.d("EXCEPTION_SOURCE", " IOException -> ${e.message}")
            LoadResult.Error(e)
        }
    }
}