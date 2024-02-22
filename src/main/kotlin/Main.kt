import java.io.File
import kotlin.math.pow
import kotlin.random.Random

private const val FILE = "test.fc" 

fun main() {
    val core = Core()
    val file = File(FILE)
    val interpreter = Interpreter(file, core)
    interpreter.parse()
    /*
    Set registers like this:
    core[0] = dividend
    core[1] = divisor
    */
    interpreter.run()
    /*
    Print registers like this:
    println(core[2])
    println(core[3])
    */
}
