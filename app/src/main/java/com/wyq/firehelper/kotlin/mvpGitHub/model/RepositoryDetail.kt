package com.wyq.firehelper.kotlin.mvpGitHub.model

import com.google.gson.annotations.SerializedName

data class RepositoryDetail(val name: String,
                            val description: String,
                            val stargazers_count: String,
                            val forks_count: String,
                            val created_at: String,
                            val updated_at: String,
                            @SerializedName("source")
                            val parent: Source?)