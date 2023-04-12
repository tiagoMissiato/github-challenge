package com.tiagomissiato.challenge.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitRepoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "repo_name") val name: String,
    @ColumnInfo(name = "repo_full_name") val fullName: String,
    @ColumnInfo(name = "repo_description") val description: String,
    @ColumnInfo(name = "repo_url") val url: String,
    @ColumnInfo(name = "repo_stars") val starCount: Int,
    @ColumnInfo(name = "repo_forks") val forkCount: Int,
    @ColumnInfo(name = "owner_name") val ownerName: String,
    @ColumnInfo(name = "owner_avatar_url") val ownerAvatarUrl: String,
    @ColumnInfo(name = "license_name") val licenseName: String,
)