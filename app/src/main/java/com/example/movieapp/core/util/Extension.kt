package com.example.movieapp.core.util

import com.example.movieapp.BuildConfig

fun String?.toPostUrl() = "${BuildConfig.BASE_URL_IMAGE}$this"

fun String?.toBackDropUrl() = "${BuildConfig.BASE_URL_IMAGE}$this"