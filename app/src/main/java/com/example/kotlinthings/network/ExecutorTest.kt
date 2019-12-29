package com.example.kotlinthings.network

import java.util.concurrent.Executor

class ExecutorTest : Executor {

    override fun execute(command: Runnable) {}
}

class runable : Runnable {
    override fun run() {

    }

}