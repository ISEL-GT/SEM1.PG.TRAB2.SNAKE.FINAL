package com.github.iselg1.snake_game.snake

import com.github.iselg1.snake_game.common.SNAKE_SIZE
import com.github.iselg1.snake_game.common.SNAKE_SPRITE

// The size in pixels for the sprite grid blocks
const val SPRITE_GRID_SIZE = 64

/**
 * This enum holds the different sprites that exist in the snake.png file
 * @param x The x position of the top-left corner of the selected area, within a grid
 * @param y The y position of the top-left corner of the selected are, within a grid
 */
enum class SnakeSprite(val x: Int, val y: Int) {
    HEAD_UP(3, 0),
    HEAD_DOWN(4, 1),
    HEAD_RIGHT(4, 0),
    HEAD_LEFT(3, 1),

    TORSO_UP(2, 1),
    TORSO_DOWN(2, 1),
    TORSO_LEFT(1, 0),
    TORSO_RIGHT(1, 0),

    TORSO_UP_RIGHT(0, 0),
    TORSO_UP_LEFT(2, 0),
    TORSO_DOWN_RIGHT(0, 1),
    TORSO_DOWN_LEFT(2, 2),
    TORSO_RIGHT_UP(2, 2),
    TORSO_RIGHT_DOWN(2, 0),
    TORSO_LEFT_UP(0, 1),
    TORSO_LEFT_DOWN(0, 0),


    TAIL_UP(3, 2),
    TAIL_DOWN(4, 3),
    TAIL_RIGHT(4, 2),
    TAIL_LEFT(3, 3),

    APPLE(0,3)
}
/**
 * Generates a string to be used while drawing the snake sprite, with the correct square
 * dimensions
 *
 * @return A string in the "file|x,y,height,width" format
 */
fun SnakeSprite.getString(): String {
    return "$SNAKE_SPRITE|${this.x*SPRITE_GRID_SIZE},${this.y*SPRITE_GRID_SIZE},$SNAKE_SIZE,$SNAKE_SIZE"
}
