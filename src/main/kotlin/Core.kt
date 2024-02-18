import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import kotlin.math.pow

class Core {
    private val NUM_REGISTERS = 16
    private val registers: MutableList<Int> = MutableList(NUM_REGISTERS) { 0 }
    var cycles = 0
        private set

    private fun op(f: (a: Int, b: Int) -> Int, d: Int, r1: Int, r2: Int) {
        if (d !in 0..<NUM_REGISTERS || r1 !in 0..<NUM_REGISTERS || r2 !in 0..<NUM_REGISTERS) {
            throw IllegalArgumentException("Registers must be integers from 0 to 15")
        }
        registers[d] = f(registers[r1], registers[r2])
        cycles++
    }
    fun add(d: Int, r1: Int, r2: Int) = op({ a, b -> a + b}, d, r1, r2)
    fun sub(d: Int, r1: Int, r2: Int) = op({ a, b -> a - b}, d, r1, r2)
    fun mul(d: Int, r1: Int, r2: Int) {
        op({ a, b -> a * b}, d, r1, r2)
        cycles += 2
    }
    fun shl(d: Int, r: Int) {
        if (d !in 0..<NUM_REGISTERS || r !in 0..<NUM_REGISTERS) {
            throw IllegalArgumentException("Registers must be integers from 0 to 15")
        }
        if(registers[r] !in 0..<32) throw RuntimeException("Bit shift distance ${registers[r]} too high")
        val res = registers[d] shl registers[r]
        registers[d] = res and (2.0.pow(31.0)).toInt() //keep lowest bit 0
        cycles++
    }
    fun shr(d: Int, r: Int) {
        if (d !in 0..<NUM_REGISTERS || r !in 0..<NUM_REGISTERS) {
            throw IllegalArgumentException("Registers must be integers from 0 to 15")
        }
        if(registers[r] !in 0..<32) throw RuntimeException("Bit shift distance ${registers[r]} too high")
        registers[d] = registers[d] shr registers[r]
        cycles++
    }
    fun bor(d: Int, r1: Int, r2: Int) = op(Int::or, d, r1, r2)
    fun band(d: Int, r1: Int, r2: Int) = op(Int::and, d, r1, r2)
    fun li(d: Int, i: Short) {
        if (d !in 0..<NUM_REGISTERS) throw IllegalArgumentException("Registers must be integers from 0 to 15")
        registers[d] = i.toInt()
        cycles++
    }
    fun e(r: Int): Boolean {
        if (r !in 0..<NUM_REGISTERS) throw IllegalArgumentException("Registers must be integers from 0 to 15")
        cycles += 3
        return registers[r] == 0
    }
    fun gt(r: Int): Boolean {
        if (r !in 0..<NUM_REGISTERS) throw IllegalArgumentException("Registers must be integers from 0 to 15")
        cycles += 3
        return registers[r] > 0
    }
    operator fun get(r: Int) = registers[r]
    operator fun set(r: Int, v: Int) {
        registers[r] = v
    }
}