package com.xp.learn.widget

fun main() {
    val nums= generator {start:Int->

    }

}

interface Generator<T> {
    operator fun iterator(): Iterable<T>
}

class GeneratorImpl<T>(private val block: suspend (T) -> Unit, private val parameter: T) :
    Generator<T> {
    override fun iterator(): Iterable<T> {

    }

}

fun <T> generator(block: suspend (T) -> Unit): (T) -> Generator<T> {
    return { parameter: T ->
        GeneratorImpl(block,parameter)
    }
}