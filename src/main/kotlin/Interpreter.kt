import java.io.File
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.nio.charset.Charset

class Interpreter(
    private val file: File,
    private val core: Core,
) {
    private var lines: List<String> = emptyList()
    fun parse() {
        lines = file.readLines(Charset.defaultCharset())
    }
    fun run() {
        var i = 0
        while (i in lines.indices) {
            val line = lines[i]
            val components = line.split(" ")
            if (components.size !in 3..4) {
                throw IllegalInstructionException(line)
            }
            val opcode = components[0]
            when (opcode) {
                "add" -> core.add(components[1].toInt(), components[2].toInt(), components[3].toInt())
                "sub" -> core.sub(components[1].toInt(), components[2].toInt(), components[3].toInt())
                "mul" -> core.mul(components[1].toInt(), components[2].toInt(), components[3].toInt())
                "shl" -> core.shl(components[1].toInt(), components[2].toInt())
                "shr" -> core.shr(components[1].toInt(), components[2].toInt())
                "bor" -> core.bor(components[1].toInt(), components[2].toInt(), components[3].toInt())
                "band" -> core.band(components[1].toInt(), components[2].toInt(), components[3].toInt())
                "li" -> core.li(components[1].toInt(), components[2].toShort())
                "jz" -> {
                    if (core.e(components[1].toInt())) {
                        i += components[2].toShort()
                        if (i !in lines.indices) throw RuntimeException("Jump to undefined code!")
                        continue
                    }
                }
                "jp" -> {
                    if (core.gt(components[1].toInt())) {
                        i += components[2].toShort()
                        if (i !in lines.indices) throw RuntimeException("Jump to undefined code!")
                        continue
                    }
                }
                ";" -> continue // this is a comment
                else -> throw IllegalInstructionException(line)
            }
            i++
        }
    }
}

class IllegalInstructionException(msg: String) : Exception("$msg is not a valid FunnyCore instruction!")