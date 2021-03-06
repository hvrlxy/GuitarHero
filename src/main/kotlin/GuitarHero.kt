/**
 * file: GuitarHeroLite.kt
 *
 * DON'T FORGET TO WRITE THIS HEADER.
 */

@file:Suppress("DEPRECATION")

import kotlin.math.pow
import processing.core.*
import ddf.minim.*
import java.util.Scanner
import java.io.FileReader

const val CONCERT_A = 440.0F
lateinit var out: AudioOutput
fun main(args: Array<String>) {
    PApplet.main("GuitarHero")
}

class GuitarHero : PApplet() {
    override fun settings() {
        size(512, 200)
    }

    override fun setup() {
        background(0)
        val minim = Minim(this)

        /*
         * Gets a line out from Minim, default bufferSize is 1024,
         * default sample rate is 44100, bit depth is 16
         */
        out = minim.getLineOut(Minim.STEREO)
    }

    override fun draw() {
        background(0)
        stroke(255)
        for (i in 0 until (out.bufferSize() - 1)){
            var x1 = map( i.toFloat(), 0F, out.bufferSize().toFloat(), 0F, width.toFloat())
            var x2 = map( i.toFloat()+1F, 0F, out.bufferSize().toFloat(), 0F, width.toFloat())
            line( x1, 50F + out.left.get(i)*50F, x2, 50F + out.left.get(i+1)*50F ) //draw the first line
            line( x1, 150F + out.right.get(i)*50F, x2, 150F + out.right.get(i+1)*50F ) //draw the second line
        }
    }

    override fun keyPressed() {
        var keyboard: String = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' " //declare a string with all the notes being represented as letters

        var stringObject = Array<GuitarString>(37){GuitarString(CONCERT_A)}
        for (i in 0..36){
            stringObject[i] = GuitarString(CONCERT_A * 2F.pow((i.toFloat() - 24F)/12F))
        }

        if ((key in keyboard) || (key in keyboard.toUpperCase())){
            stringObject[keyboard.indexOf(key)].pluck() //when a key is plucked, make the sound corresponding to the key
        }
    }
}


