package com.wyq.firehelper.kotlin.mvpGitHub.net

import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import com.wyq.firehelper.kotlin.mvpGitHub.model.RepositoryDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("users/{users}/repos")
    fun getUserRepos(@Path("users") organizationName:String,
                     @Query("type") reposType:String,
                     @Query("sort") sortType:String
                     ):Observable<MutableList<Repository>>

    @GET("/repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String,
                      @Path("repo") name: String): Observable<RepositoryDetail>
}