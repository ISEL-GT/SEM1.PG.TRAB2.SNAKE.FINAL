package com.github.iselg1.snake_game.snake

import com.github.iselg1.snake_game.common.SNAKE_SIZE
import com.github.iselg1.snake_game.common.SNAKE_SPRITE

/**
 * This enum holds the different sprites that exist in the snake.png file
 * @param x The x position of the top-left corner of the selected area
 * @param y The y position of the top-left corner of the selected are
 */
enum class SnakeSprite(val x: Int, val y: Int) {
    HEAD_UP(192, 0),
    HEAD_DOWN(256, 64),
    HEAD_RIGHT(256, 0),
    HEAD_LEFT(192, 64),

    TORSO_UP(128, 64),
    TORSO_DOWN(128, 64),
    TORSO_LEFT(66, 0),
    TORSO_RIGHT(66, 0),

    CORNER(0, 0),

    TAIL_UP(192, 128),
    TAIL_DOWN(256, 192),
    TAIL_RIGHT(256, 128),
    TAIL_LEFT(192, 192)
}

/**
 * Generates a string to be used while drawing the snake sprite, with the correct square
 * dimensions
 *
 * @return A string in the "file|x,y,height,width" format
 */
fun SnakeSprite.getString(): String {
    return "$SNAKE_SPRITE|${this.x},${this.y},$SNAKE_SIZE,$SNAKE_SIZE"
}
