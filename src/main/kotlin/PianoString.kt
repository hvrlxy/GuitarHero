/**
 * file: GuitarString.kt
 *
 * DON'T FORGET TO WRITE YOUR HEADER.
 */

@file:Suppress("DEPRECATION")

import kotlin.math.roundToInt
import ddf.minim.*
import kotlin.random.*
import processing.core.*

const val DECAY_FACTOR = 0.996F // energy decay factor

class PianoString(pitch: Float) : AudioSignal {
    // DECLARE THE DATA STRUCTURE FOR RING BUFFER HERE
    var ringBuffer = FloatArray((out.sampleRate()/pitch).roundToInt()){0F}
    var frontPointer = 0

    init {
        // CODE FOR THE CONSTRUCTOR GOES HERE
        out.addSignal(this)
    }

    // The following two methods: pluck() & generate() are the heart of
    // the Karplus-Strong algorithm.

    // Fills the buffer with random noise.
    fun pluck() {
        // YOUR CODE GOES HERE
        for (i in 0 until ringBuffer.size){
            ringBuffer[i] = (Random.nextFloat() - 0.5F)
        } 
    }

    // Fills the [signal] array with samples from buffer and updates
    // the buffer according to KS algorithm.
    override fun generate(signal: FloatArray){
        // YOUR CODE GOES HERE
        for (i in 0 until signal.size){
            ringBuffer[frontPointer] = DECAY_FACTOR * 0.5F * (ringBuffer[frontPointer] + ringBuffer[(frontPointer + 1)%(ringBuffer.size)])
            signal[i] = ringBuffer[frontPointer]
            if (frontPointer != ringBuffer.size - 1){
                frontPointer ++
            }
            else{
                frontPointer = 0
            }
        }
    }

    // AudioSignal requires both mono and stereo generate functions.
    override fun generate(left: FloatArray, right: FloatArray) {
        // YOUR CODE GOES HERE
        generate(left)
        for (i in 0 until left.size){
            right[i] = left[i]
        }
    }
}
