package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.BOARD_HEIGHT
import com.github.iselg1.snake_game.BOARD_WIDTH
import kotlin.random.Random

/**
 * This class is responsible for holding x and y positions to be used throughout the program
 * @param x Abscissa for position in grid
 * @param y Ordinate for position in grid
 */
data class Position(val x: Int, val y: Int)

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
 * Generates a new random position between 0 and the constants of the board
 *
 * @return A random coordinate position (x,y)
 */
fun randomPosition(): Position {

    val x = Random.nextInt(0, BOARD_WIDTH)
    val y = Random.nextInt(0, BOARD_HEIGHT)

    return Position(x, y)
}
