package com.tiagomissiato.challenge.repository.data.model

data class GitRepo(
    val id: Int,
    val name: String,
    val fullName: String,
    val description: String,
    val url: String,
    val starCount: Int,
    val forkCount: Int,
    val owner: Owner,
    val license: License?
)
