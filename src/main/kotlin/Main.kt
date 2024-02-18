import java.io.File
private const val FILE = "division.fc"

fun main() {
    val core = Core()
    core[0] = 24 //dividend
    core[1] = 6 //divisor
    val file = File(FILE)
    val interpreter = Interpreter(file, core)
    interpreter.parse()
    interpreter.run()
    println(core.cycles)
    println(core[2])
    println(core[3])
}