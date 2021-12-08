class Keyword {

    private val kw: Map<Char, Int> = mapOf(
        'а' to 1,
        'б' to 2,
        'в' to 3,
        'г' to 4,
        'д' to 5,
        'е' to 6,
        'ё' to 7,
        'ж' to 8,
        'з' to 9,
        'и' to 10,
        'й' to 11,
        'к' to 12,
        'л' to 13,
        'м' to 14,
        'н' to 15,
        'о' to 16,
        'п' to 17,
        'р' to 18,
        'с' to 19,
        'т' to 20,
        'у' to 21,
        'ф' to 22,
        'х' to 23,
        'ц' to 24,
        'ч' to 25,
        'ш' to 26,
        'щ' to 27,
        'ъ' to 28,
        'ы' to 29,
        'ь' to 30,
        'э' to 31,
        'ю' to 32,
        'я' to 33,
        'А' to 34,
        'Б' to 35,
        'В' to 36,
        'Г' to 37,
        'Д' to 38,
        'Е' to 39,
        'Ё' to 40,
        'Ж' to 41,
        'З' to 42,
        'И' to 43,
        'Й' to 44,
        'К' to 45,
        'Л' to 46,
        'М' to 47,
        'Н' to 48,
        'О' to 49,
        'П' to 50,
        'Р' to 51,
        'С' to 52,
        'Т' to 53,
        'У' to 54,
        'Ф' to 55,
        'Х' to 56,
        'Ц' to 57,
        'Ч' to 58,
        'Ш' to 59,
        'Щ' to 60,
        'Ъ' to 61,
        'Ы' to 62,
        'Ь' to 63,
        'Э' to 64,
        'Ю' to 65,
        'Я' to 66,
        '!' to 67,
        '?' to 68,
        ',' to 69,
        '.' to 70,
        '-' to 71,
        '*' to 72,
        '+' to 73,
        ';' to 74,
        'I' to 75,
        'V' to 76,
        'X' to 77,
        ':' to 78,
        ' ' to 79
    )
    private val kwReversed: Map<Int, Char> = kw.entries.associate{ (k, v) -> v to k }
    private val kwMax: Int = 79
    private val kwMin: Int = 1

    private fun getSymCode(symbol: Char): Int {
        if (kw[symbol] != null) {
            return kw[symbol]!!
        }
        return 0
    }

    private fun getSymByCode(code: Int): Char {
        if (kwReversed[code] != null) {
            return kwReversed[code]!!
        }
        return '|'
    }

    fun handle(value: Char, key: Int, text: StringBuilder, idx: Int) {
        val symbol: Int? = getSymCode(value)
        if (symbol != null) {
            var newIdx: Int = symbol + key;
            if (newIdx > kwMax) {
                newIdx -= kwMax
            }
            if (newIdx < kwMin) {
                newIdx += kwMax
            }
            val newIt: Char? = getSymByCode(newIdx)
            text.setCharAt(idx, newIt!!)
        }
    }
}