package com.github.iselg1.snake_game.common

import com.github.iselg1.snake_game.*
import com.github.iselg1.snake_game.snake.*
import pt.isel.canvas.*

// The filename constants for the resources (We can't have configuration files yet)
const val BRICK_SPRITE = "bricks"
const val SNAKE_SPRITE = "snake"
const val SNAKE_SIZE = 64
const val GRAY = 0x404040

/**
 * Draws every brick in the game inside the arena
 * @param arena The canvas to draw the game on
 */
fun Game.drawBricks() {

    for (brick in this.bricks) {
        arena.drawImage(BRICK_SPRITE, brick.x * SQUARE_DIMENSIONS, brick.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
    }
}

/**
 * Erases the snake parts and rebuilds the bricks
 */
fun Game.eraseSnake() {
    arena.erase()
    drawBricks()
    drawApple()
    drawLabel()
}

/**
 * Draws every snake part in the game inside the arena
 * @param body The list of snakes to draw in the arena
 * @param arena The canvas to draw the snakes in
 */
fun Game.drawSnake(body: List<SnakePart>) {
    this.eraseSnake()

    for (i in body.indices) {

        // Gets the body part currently being iterated over
        val part = body[i]
        val leadingPart = if (i > 0) body[i - 1] else part

        // If the body part is a torso, check if the part before it is perpendicular to it
        // if so, draw the sprite equivalent to its correct corner
        if (part.type == SnakePartType.TORSO && part.direction.isPerpendicular(leadingPart.direction)) {

            // Create a list of sorted directions so we have a predictable order to use for the sprite collection
            val sprite = SnakeSprite.valueOf("${part.type}_${part.direction.name}_${leadingPart.direction.name}").getString()
            arena.drawImage(sprite, part.position.x * SQUARE_DIMENSIONS, part.position.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
            continue
        }

        // If the body is anything else, get its sprite and draw it
        val sprite = part.getSprite().getString()
        arena.drawImage(sprite, part.position.x * SQUARE_DIMENSIONS, part.position.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
    }
}


/**
 * Draws an apple within the screen based on the position in the game's apple property
 */
fun Game.drawApple() {
    val sprite = SnakeSprite.APPLE.getString()
    arena.drawImage(sprite,this.apple.x * SQUARE_DIMENSIONS, this.apple.y * SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS)
}

/**
 * Draws the statistics label to be placed at the bottom of the screen, with the size and score count
 */
fun Game.drawLabel() {

    arena.drawRect(0, BOARD_HEIGHT * SQUARE_DIMENSIONS, BOARD_WIDTH * SQUARE_DIMENSIONS, LABEL_HEIGHT, GRAY)

    // Draws the snake's size counter
    val snakeSize = this.snake.body.size
    val sizeColour = if (snakeSize >= 60) GREEN else WHITE
    arena.drawText(SQUARE_DIMENSIONS, (BOARD_HEIGHT * SQUARE_DIMENSIONS) + (LABEL_HEIGHT / 2) + 10, "Size: $snakeSize", sizeColour)

    // Draws the score counter
    arena.drawText(arena.width - SQUARE_DIMENSIONS*6, (BOARD_HEIGHT * SQUARE_DIMENSIONS) + (LABEL_HEIGHT / 2) + 10, "Score: ${this.score}", WHITE)

    if (!game.isSnakeBlocked()) return;   // Only continue if the game is over

    if (snakeSize >= 60) this.drawVictoryStatus("You Win!")
    else this.drawVictoryStatus("You Lose!")
}

/**
 * Draw the player's victory status at the center of the label.
 * @param status The status to draw
 */
fun Game.drawVictoryStatus(status: String) {
    arena.drawText((arena.width / 2) - SQUARE_DIMENSIONS*3, (BOARD_HEIGHT * SQUARE_DIMENSIONS) + (LABEL_HEIGHT / 2) + 10, status, YELLOW)

}

