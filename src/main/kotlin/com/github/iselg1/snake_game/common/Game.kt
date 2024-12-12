package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.*
import com.github.iselg1.snake_game.snake.*

/**
 * A main control data class used to save the positions of everything in the game
 * @param snake The object controlling the active snake parts
 * @param bricks The positions of every brick in the game
 * @param apple The object used to add length to snake's body and score
 * @param score The value of points gained by eating apples during the game
 */
data class Game(val snake: Snake, val bricks: List<Position>, val apple: Position, val score: Int)

/**
 * @return An empty game object to be used as a dummy
 */
fun emptyGame() : Game = Game(Snake(emptyList(), Direction.RIGHT, false, 0), emptyList(), Position(0,0), 0)

/**
 * Creates a new game instance with a new snake for writing simplicity
 *
 * @return A Game instance with a different snake in it
 */
fun Game.withSnake(snake: Snake) : Game = Game(snake, this.bricks, this.apple, this.score)

/**
 * Generates a new brick with a random position
 *
 * @return A position object with the new brick coordinates
 */
fun Game.generateNewBrick(): Game {

    // Generates a random position
    val brickPosition = randomPosition()
    val snakePositions = this.snake.body.map { part -> part.position }

    // Recursively generates a brick position that hasn't been used yet
    if (brickPosition.exists(this.bricks) || brickPosition.exists(snakePositions))
        return this.generateNewBrick()

    // Returns the new brick set made from this new brick position
    val newBricks = this.bricks.plus(brickPosition)
    return Game(this.snake, newBricks, this.apple, this.score)
}

/**
 * Calculates the positions for every snake part based on the given direction. The movement
 * will be relative to the head of the snake.
 *
 * @param direction The direction to move the head of the snake in
 * @param headPosition The position where the head should be next in
 *
 * @return A snake object with its new parts in the calculated positions
 */
fun Game.calculateSnakeMovement(direction: Direction, headPosition: Position) : Snake {

    // Creates a base array to return and adds the head at the next position
    var newSnake = this.snake.clear()
    newSnake = newSnake.plus(SnakePartType.HEAD, headPosition, direction)

    // Iterates through all the snake's parts, applying the direction of the previous part.
    // Ignores the tail, because that's handled differently
    for ((index, snake) in this.snake.body.withIndex()) {

        // Skip the head, that's handled differently, but we need it in the list to get its direction
        if (snake == this.snake.body.first()) continue

        // Handles the tail by setting it as the previous last position with the new orientation
        if (snake == this.snake.body.last()) {
            newSnake = newSnake.plus(snake.type, this.snake.body.dropLast(1).last().position, newSnake.body.last().direction)
            continue
        }

        val lastSnake = this.snake.body[index-1]
        newSnake = newSnake.plus(snake.type, lastSnake.position, lastSnake.direction)
    }

    return newSnake
}