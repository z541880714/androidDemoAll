package com.example.performance.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMessenger(var content: String) : Parcelable 