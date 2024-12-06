package com.github.iselg1.snake

/**
 *  A single snake part, representing both a type and its position
 *  @param position The snake position with coordinates (x,y)
 */
data class SnakePart(val type: SnakeType, val position: Position, val direction: Direction)

/**
 * Puts together the name of the sprite based on the snake information and
 * returns the SnakeSprite enum based on that information.
 *
 * @return A SnakeSprite object based on the current snake information
 * @see com.github.iselg1.snake.SnakeSprite
 */
fun SnakePart.getSprite(): SnakeSprite {
    return SnakeSprite.valueOf(this.type.name + "_" + this.direction.name)
}