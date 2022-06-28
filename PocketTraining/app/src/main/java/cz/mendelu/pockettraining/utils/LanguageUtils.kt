package cz.mendelu.pockettraining.utils

import java.util.*

object LanguageUtils {

    private val CZECH = "cs"

    fun isLanguageCzech(): Boolean{
        val language = Locale.getDefault().language
        return language.equals(CZECH)
    }
}