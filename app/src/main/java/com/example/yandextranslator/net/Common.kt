package com.example.yandextranslator.net

import java.awt.font.TextAttribute

data class Languages(
        val dirs: List<String>,
        val langs: Map<String, String>
)

data class ProbablyLanguage(
        val code: Int,
        val lang: String
)

data class Translate(
        val code: Int,
        val lang: String,
        val text: List<String>
)