package com.github.iselg1.snake

import kotlin.random.Random

/**
 * This class is responsible for holding x and y positions to be used throughout the program
 * @param x Abscissa for position in grid
 * @param y Ordinate for position in grid
 */
data class Position(val x: Int, val y: Int)

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
 * Checks for the first entry in the directions that has symmetrical values to the one being checked,
 * meaning it is its opposite.
 * @return Returns the opposite direction to this one
 */
fun Direction.getOpposite(): Direction {
    return Direction.entries.first { entry -> (entry.x == symmetric(this.x)) && (entry.y == symmetric(this.y)) }
}

/**
 * Maps the relevant key codes into directions to be used
 */
enum class DirectionMappings(val direction: Direction, val keys: Array<Int>) {
    LEFT_MAP(Direction.LEFT, keys = arrayOf(37, 65)),
    UP_MAP(Direction.UP, keys = arrayOf(38, 87)),
    RIGHT_MAP(Direction.RIGHT, keys = arrayOf(39, 68)),
    DOWN_MAP(Direction.DOWN, keys = arrayOf(40, 83)),
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

/**
 * Checks if the positional values between this and another position are the same
 * @param position Any other position to compare this one to
 *
 * @return Whether the X and Y values for both positions are the same
 */
fun Position.isEqual(position: Position): Boolean {
    return this.x == position.x && this.y == position.y
}

/**
 * Checks if this position exists anywhere inside the given list
 * @param positions A list of positions to check the for the existence of this one
 */
fun Position.exists(positions: List<Position>): Boolean {

    for (position in positions)
        if (position.isEqual(this)) return true

    return false
}

/**
 * Calculates a position based on the vector of the given direction, and loops around
 * the boundaries of the canvas
 * @param direction Vector used to calculate the new position
 * @param factor A constant to multiply the weight of the vector
 * @return A new position with the vector applied
 */
fun Position.applyDirection(direction: Direction, factor: Double = 1.0): Position {

    var y = this.y + (direction.y * factor).toInt()
    var x = this.x + (direction.x * factor).toInt()

    when {
        x >= BOARD_WIDTH -> x = 0  // Wraparound: right edge to left edge
        x < 0 -> x = BOARD_WIDTH - 1  // Wraparound: left edge to right edge
        y >= BOARD_HEIGHT -> y = 0  // Wraparound: bottom edge to top edge
        y < 0 -> y = BOARD_HEIGHT - 1  // Wraparound: top edge to bottom edge
    }

    return Position(x, y)
}

/**
 * Forces the application of a direction without the boundary checks
 * @param direction Vector used to calculate the new position
 * @param factor A constant to multiply the weight of the vector
 * @return A new position with the vector applied
 */
fun Position.forceApplyDirection(direction: Direction, factor: Double = 1.0): Position {

    val x = this.x + (direction.x * factor).toInt()
    val y = this.y + (direction.y * factor).toInt()

    return Position(x, y)
}

/**
 * Generates a new random position between 0 and the constants of the board
 *
 * @return A random coordinate position (x,y)
 */
fun randomPosition(): Position {

    val x = Random.nextInt(0, BOARD_WIDTH)
    val y = Random.nextInt(0, BOARD_HEIGHT)

    return Position(x, y)
}

/**
 * Returns the symmetrical number to the one provided
 *
 * @param x An integer to ge the symmetrical for
 * @return The symmetrical of x
 */
fun symmetric(x: Int): Int {
    return x * -1
}
