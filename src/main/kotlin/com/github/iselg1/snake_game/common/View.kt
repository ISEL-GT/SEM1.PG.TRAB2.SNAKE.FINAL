package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.*
import com.github.iselg1.snake_game.snake.*
import pt.isel.canvas.*

// The filename constants for the resources (We can't have configuration files yet)
const val BRICK_SPRITE = "bricks"
const val SNAKE_SPRITE = "snake"
const val SNAKE_SIZE = 64
const val GRAY = 0x404040

/**
 * Draws every brick in the game inside the arena
 * @param arena The canvas to draw the game on
 */
fun Game.drawBricks() {

    for (brick in this.bricks) {
        arena.drawImage(BRICK_SPRITE, brick.x * SQUARE_DIMENSIONS, brick.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
    }
}

/**
 * Erases the snake parts and rebuilds the bricks
 */
fun Game.eraseSnake() {
    arena.erase()
    drawBricks()
    drawApple()
    drawLabel()
}

/**
 * Draws every snake part in the game inside the arena
 * @param body The list of snakes to draw in the arena
 * @param arena The canvas to draw the snakes in
 */
fun Game.drawSnake(body: List<SnakePart>) {
    this.eraseSnake()

    for (part in body) {
        val sprite = part.getSprite().getString()
        arena.drawImage(sprite, part.position.x * SQUARE_DIMENSIONS, part.position.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
    }
}


/**
 * Draws an apple within the screen based on the position in the game's apple property
 */
fun Game.drawApple() {
    val sprite = SnakeSprite.APPLE.getString()
    arena.drawImage(sprite,this.apple.x * SQUARE_DIMENSIONS, this.apple.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
}

/**
 * Draws the statistics label to be placed at the bottom of the screen, with the size and score count
 */
fun Game.drawLabel() {

    arena.drawRect(0, BOARD_HEIGHT * SQUARE_DIMENSIONS, BOARD_WIDTH * SQUARE_DIMENSIONS, LABEL_HEIGHT, GRAY)

    // Draws the snake's size counter
    val snakeSize = this.snake.body.size
    val sizeColour = if (snakeSize > 60) GREEN else WHITE
    arena.drawText(SQUARE_DIMENSIONS, (BOARD_HEIGHT * SQUARE_DIMENSIONS) + (LABEL_HEIGHT / 2) + 10, "Size: $snakeSize", sizeColour)

    // Draws the score counter
    arena.drawText(arena.width - SQUARE_DIMENSIONS*6, (BOARD_HEIGHT * SQUARE_DIMENSIONS) + (LABEL_HEIGHT / 2) + 10, "Score: ${this.score}", WHITE)
}

