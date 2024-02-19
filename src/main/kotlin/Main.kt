import java.io.File
import kotlin.math.pow
import kotlin.random.Random

private const val FILE = "division.fc"

fun main() {
    test(100)
}

fun test(n: Int) {
    val core = Core()
    val file = File(FILE)
    val interpreter = Interpreter(file, core)
    interpreter.parse()
    for (i in 0..n) {
        val divisor = Random.nextInt(1, 3000)
        val dividend = Random.nextInt(1, 3000)
        core[0] = dividend
        core[1] = divisor
        interpreter.run()
        require(core[2] * divisor + core[3] == dividend)
    }
    println("Average cycles: ${core.cycles / n}")
}