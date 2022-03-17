import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


suspend fun main() {
    val flow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 3)

    coroutineScope {
        flow.emit(1)
        flow.emit(2)
        flow.emit(3)
        flow.emit(4)
        flow.emit(5)
    }

    coroutineScope {
        launch {
            delay(1000)
            flow.collect {
                println("received num: $it")
            }
        }
        println("Hello")
    }
}
