import java.math.BigDecimal

class Decryptor(text: StringBuilder) {

    private var text: StringBuilder
    private var stat: Statistic
    private var statTable: Map<BigDecimal, Char>

    fun decryptChar(chr: Char): Char {
        val chrValue: BigDecimal = stat.get(chr)!!
        if (chrValue != BigDecimal(0.0, stat.getMathContext())) {
            var minDistance: BigDecimal = BigDecimal(1, stat.getMathContext())
            var minDistanceChr: Char = '|' // этот символ означает, что программа не смогла найти соответствие

            statTable.forEach {
                minDistance = minDistance.min(it.key.subtract(chrValue).abs())
            }
            statTable.forEach {
                if (it.key.subtract(chrValue).abs().equals(minDistance)) {
                    minDistanceChr = it.value
                }
            }
            return minDistanceChr
        }
        return chr
    }

    fun decrypt() {
        text.forEachIndexed { idx: Int, it: Char ->
            val newChar: Char = decryptChar(it)
            if (newChar != '|') {
                text.setCharAt(idx, newChar)
            }
        }
        println(text)
    }

    init {
        this.text = text
        stat = Statistic(text)
        statTable = stat.getPrebuiltTbl().entries.associate{ (k, v) -> (v to k) as Pair<BigDecimal, Char> }
    }
}