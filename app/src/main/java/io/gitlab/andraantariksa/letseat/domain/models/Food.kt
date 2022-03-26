package io.gitlab.andraantariksa.letseat.domain.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Food(
    val price: String,
    val name: String,
    val imageUrl: String,
    val description: String
): Parcelable