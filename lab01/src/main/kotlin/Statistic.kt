import java.lang.NullPointerException
import java.math.BigDecimal
import java.math.MathContext


class Statistic(text: StringBuilder) {

    private val mathContext: MathContext = MathContext(40)
    private val prebuiltTable: Map<Char, BigDecimal> = mapOf(
        'а' to BigDecimal(0.06409786732958262462383880675127567709015, mathContext),
        'б' to BigDecimal(0.01278948057045662697893497317807143791705, mathContext),
        'в' to BigDecimal(0.03467879105063456757817610885777835928300, mathContext),
        'г' to BigDecimal(0.01567447337432945178594792620698678529373, mathContext),
        'д' to BigDecimal(0.02155982251122629818646981225098937998854, mathContext),
        'е' to BigDecimal(0.06393993097658800485029381587538475888443, mathContext),
        'ё' to BigDecimal(0.00004579353656940991757163417506214837105848, mathContext),
        'ж' to BigDecimal(0.007909197958916655763443673950019625801387, mathContext),
        'з' to BigDecimal(0.01355488682454533560120371581839591783331, mathContext),
        'и' to BigDecimal(0.05113462230335656322038189401308513331646, mathContext),
        'й' to BigDecimal(0.009040952505560643726285489990841292686118, mathContext),
        'к' to BigDecimal(0.02652754154127960225042522569671594923459, mathContext),
        'л' to BigDecimal(0.03922208749183844790597891987687715698162, mathContext),
        'м' to BigDecimal(0.02170613633390030092895459897945832788172, mathContext),
        'н' to BigDecimal(0.05143268350124296742116969776265864189454, mathContext),
        'о' to BigDecimal(0.08683108726939683370404291508569933272275, mathContext),
        'п' to BigDecimal(0.01853505136780950604287979532826095646728, mathContext),
        'р' to BigDecimal(0.03318068821143530027476121941645950542981, mathContext),
        'с' to BigDecimal(0.03901609315713724977103231715295041214183, mathContext),
        'т' to BigDecimal(0.04288891796415020279994766452963496009420, mathContext),
        'у' to BigDecimal(0.02130664783402401161938518528388876304183, mathContext),
        'ф' to BigDecimal(0.001262593222556587727332199398142090802041, mathContext),
        'х' to BigDecimal(0.006395991845110397484243207589910322864338, mathContext),
        'ц' to BigDecimal(0.002930786340442234724584587203977495747743, mathContext),
        'ч' to BigDecimal(0.01118016485673164987570325788303022373414, mathContext),
        'ш' to BigDecimal(0.006424178987308648436477822844432814339919, mathContext),
        'щ' to BigDecimal(0.002073793013214706267172576213528719089363, mathContext),
        'ъ' to BigDecimal(0.0002616773518251995289807667146408478346199, mathContext),
        'ы' to BigDecimal(0.01460159623184613371712678267695930917179, mathContext),
        'ь' to BigDecimal(0.01670952869535091342758537982864071848308, mathContext),
        'э' to BigDecimal(0.001851367264163286667538924506083998429936, mathContext),
        'ю' to BigDecimal(0.005390553447599110297003794321601465393170, mathContext),
        'я' to BigDecimal(0.0007661865231121830319666342425413407597905, mathContext),
        '!' to BigDecimal(0.0008897029962056783985346068297788826377077, mathContext),
        '?' to BigDecimal(0.001406515766060447468271621091194557111082, mathContext),
        ',' to BigDecimal(0.02280518121156613895067381918094988878713, mathContext),
        '.' to BigDecimal(0.01169697762658641894544027214444589820751, mathContext),
        '-' to BigDecimal(0.01651838283396572026691089886170351956038, mathContext),
        '*' to BigDecimal(0, mathContext),
        '+' to BigDecimal(0, mathContext),
        ';' to BigDecimal(0.0003336386235771293994504775611670809891404, mathContext),
        'i' to BigDecimal(0, mathContext),
        'v' to BigDecimal(0, mathContext),
        'x' to BigDecimal(0, mathContext),
        ':' to BigDecimal(0.0003990579615334292816956692398272929477954, mathContext),
        ' ' to BigDecimal(0.1875114483841423524793929085437655370928, mathContext)
    )

    private var kw: MutableMap<Char, Int> = mutableMapOf(
        'а' to 0,
        'б' to 0,
        'в' to 0,
        'г' to 0,
        'д' to 0,
        'е' to 0,
        'ё' to 0,
        'ж' to 0,
        'з' to 0,
        'и' to 0,
        'й' to 0,
        'к' to 0,
        'л' to 0,
        'м' to 0,
        'н' to 0,
        'о' to 0,
        'п' to 0,
        'р' to 0,
        'с' to 0,
        'т' to 0,
        'у' to 0,
        'ф' to 0,
        'х' to 0,
        'ц' to 0,
        'ч' to 0,
        'ш' to 0,
        'щ' to 0,
        'ъ' to 0,
        'ы' to 0,
        'ь' to 0,
        'э' to 0,
        'ю' to 0,
        'я' to 0,
        '!' to 0,
        '?' to 0,
        ',' to 0,
        '.' to 0,
        '-' to 0,
        '*' to 0,
        '+' to 0,
        ';' to 0,
        'i' to 0,
        'v' to 0,
        'x' to 0,
        ':' to 0,
        ' ' to 0
    )
    private var symbolSum: Double = 0.0

    private fun handleSymbol(symbol: Char) {
        if (kw[symbol] != null) {
            kw?.put(symbol, kw.getValue(symbol).plus(1))
            symbolSum += 1f
        }
    }

    fun getMathContext(): MathContext {
        return mathContext
    }

    fun getPrebuiltTbl(): Map<Char, Any> {
        return prebuiltTable
    }

    fun getPrebuilt(chr: Char): BigDecimal {
        if (symbolSum != 0.0) {
            val decapitalized: Char = chr.toString().lowercase().get(0).toChar()
            var value = BigDecimal(0.0, mathContext)
            try {
                value = prebuiltTable[decapitalized]!!
                return value
            } catch (e: NullPointerException) {
                return value
            }
        } else {
            return BigDecimal(0.0, mathContext)
        }
    }

    fun get(symbol: Char): BigDecimal {
        if (symbolSum != 0.0) {
            var valueA: Double = 0.0
            var a: BigDecimal = BigDecimal(0.0, mathContext)
            val b = BigDecimal(symbolSum, mathContext)
            try {
                valueA = kw[symbol]!!.toDouble()
                a = BigDecimal(valueA, mathContext)
            } catch (e: NullPointerException) {
                return BigDecimal(0.0, mathContext)
            }

            var value = BigDecimal(0.0, mathContext)
            try {
                value = a.divide(b, mathContext)
                return value
            } catch (e: NullPointerException) {
                return value
            }
        } else {
            return BigDecimal(0.0, mathContext)
        }
    }

    fun printAll() {
        println("Всего символов: $symbolSum")
        kw.forEach {
            println("'" + it.key.toString() + "' to BigDecimal(" + get(it.key) + ", mathContext),")
        }
    }

    init {
        text.forEach {
            handleSymbol(it)
        }
    }
}