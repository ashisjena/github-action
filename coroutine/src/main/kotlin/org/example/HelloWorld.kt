package org.example

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val job = GlobalScope.launch {
        delay(1000)
        println("World!")
    }

    print("Hello, ")

    runBlocking { // or make the org.example.org.example.main suspended, so that it waits for the job.
        job.join()
        val obj = ABC("blah")
    }
}


data class ABC(val a: String, val b: String = "Insurance")