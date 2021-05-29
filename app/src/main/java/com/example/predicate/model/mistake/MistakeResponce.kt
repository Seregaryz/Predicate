package com.example.predicate.model.mistake

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MistakeResponce(

	@SerializedName("sentence")
	val sentence: String? = null,

	@SerializedName("mistakes")
	val mistakes: List<MistakesItem?>? = null,

	@SerializedName("id")
	val id: Int? = null
): Parcelable