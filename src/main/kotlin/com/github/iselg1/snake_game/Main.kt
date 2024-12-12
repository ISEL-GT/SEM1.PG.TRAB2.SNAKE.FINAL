package com.github.iselg1.snake_game

import com.github.iselg1.snake_game.common.*
import com.github.iselg1.snake_game.snake.*
import pt.isel.canvas.*

// The global tick speed. The higher this is, the faster everything goes.
const val GLOBAL_TICK_SPEED = 1

// The different ticking speed for both the snake and brick actions (every x ms)
const val SNAKE_TICK_SPEED = 250
const val BRICK_TICK_SPEED = 5 * 1000

// The width of the board measured in grid squares
const val BOARD_WIDTH = 20
const val BOARD_HEIGHT = 16
const val TOTAL_SQUARES = BOARD_HEIGHT * BOARD_WIDTH

// The size in pixels of one of the square's sides
const val SQUARE_DIMENSIONS = 32

// The main arena and game where everything will be drawn on
val arena = Canvas(BOARD_WIDTH * SQUARE_DIMENSIONS, BOARD_HEIGHT * SQUARE_DIMENSIONS, BLACK)
var game = emptyGame()

// Acts as a queue for inputs, in case the user sends multiple too quickly
const val MAX_INPUTS = 1

/**
 * Main entrypoint for the program, kick off the main logic flow and spawn the snake
 */
fun main() {


    onStart {

        // Spawns the snake and ticks the bricks once
        spawnSnake()

        // Starts the ticking process for both the snake and the brick
        arena.onTimeProgress(SNAKE_TICK_SPEED * (1 / GLOBAL_TICK_SPEED)) { onSnakeTick() }
        arena.onTimeProgress(BRICK_TICK_SPEED * (1 / GLOBAL_TICK_SPEED)) { onBrickTick() }
        arena.onKeyPressed { key -> onKeyPressed(key) }
    }

    onFinish { }
}

/**
 * Spawns in the snake, creating a head-typed snake part and a tail.
 */
fun spawnSnake() {

    // Create the snake head and add it to the snake parts
    val centerHeight = BOARD_HEIGHT / 2

    var newSnake = game.snake.plus(SnakePartType.HEAD, Position(1, centerHeight))
    newSnake = newSnake.plus(SnakePartType.TAIL, Position(0, centerHeight), game.snake.direction)

    game = game.withSnake(newSnake)
}

/**
 * Fired whenever a key is pressed;
 * Checks if the keys are WASD/UP, DOWN, LEFT, RIGHT, and changes the direction accordingly.
 * @param key Use the pressed key to change de direction
 */
fun onKeyPressed(key: KeyEvent) {

    // Get the direction associated with the key pressed.
    // If the key isn't mapped to a direction, return.
    val direction = getDirectionFor(key.code) ?: return

    // Prevent the snake from moving in opposite directions
    if (game.snake.direction.isOpposite(direction)) return;
    game = game.withSnake(game.snake.withDirection(direction))
}

/**
 * Fired whenever a brick drawing event occurs;
 * Clears the arena and draws all the bricks in their positions, generating one more brick
 */
fun onBrickTick() {

    // Checks if all the squares have been filled, and if so, stops generating them
    if (game.bricks.size == TOTAL_SQUARES - game.snake.body.size) return

    // Generates a new brick and draws it on screen
    game = game.generateNewBrick()
    game.drawBricks(arena)
}

/**
 * Fired whenever a snake drawing event occurs
 * Clears the arena and draws the snake parts in their positions
 */
fun onSnakeTick() {

    // Get the queued input, or maintain the current direction if there's none
    val directionInput = game.snake.direction
    val nextHeadPosition = game.snake.body.first().position.applyDirection(directionInput)

    // If the snake is about to "snap its neck" with a bad move, ignore the last input.
    val correctedInput = if (nextHeadPosition.exists(game.bricks)) game.snake.body.first().direction else directionInput
    game = game.withSnake(game.snake.withDirection(correctedInput))


    // If the next position contains a brick, stop the snake.
    if (game.bricks.contains(nextHeadPosition)) return

    // Updates and draws the snake on screen
    game = game.withSnake(game.calculateSnakeMovement(directionInput, nextHeadPosition))
    game.drawSnake(game.snake.body, arena)
}

