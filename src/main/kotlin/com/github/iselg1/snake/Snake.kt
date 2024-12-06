package com.github.iselg1.snake

/**
 * This enum holds the different types of snake sprite variations that exist
 */
enum class SnakeType {
    HEAD,
    TAIL,
    TORSO
}

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

/**
 * This class is responsible for handling any snake-related operations
 * @param type The snake variant, or the body part it represents
 * @param position The snake position with coordinates (x,y)
 * @param direction The direction the part is facing.
 */
data class Snake(val type: SnakeType, val position: Position, val direction: Direction)

/**
 * Puts together the name of the sprite based on the snake information and
 * returns the SnakeSprite enum based on that information.
 *
 * @return A SnakeSprite object based on the current snake information
 * @see com.github.iselg1.snake.SnakeSprite
 */
fun Snake.getSprite(): SnakeSprite {
    return SnakeSprite.valueOf(this.type.name + "_" + this.direction.name)
}