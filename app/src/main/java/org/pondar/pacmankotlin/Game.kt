package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import android.widget.Toast
import java.util.*


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {

        private var pointsView: TextView = view
        private var points = 0
        //bitmap of the pacman
        var pacBitmap: Bitmap
        var pacx  = 0
        var pacy = 0

        var coinBitmap: Bitmap


        //did we initialize the coins?
        var coinsInitialized = false

        //the list of goldcoins - initially empty
        var coins = ArrayList<GoldCoin>()

        //a reference to the gameview
        private var gameView: GameView? = null
        private var h = 0
        private var w = 0 //height and width of screen

        var counter = 0
        var running = false
        var direction = 1


    //The init code is called when we create a new Game class.
    //it's a good place to initialize our images.
    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
        coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dollar)

    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins()
    {
        var minX = 0
        var maxX = w - coinBitmap.width

        var minY = 0
        var maxY = h - coinBitmap.width

        var random = Random()

        for (i in 0..1) {
            var randomX = random.nextInt( maxX - minX + 1) + minX
            var randomY = random.nextInt( maxY - minY + 1) + minY
            coins.add(GoldCoin(randomX, randomY, false))
        }




        //DO Stuff to initialize the array list with some coins.

        coinsInitialized = true
    }


    fun newGame() {
        pacx = 50
        pacy = 400 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
        coins.clear()
    }
    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }




    fun movePacmanRight(pixels: Int) {

        //still within our boundaries?
        if (pacx + pixels + pacBitmap.width < w) {
            pacx = pacx + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanLeft(pixels: Int) {
        //still within our boundaries?
        if (pacx > 0) {
            pacx = pacx - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanUp(pixels: Int) {
        //still within our boundaries?
        if (pacy > 0) {
            pacy = pacy - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanDown(pixels: Int) {
        //still within our boundaries?
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }



    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {

        for (coin in coins) {
            if (pacx + pacBitmap.width >= coin.coinX && pacx <= coin.coinX + coinBitmap.width && pacy + pacBitmap.height >= coin.coinY && pacy <= coin.coinY + coinBitmap.height && !coin.taken) {
                Toast.makeText(this.context, "Yum yum yem", Toast.LENGTH_SHORT).show()
                coin.taken
                points += 1
                pointsView.text = "${context.resources.getString(R.string.points)} $points"
            }

            if (points == coins.size || coin.taken == null ) {
                return newGame()
            }




        }

    }



}