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
lateinit var noteArray: Array<String> // names of notes
lateinit var duration: Array<Double>   //duration of the notes
lateinit var stringObject: Array<GuitarString>

var n: Int = 0 //number of notes in the composition
var fp = 0
var crotchet = 1.5F
var fr = crotchet

var keyboard: String = "asq2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/']{~"

fun main(args: Array<String>) {
    if (args.isEmpty()){
        println("Please provide a file")
        System.exit(-1)
    } 
    if (args.size == 2){
        crotchet = args[1].toFloat()
    }

    val file = java.io.FileReader(args[0])
    val scanner = java.util.Scanner(file)

    n = scanner.nextInt()

    noteArray = Array<String>(n){" "}
    duration = Array<Double>(n){0.0}

    for (i in 0 until n){
            noteArray[i] = scanner.next()
            duration[i] = scanner.nextDouble()
    }

    PApplet.main("GuitarHeroExtra")
}

class GuitarHeroExtra : PApplet() {
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
        stringObject = Array<GuitarString>(41){GuitarString(CONCERT_A)}

        for (i in 0..40){
            stringObject[i] = GuitarString(CONCERT_A * 2F.pow((i.toFloat() - 26F)/12F))
        }

        frameRate(crotchet)
    }

    override fun draw() {
        fr = crotchet * (2F).pow(duration[fp].toFloat())
        frameRate(fr)
        background(0)
        stroke(255)
        for (i in 0 until (out.bufferSize() - 1)){
            var x1 = map( i.toFloat(), 0F, out.bufferSize().toFloat(), 0F, width.toFloat())
            var x2 = map( i.toFloat()+1F, 0F, out.bufferSize().toFloat(), 0F, width.toFloat())
            line( x1, 50F + out.left.get(i)*50F, x2, 50F + out.left.get(i+1)*50F )
            line( x1, 150F + out.right.get(i)*50F, x2, 150F + out.right.get(i+1)*50F )
        }
    
        
        for (i in 0 until noteArray[fp].length){
            stringObject[keyboard.indexOf((noteArray[fp])[i])].pluck()
        }

        if (fp == (noteArray.size -1)){
            exit()
        }
        fp ++
    }
}


