package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.utils.symmetric


/**
 * This class is responsible for holding the direction vectors to be used by the snake
 * @param x Abscissa of the vector direction
 * @param y Ordinate of the vector direction
 */
enum class Direction(val x: Int, val y: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0)
}
/**
 * Since there's symmetrical relationship between values in opposite directions, if we
 * multiply them by -1 and land on the other, then they're opposites.
 *
 * @param direction Vector with coordinates (x,y)
 * @return Whether the two directions being compared are opposites or not
 */
fun Direction.isOpposite(direction: Direction): Boolean {

    val oppositeX = (symmetric(this.x) == direction.x) && this.x != 0
    val oppositeY = (symmetric(this.y) == direction.y) && this.y != 0
    return oppositeX || oppositeY
}

/**
 * Checks if two directions are perpendicular to each other by checking if their scalar equals to 0
 * @return Whether the directions are perpendicular
 */
fun Direction.isPerpendicular(direction: Direction) : Boolean = (this.x * direction.x) + (this.y * direction.y) == 0

/**
 * This enum is responsible for mapping the relevant key codes into directions
 */
enum class DirectionMappings(val direction: Direction, val keys: List<Int>) {
    LEFT_MAP(Direction.LEFT, keys = listOf(37, 65)),
    UP_MAP(Direction.UP, keys = listOf(38, 87)),
    RIGHT_MAP(Direction.RIGHT, keys = listOf(39, 68)),
    DOWN_MAP(Direction.DOWN, keys = listOf(40, 83)),
}

/**
 * Gets the direction associated with the given key
 *
 * @param keyCode The key code to check for
 * @return The direction associated with the key code
 */
fun getDirectionFor(keyCode: Int): Direction? {

    // Iterate over every mapping and check if our key is within it
    for (mapping in DirectionMappings.entries) {
        if (mapping.keys.contains(keyCode)) return mapping.direction
    }

    return null
}

