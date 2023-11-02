import kotlinx.coroutines.*


suspend fun test1(): Boolean {
    println("---test 1 start---")
//    return try {
//        delay(1000L)
//
//        true
//    } catch (e: CancellationException) {
//        println("--- test 1 is cancelled! ---")
//
//        false
//    }.also {
//        println("---test 1 finish---, result: $it")
//    }

    delay(1000L)
    return true
}

suspend fun test2(): Boolean {
    println("---test 2 start---")
    return try {
        delay(1000L)

        true
    } catch (e: CancellationException) {
        println("--- test 2 is cancelled! ---")

        false
    }.also {
        println("---test 2 finish---, result: $it")
    }
}

suspend fun test3() {
    delay(1000L)
}

private var job1: Job? = null
private var job2: Deferred<Boolean>? = null
private var job3: Job? = null

suspend fun runAllTests() {
    coroutineScope {
        job1 = launch {
            test1()
        }
        job1?.join()
        println("Test 1 done.")
        job2 = async {
            test2()
        }
        try {
            job2?.await()
        } catch (e: Exception) {
            println("Exception detected when await job2: $e")
        }
        println("Test 2 done.")
        job3 = launch {
            test3()
        }
        job3?.join()
        println("Test 3 done.")
    }
}

fun main() {
    runBlocking {
        launch {
            runAllTests()
            println("runAllTests is about to finish")
        }
        launch {
            println("do something")
//            delay(500L)
//            job1?.cancel(CancellationException("Stop Job 1"))
            delay(1500L)
            job2?.cancel(CancellationException("Stop Job 2"))
            println("do something is about to finish")
        }
    }
}

