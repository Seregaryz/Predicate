package com.example.predicate.model.mistake

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MistakesItem(

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("type")
	val type: String? = null
): Parcelable