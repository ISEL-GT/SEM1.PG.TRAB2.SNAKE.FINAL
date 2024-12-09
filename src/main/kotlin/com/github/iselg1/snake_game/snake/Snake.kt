package com.github.iselg1.snake_game.snake

import com.github.iselg1.snake_game.common.Direction

/**
 * This class is responsible for handling any snake-related operations, controlling a cluster of snake parts
 * @param body The cluster of snake parts being controlled
 * @param direction The direction the snake's head is currently going in
 * @param stopped Stop the movement of snake's parts excluding the head until toGrow reaches 0
 * @param toGrow Number of elements that the snake needs to add to body
 */
data class Snake(val body: List<SnakePart>, val direction: Direction, val stopped: Boolean, val toGrow: Int)