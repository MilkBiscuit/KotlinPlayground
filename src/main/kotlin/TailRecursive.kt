import kotlin.system.*

fun fibonacciRecursive(n: UInt): UInt {
    if (n < 2u) {
        return n
    }

    return fibonacciRecursive(n - 1u) + fibonacciRecursive(n - 2u)
}

fun fibonacciTail(n: UInt): UInt {
    tailrec fun fibonacciInner(n: UInt, a: UInt, b: UInt): UInt {
        if (n < 2u) {
            return b
        }

        return fibonacciInner(n - 1u, b, a + b)
    }

    return fibonacciInner(n, 0u, 1u)
}

inline fun timeCost(crossinline block: ()-> Unit) {
    measureTimeMillis {
        block.invoke()
    }.let {
        println("took time $it")
    }
}

fun main() {
    println("Hello, World!")

    timeCost {
        val result = fibonacciTail(50u)
        println("The result is $result")
    }

    timeCost {
        val result = fibonacciRecursive(50u)
        println("The result is $result")
    }
}
