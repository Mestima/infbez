import java.io.File

fun main(args: Array<String>) {
    print("Введите имя файла: ")
    val fileName = readLine()
    var path: String = "src/main/resources/$fileName.txt"
    val raw: String = File(path).readText()
    var text: StringBuilder = StringBuilder(raw)

    print("""
        Список доступных функций:
            1 - Зашифровать текстовый файл
            2 - Попытаться взломать текстовый файл
            3 - Вывести prebuilt статистику словаря
            4 - Сгенерировать prebuilt статистику словаря по файлу
        
        Выберите функцию: 
        """.trimIndent())

    when (readLine()!!.toInt()) {
        1 -> {
            print("Введите смещение (число): ")
            val key: Int? = readLine()!!.toInt()
            val keyword: Keyword = Keyword()
            text.forEachIndexed { idx: Int, it: Char ->
                keyword.handle(it, key!!, text, idx)
            }
            println(text)
        }

        2 -> {
            val decr: Decryptor = Decryptor(text)
            decr.decrypt()
        }

        3 -> {
            val stat: Statistic = Statistic(text)
            stat.getPrebuiltTbl().forEach {
                println(it.key.toString() + " " + it.value)
            }
        }

        4 -> {
            val stat: Statistic = Statistic(text)
            stat.printAll()
        }
    }
}
