package com.nesgorilla.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nesgorilla.model.network.FeedDTO
import com.nesgorilla.util.ACCOUNT_MOCK

@Entity(
    foreignKeys = [androidx.room.ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"]
    )]
)
data class Feed(
    @PrimaryKey
    var id: String,
    var accountId: String,
    var first_name: String,
    var last_name: String,
    var post_body: String?,
    var unix_timestamp: String?
) {
    constructor(dto: FeedDTO) : this(
        id = dto.id!!,
        accountId = ACCOUNT_MOCK,
        first_name = dto.first_name!!,
        last_name = dto.last_name!!,
        post_body = dto.post_body,
        unix_timestamp = dto.unix_timestamp
    )
}
