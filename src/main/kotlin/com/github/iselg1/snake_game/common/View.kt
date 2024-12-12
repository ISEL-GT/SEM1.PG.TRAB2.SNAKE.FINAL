package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.SQUARE_DIMENSIONS
import com.github.iselg1.snake_game.arena
import com.github.iselg1.snake_game.snake.Snake
import com.github.iselg1.snake_game.snake.SnakePart
import com.github.iselg1.snake_game.snake.getSprite
import com.github.iselg1.snake_game.snake.getString
import pt.isel.canvas.Canvas

// The filename constants for the resources (We can't have configuration files yet)
const val BRICK_SPRITE = "bricks"
const val SNAKE_SPRITE = "snake"
const val SNAKE_SIZE = 64

/**
 * Draws every brick in the game inside the arena
 * @param arena The canvas to draw the game on
 */
fun Game.drawBricks(arena: Canvas) {

    for (brick in this.bricks) {
        arena.drawImage(BRICK_SPRITE, brick.x * SQUARE_DIMENSIONS, brick.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
    }
}

/**
 * Erases the snake parts and rebuilds the bricks
 */
fun Game.eraseSnake() {
    arena.erase()
    drawBricks(arena)
}

/**
 * Draws every snake part in the game inside the arena
 * @param snakes The list of snakes to draw in the arena
 * @param arena The canvas to draw the snakes in
 */
fun Game.drawSnake(snakes: List<SnakePart>, arena: Canvas) {
    this.eraseSnake()

    for (snake in snakes) {
        val sprite = snake.getSprite().getString()
        arena.drawImage(sprite, snake.position.x * SQUARE_DIMENSIONS, snake.position.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
    }
}

