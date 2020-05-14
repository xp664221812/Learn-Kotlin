package com.xp.learn.widget

import kotlin.concurrent.thread
import kotlin.coroutines.*

fun main() {
    val nums = generator { start: Int ->
        for (i in 0..5) {
            yield(i + start)
        }
    }
    val seq = nums(10)

    println(seq)

    for (j in seq) {
        println(j)
    }

}

fun <T> generator(block: suspend GenerateScope<T>.(T) -> Unit): (T) -> Generator<T> {
    return { parameter: T ->
        GeneratorImpl(block, parameter)
    }
}

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(
    private val block: suspend GenerateScope<T>.(T) -> Unit,
    private val parameter: T
) : Generator<T> {
    override fun iterator(): Iterator<T> {
        return GeneratorIterator(block, parameter)
    }

}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>) : State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T) : State()
    object Done : State()
}


class GeneratorIterator<T>(
    private val block: suspend GenerateScope<T>.(T) -> Unit,
    override val parameter: T
) : GenerateScope<T>(), Iterator<T>, Continuation<Any?> {

    override val context: CoroutineContext = EmptyCoroutineContext

    private var state: State

    init {
        val suspendBlock: suspend GenerateScope<T>.() -> Unit = { block(parameter) }


        val start = suspendBlock.createCoroutine(this, this)

        state = State.NotReady(start)
    }


    override suspend fun yield(value: T) = suspendCoroutine<Unit> {
        state = when (state) {
            is State.NotReady -> State.Ready(it, value)
            is State.Ready<*> -> throw IllegalStateException("cannot yield a value while ready")
            State.Done -> throw IllegalStateException("cannot yield a value while done")
        }
    }

    private fun resume() {
        when (val currentState = state) {
            is State.NotReady -> currentState.continuation.resume(Unit)
        }
    }

    override fun hasNext(): Boolean {
        resume()
        return state != State.Done
    }

    override fun next(): T {
        return when (val currentState = state) {
            is State.NotReady -> {
                resume()
                return next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> throw IndexOutOfBoundsException("no value is left")
        }
    }


    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        result.getOrThrow()
    }

}


abstract class GenerateScope<T> internal constructor() {
    protected abstract val parameter: T

    abstract suspend fun yield(value: T)

}

suspend fun test1(block: suspend () -> Int) = suspendCoroutine<Int> {
    println("1111")
    it.resume(111)
}

suspend fun test2() = suspendCoroutine<Int> {
    it.resume(3)
}


