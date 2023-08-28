import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger


fun fibonacci(): Flow<BigInteger> = flow {
    var x = BigInteger.ZERO
    var y = BigInteger.ONE
    while (true) {
        emit(x)
        x = y.also {
            y += x
        }
    }
}

val mutableSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow<Int>()
val flow: SharedFlow<Int> = mutableSharedFlow.asSharedFlow()

fun main() {
//    fibonacci().take(100).collect { println(it) }

    runBlocking {
        launch {
            flow.collect {
                delay(100)
                println("received num: $it")
            }
        }
    }

    mutableSharedFlow.tryEmit(1)
    mutableSharedFlow.tryEmit(2)
    mutableSharedFlow.tryEmit(3)
    mutableSharedFlow.tryEmit(4)
}
