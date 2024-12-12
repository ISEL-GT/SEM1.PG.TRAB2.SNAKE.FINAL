package com.github.iselg1.snake_game.snake

import com.github.iselg1.snake_game.common.Direction
import com.github.iselg1.snake_game.common.Position

/**
 * This class is responsible for handling any snake-related operations, controlling a cluster of snake parts
 * @param body The cluster of snake parts being controlled
 * @param direction The direction the snake's head is currently going in
 * @param stopped Stop the movement of snake's parts excluding the head until toGrow reaches 0
 * @param toGrow Number of elements that the snake needs to add to body
 */
data class Snake(val body: List<SnakePart>, val direction: Direction, val stopped: Boolean, val toGrow: Int)

/**
 * Gets the current snake with no body parts in it
 * @return A snake with no body parts in it
 */
fun Snake.clear() : Snake = Snake(emptyList(), this.direction, false, 0)

/**
 * Adds a snake part into the list of parts after the head, defaulting its settings to the head's.
 *
 * @param type The type of snake part to be added into the body
 * @param position The position of the snake part
 * @param direction The direction of the snake part
 *
 * @return A new snake instance containing the part applied after the head
 */
fun Snake.plus(type: SnakePartType, position: Position = this.body[0].position, direction: Direction = this.direction) : Snake {

    val part = SnakePart(type, position, direction)
    if (this.body.isEmpty()) return Snake(listOf(part), this.direction, this.stopped, this.toGrow);

    val newBody = this.body.take(1) + part + this.body.drop(2)

    return Snake(newBody, direction, this.stopped, this.toGrow)
}

/**
 * Creates a new snake instance with a new direction for writing simplicity
 *
 * @return A new snake instance with its direction changed
 */
fun Snake.withDirection(direction: Direction) : Snake = Snake(this.body, direction, this.stopped, this.toGrow)