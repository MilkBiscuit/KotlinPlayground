import kotlinx.coroutines.*


private suspend fun doSomethingSlow() {
    coroutineScope {
        doThatIn(this)

        delay(2000L)
        println("some job is finished.")
    }
    println("When do I get called?")
}

private fun doThatIn(scope: CoroutineScope) {
    scope.launch {
        println("I'm fine")
    }
}

suspend fun main() {
    doSomethingSlow()
}
