package com.wyq.firehelper.kotlin.example

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 *
 *
 * @author Uni.W
 * @date 2019/3/14 16:22
 */
class CoroutinesExample {

    fun main(args: Array<String>) {


    }

    fun print() = runBlocking {
        val job = GlobalScope.launch {
            // 启动一个新协程并保持对这个作业的引用
            delay(1000)
            println("word!")
        }

        println("hello ")
        job.join()// 等待直到子协程执行结束
    }

    /**
     * 我们可以在执行操作所在的指
    定作用域内启动协程， 而不是像通常使用线程（线程总是全局的）那样在 GlobalScope 中启
    动。
     */
    fun print1() = runBlocking {
        launch {
            // 在 runBlocking 作用域中启动新协程
            delay(1000)
            println("word!")
        }
        println("hello ")

    }


    fun print2() = runBlocking {
        launch { printWord() }
        println("hello ")
    }

    //提取挂起函数
    suspend fun printWord() {
        delay(1000)
        println("word!")
    }


    /**
     * 协程的取消是 协作 的。一段协程代码必须协作才能被取消。 所有 kotlinx.coroutines 中的
    挂起函数都是 可被取消的 。它们检查协程的取消， 并在取消时抛出
    CancellationException。 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么
    它是不能被取消的，

    有两种方法来使执行计算的代码可以被取消。第一种方法是定期调用挂起函数来检查取
    消。对于这种目的 yield 是一个好的选择。 另一种方法是显式的检查取消状态。
     */
    fun print3() = runBlocking {
        //sampleStart
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // 可以被取消的计算循环
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // 等待一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该任务并等待它结束
        println("main: Now I can quit.")
        //sampleEnd
    }

    /**
     * 超时
     */
    fun print4() = runBlocking {
        var result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
            "Done"
        }

        println("result is $result")
    }


    fun measureTime() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val a = doSomeThing1()
            val b = doSomeThing2()
            println("The answer is ${a + b}")
        }
        println("Completed in $time ms")
    }

    suspend fun doSomeThing1(): Int {
        delay(1000L)
        return 1;
    }

    suspend fun doSomeThing2(): Int {
        delay(1000L)
        return 2;
    }
}