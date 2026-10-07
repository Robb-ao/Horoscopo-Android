package com.example.horoscopo

data class Horoscope (
    val id: String, // Unico e inmutable, llama a la Api y fav
    val name: Int,
    val date: Int,
    val sign: Int
) {

    companion object {
        val horoscopeList: List<Horoscope> = listOf(
            Horoscope(
                id = "aries",
                name = R.string.horoscope_name_aries,
                date = R.string.horoscope_date_aries,
                sign = R.drawable.aries_icon
            ),
            Horoscope(
                id = "taurus",
                name = R.string.horoscope_name_taurus,
                date = R.string.horoscope_date_taurus,
                sign = R.drawable.taurus_icon
            ),
            Horoscope(
                id = "gemini",
                name = R.string.horoscope_name_gemini,
                date = R.string.horoscope_date_gemini,
                sign = R.drawable.gemini_icon
            ),
            Horoscope(
                id = "cancer",
                name = R.string.horoscope_name_cancer,
                date = R.string.horoscope_date_cancer,
                sign = R.drawable.cancer_icon
            ),
            Horoscope(
                id = "leo",
                name = R.string.horoscope_name_leo,
                date = R.string.horoscope_date_leo,
                sign = R.drawable.leo_icon
            ),
            Horoscope(
                id = "virgo",
                name = R.string.horoscope_name_virgo,
                date = R.string.horoscope_date_virgo,
                sign = R.drawable.virgo_icon
            ),
            Horoscope(
                id = "libra",
                name = R.string.horoscope_name_libra,
                date = R.string.horoscope_date_libra,
                sign = R.drawable.libra_icon
            ),
            Horoscope(
                id = "scorpio",
                name = R.string.horoscope_name_scorpio,
                date = R.string.horoscope_date_scorpio,
                sign = R.drawable.scorpio_icon
            ),
            Horoscope(
                id = "sagittarius",
                name = R.string.horoscope_name_sagittarius,
                date = R.string.horoscope_date_sagittarius,
                sign = R.drawable.sagittarius_icon
            ),
            Horoscope(
                id = "capricorn",
                name = R.string.horoscope_name_capricorn,
                date = R.string.horoscope_date_capricorn,
                sign = R.drawable.capricorn_icon
            ),
            Horoscope(
                id = "aquarius",
                name = R.string.horoscope_name_aquarius,
                date = R.string.horoscope_date_aquarius,
                sign = R.drawable.aquarius_icon
            ),
            Horoscope(
                id = "pisces",
                name = R.string.horoscope_name_pisces,
                date = R.string.horoscope_date_pisces,
                sign = R.drawable.pisces_icon
            )
        )

        fun getAll(): List<Horoscope> {
            return horoscopeList
        }

        fun getById(id: String): Horoscope {
            return horoscopeList.find { it.id == id }!!
        }
    }

}