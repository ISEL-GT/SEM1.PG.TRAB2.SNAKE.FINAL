package com.github.iselg1.snake

/**
 * A main control data class used to save the positions of everything in the game
 * @param snakeParts A list of snake objects
 * @param bricks The positions of every brick in the game
 * @param direction The direction that the user wants to move to
 */
data class Game(val snakeParts: ArrayList<Snake>, val bricks: ArrayList<Position>, val direction: Direction)

/**
 * Generates a new brick with a random position
 *
 * @return A position object with the new brick coordinates
 */
fun Game.generateNewBrick(): Game {

    // Generates a random position
    val brickPosition = randomPosition()
    val snakePositions = this.snakeParts.map { snake -> snake.position }

    // Recursively generates a brick position that hasn't been used yet
    if (brickPosition.exists(this.bricks) || brickPosition.exists(snakePositions))
        return this.generateNewBrick()

    // Returns the new brick set made from this new brick position
    this.bricks.add(brickPosition)
    return Game(this.snakeParts, this.bricks, this.direction)
}

/**
 * Updates the internal list of Snake parts
 *
 * @param snakeParts A list of Snake objects to update the snakes list with
 */
fun Game.updateSnake(snakeParts: List<Snake>) {
    this.snakeParts.clear()
    snakeParts.forEach { part -> game.snakeParts.add(part) }
}

/**
 * Calculates the positions for every snake part based on the given direction. The movement
 * will be relative to the head of the snake.
 *
 * @param direction The direction to move the head of the snake in
 * @param headPosition The position where the head should be next in
 * @return A list of snake parts containing the snake objects with their new positions
 */
fun Game.calculateSnakeMovement(direction: Direction, headPosition: Position) : List<Snake> {

    // Creates a base array to return and adds the head at the next position
    val snakes = ArrayList<Snake>()
    snakes.add(Snake(SnakeType.HEAD, headPosition, direction))

    // Iterates through all the snake's parts, applying the direction of the previous part.
    // Ignores the tail, because that's handled differently
    for ((index, snake) in this.snakeParts.withIndex()) {

        // Skip the head, that's handled differently, but we need it in the list to get its direction
        if (snake == this.snakeParts.first()) continue

        // Handles the tail by setting it as the previous last position with the new orientation
        if (snake == this.snakeParts.last()) {
            snakes.add(Snake(snake.type, this.snakeParts.dropLast(1).last().position, snakes.last().direction))
            continue
        }

        val lastSnake = this.snakeParts[index-1]
        snakes.add(Snake(snake.type, lastSnake.position, lastSnake.direction))
    }

    return snakes
}