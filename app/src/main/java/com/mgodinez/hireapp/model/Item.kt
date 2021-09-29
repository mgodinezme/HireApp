/*
 * Filename: Item.kt
 * Description: Data for an Item object used in the HTTP request as well as the RecyclerView.
 *
 * @author Mauricio Godinez
 *
 */

package com.mgodinez.hireapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item (
    @field:Json(name = "listId") val listId: Long?,
    @field:Json(name = "name") val name: String?,
) // End of data class Item