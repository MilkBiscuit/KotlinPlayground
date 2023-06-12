import kotlinx.coroutines.*
import java.lang.IllegalStateException


private suspend fun doSomethingSlow() {
    coroutineScope {
        val infiniteJob = somethingInfinite(this)
        println("jobs have been launched.")
        anotherThing()
        println("The second job done, I want to finish.")

        infiniteJob.cancel()
//        cancel()
        println("Do I get called?")
    }
    println("When do I get called?")
}

private suspend fun anotherThing() {
    delay(1000L)
    println("Another job done.")
}

private fun somethingInfinite(scope: CoroutineScope): Job {
    return scope.launch {
        try {
            for (index in 0 until 50) {
                delay(200L)
                println("I'm doing something. $index")
            }
        } catch (e: CancellationException) {
            println("Infinite job got cancelled.")
        } finally {
            println("Calling another suspend after cancelled is not allowed.")
            delay(10L)
            println("Infinite job completed anyway.")
        }
    }
}

suspend fun main() {
    doSomethingSlow()
}
