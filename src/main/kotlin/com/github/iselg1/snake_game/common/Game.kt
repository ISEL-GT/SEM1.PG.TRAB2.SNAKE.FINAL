package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.*
import com.github.iselg1.snake_game.snake.*
import pt.isel.canvas.Canvas

/**
 * A main control data class used to save the positions of everything in the game
 * @param snake The object controlling the active snake parts
 * @param bricks The positions of every brick in the game
 * @param apple The object used to add length to snake's body and score
 * @param score The value of points gained by eating apples during the game
 * @param queuedDirection The direction queued for processing
 */
data class Game(val snake: Snake, val bricks: List<Position>, val apple: Position, val score: Int, val queuedDirection: Direction = Direction.NONE)

/**
 * @return An empty game object to be used as a dummy
 */
fun emptyGame() : Game = Game(Snake(emptyList(), Direction.RIGHT, false, 0), emptyList(), Position(0,0), 0)

/**
 * Creates a new game instance with an incremented score value
 * @return A new game instance with +1 score
 */
fun Game.incrementScore() : Game = Game(this.snake, this.bricks, this.apple, this.score + 1)

/**
 * Creates a new game instance with a new snake for writing simplicity
 *
 * @return A Game instance with a different snake in it
 */
fun Game.withSnake(snake: Snake) : Game = Game(snake, this.bricks, this.apple, this.score)

/**
 * Creates a new game instance with a new list of brick positions
 * @return A Game instance with a new brick of positions
 */
fun Game.withBricks(positions: List<Position>) : Game = Game(this.snake, positions, this.apple, this.score)

/**
 * Creates a new game instance with a new apple position
 * @return A Game instance with a new apple position
 */
fun Game.withApple(position: Position) : Game = Game(this.snake, this.bricks, position, this.score)

/**
 * Creates a new game instance with a specified direction to be processed
 * @return A new game instance with a new queued direction
 */
fun Game.withDirectionQueue(direction: Direction) : Game = Game(this.snake, this.bricks, this.apple, this.score, direction)

/**
 * Generates a new apple somewhere within the screen
 * @param apple  The position of the object apple to draw in the arena
 * @param arena  The canvas to draw the apple in
 */
fun Game.newApple(arena: Canvas) {

    val snakePositions = this.snake.body.map { part -> part.position }
    val position = generateNewPosition(snakePositions + this.bricks)

    game = this.withApple(position)
    game.drawApple()
}

/**
 * Generates a new brick with a random position
 *
 * @return A position object with the new brick coordinates
 */
fun Game.generateNewBrick(): Game {

    // Generates a random position
    val position = randomPosition()

    // Keep recursively generating a position if the one generated exists within the exclusion list
    if (position.exists(this.bricks) || position.exists(exclude))
        return this.generateNewPosition(exclude)

    // Returns the new brick set made from this new brick position
    return position
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

    // If the snake is currently digesting food, then stop processing movement and extend it
    if (this.snake.stopped) return this.snake.extend().digest()

    // Creates a base array to return and adds the head at the next position
    var newSnake = this.snake.clear()
    newSnake = newSnake.plus(SnakePartType.HEAD, headPosition, direction)

    // Iterates through all the snake's parts, applying the direction of the previous part.
    // Ignores the tail, because that's handled differently
    for ((index, part) in this.snake.body.withIndex()) {

        // Skip the head, that's handled differently, but we need it in the list to get its direction
        if (part == this.snake.body.first()) continue

        // Handles the tail by setting it as the previous last position with the new orientation
        if (part == this.snake.body.last()) {
            newSnake = newSnake.plus(part.type, this.snake.body.dropLast(1).last().position, newSnake.body.last().direction, true)
            continue
        }

        val lastPart = this.snake.body[index-1]
        newSnake = newSnake.plus(part.type, lastPart.position, lastPart.direction, true)
    }

    return newSnake
}