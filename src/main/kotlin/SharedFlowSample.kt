import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


suspend fun main() {
    val mutableFlow = MutableSharedFlow<Int>(replay = 0)
    val flow: SharedFlow<Int> = mutableFlow.asSharedFlow()

    coroutineScope {
        launch {
            println("starting to emit")
            delay(2000)
            mutableFlow.emit(1)
            mutableFlow.emit(2)
            mutableFlow.emit(3)
            mutableFlow.emit(4)
            mutableFlow.emit(5)
            mutableFlow.emit(6)
        }

        launch {
            println("first value 1, received num: ${flow.firstOrNull()}")
            println("first value 2, received num: ${flow.firstOrNull()}")
            println("first value 3, received num: ${flow.firstOrNull()}")
            println("place 1")
        }

        launch {
            delay(300)
            mutableFlow.collect {
                println("received num: $it")
            }
            println("place 2")
        }
        println("hello main")
    }
}
