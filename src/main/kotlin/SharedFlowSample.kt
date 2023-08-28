import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.math.BigInteger


private fun fibonacci(): Flow<BigInteger> = flow {
    var x = BigInteger.ZERO
    var y = BigInteger.ONE
    while (true) {
        emit(x)
        x = y.also {
            y += x
        }
    }
}

private val mutableSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow<Int>(
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
)
private val flow: SharedFlow<Int> = mutableSharedFlow.asSharedFlow()

fun main() {
//    fibonacci().take(100).collect { println(it) }

    CoroutineScope(Dispatchers.IO).launch {
        println("runBlocking start")
        flow.collect {
            println("received num: $it")
        }
        println("runBlocking end")
    }

    var emitResult = mutableSharedFlow.tryEmit(1)
    println("emit success: $emitResult")
    emitResult = mutableSharedFlow.tryEmit(2)
    println("emit success: $emitResult")
    emitResult = mutableSharedFlow.tryEmit(3)
    println("emit success: $emitResult")
    emitResult = mutableSharedFlow.tryEmit(4)
    println("emit success: $emitResult")
}
