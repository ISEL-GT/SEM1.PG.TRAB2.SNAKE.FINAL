package com.github.iselg1.snake_game.snake

import com.github.iselg1.snake_game.NUTRITION
import com.github.iselg1.snake_game.common.Direction
import com.github.iselg1.snake_game.common.Position
import com.github.iselg1.snake_game.common.applyDirection

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
 * Sets the snake as "having eaten an apple", so that it grows.
 */
fun Snake.eat() : Snake = Snake(this.body, this.direction, true, toGrow + NUTRITION)

/**
 * Decrements the growing counter and updates the stopped variable, to know when to allow the snake to move
 */
fun Snake.digest() : Snake = Snake(this.body, this.direction, this.toGrow > 0, this.toGrow - 1)

/**
 * Creates a new snake instance with a new direction for writing simplicity
 *
 * @return A new snake instance with its direction changed
 */
fun Snake.withDirection(direction: Direction) : Snake = Snake(this.body, direction, this.stopped, this.toGrow)

/**
 * Checks whether the snake is currently occupying a specified position
 * @return Whether a position is occupying by one of the snake's parts
 */
fun Snake.contains(position: Position) : Boolean = this.body.map { part -> part.position }.contains(position)

/**
 * Adds a snake part into the list of parts after the head, defaulting its settings to the head's.
 *
 * @param type The type of snake part to be added into the body
 * @param position The position of the snake part
 * @param direction The direction of the snake part
 *
 * @return A new snake instance containing the part applied after the head
 */
fun Snake.plus(type: SnakePartType, position: Position = this.body[0].position, direction: Direction = this.direction, append: Boolean = false) : Snake {

    val part = SnakePart(type, position, direction)
    if (this.body.isEmpty()) return Snake(listOf(part), this.direction, this.stopped, this.toGrow);

    // If we want the snake part to be explicitly placed at the end of the snake, then specify append
    if (append) {
        return Snake(this.body.plus(SnakePart(type, position, direction)), this.direction, this.stopped, this.toGrow)
    }

    val newBody = this.body.take(1) + part + this.body.drop(1)
    return Snake(newBody, direction, this.stopped, this.toGrow)
}

/**
 * Generates a list of snake parts, with a new torso part added after the head.
 * For this to happen, no snake parts will move except the head.
 *
 * @return A list of snake parts with a new torso on index 1
 */
fun Snake.extend() : Snake {

    // The next head position of the snake
    val headPosition = this.body.first().position.applyDirection(this.direction)

    val newHeadPart = SnakePart(SnakePartType.HEAD, headPosition, this.direction)
    val newSnake = this.plus(SnakePartType.TORSO, this.body.first().position, this.body.first().direction)

    return Snake(listOf(newHeadPart) + newSnake.body.drop(1), this.direction, this.stopped, this.toGrow)
}