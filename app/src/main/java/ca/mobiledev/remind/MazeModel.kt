package ca.mobiledev.remind

import android.util.Log
import java.util.Collections
import java.util.LinkedList
import java.util.PriorityQueue
import kotlin.math.abs

class MazeModel() {

    //6x7 grid
    val ROW= 7
    val COL= 6

    var solutionList: ArrayList<Int> = ArrayList()
    var selectedList: ArrayList<Int> = ArrayList()

    var nodeGraph: Graph = Graph(ROW, COL)

    var attempts = 3


    fun getSolution():ArrayList<Int>{
        return ArrayList(Collections.unmodifiableList(solutionList))
    }
    fun getSelected():ArrayList<Int>{
        return ArrayList(Collections.unmodifiableList(selectedList))
    }

    fun decAttempts(){
        attempts -= 1
    }

    fun addPoint(i: Int) {

        var indexofi = selectedList.indexOf(i)

        if(indexofi!=-1){ //if i is already in the list

            //remove all remaining elements in the list
            while(indexofi< (selectedList.size)){
                selectedList.remove(indexofi)
                indexofi++

                Log.d("MazeModel", "Removing point and proceeding points")
            }
        }
        else{

            //if nothing is selected
            if(selectedList.isEmpty()){
                selectedList.add(i)

                val rCurrent: Int=(i-1)/ROW
                val cCurrent: Int= (i-1)%COL

                Log.d("MazeModel", "Adding first point $rCurrent , $cCurrent!)")
            }

            /*//todo maybe remove this, covered in first case? if you only have one node selected (the same node and you press it again)
            else if(selectedList.size==1){
                selectedList.remove(i)
            }*/

            //if you select some other node (complex)
            else{
                //check whether it is a valid connection
                val last= selectedList[selectedList.size-1] //grab the last element

                val rLast: Int=(last-1)/COL
                val cLast: Int= (last-1)%COL

                val rCurrent: Int=(i-1)/COL
                val cCurrent: Int= (i-1)%COL


                if(nodeGraph.grid[rLast][cLast]?.neighbors?.contains(nodeGraph.grid[rCurrent][cCurrent]) == true){
                    selectedList.add(i)

                    Log.d("MazeModel", "Adding point $rCurrent , $cCurrent(neighbour of last point $rLast, $cLast)")

                }
                else{
                    selectedList.clear()
                    selectedList.add(i)

                    Log.d("MazeModel", "Not adding point $rCurrent , $cCurrent (not neighbour)")
                }

            }
        }
    }

    ////////////////////////////////////////

    val directions = arrayOf(
        -6,  // up
        6,   // down
        -1,  // left
        1,   // right
        -7,  // up-left diagonal
        -5,  // up-right diagonal
        5,   // down-left diagonal
        7    // down-right diagonal
    )

    // Convert button number to grid coordinates (6x7 grid)
    fun buttonToCoordinates(button: Int): Pair<Int, Int> {
        val row = (button - 1) / 6
        val col = (button - 1) % 6
        return Pair(row, col)
    }

    // Check if moving left or right stays within the same row (prevents row-jumping)
    fun sameRow(button1: Int, button2: Int): Boolean {
        return (button1 - 1) / 6 == (button2 - 1) / 6
    }

    // Check if the button number is valid (within the 1 to 42 range)
    fun isValid(button: Int): Boolean {
        return button in 1..42
    }

    // Manhattan distance heuristic
    fun heuristic(a: Int, b: Int): Int {
        val (rowA, colA) = buttonToCoordinates(a)
        val (rowB, colB) = buttonToCoordinates(b)
        return abs(rowA - rowB) + abs(colA - colB)
    }

    // Node class to store button and path information
    data class Node(val button: Int, val path: List<Int>)


    // A* Pathfinding function to find a path without backtracking
    fun aStarPath(start: Int, end: Int, visited: Set<Int>): List<Int> {
        val priorityQueue = PriorityQueue<Node>(compareBy { it.path.size }) // Use path length as priority
        priorityQueue.add(Node(start, listOf(start)))

        while (priorityQueue.isNotEmpty()) {
            val currentNode = priorityQueue.poll()
            val currentButton = currentNode.button
            val currentPath = currentNode.path

            // If we've reached the target button, return the path
            if (currentButton == end) return currentPath

            // Explore neighbors (up, down, left, right, and diagonals)
            for (direction in directions) {
                val nextButton = currentButton + direction

                // Ensure the move stays within bounds and does not wrap/jump rows
                if (isValid(nextButton)) {
                    val (currRow, currCol) = buttonToCoordinates(currentButton)
                    val (nextRow, nextCol) = buttonToCoordinates(nextButton)

                    // Horizontal moves: check for row continuity
                    if ((direction == -1 || direction == 1) && !sameRow(currentButton, nextButton)) continue

                    // Diagonal moves: check row and column continuity
                    if ((direction == -7 || direction == -5 || direction == 5 || direction == 7) &&
                        (abs(currRow - nextRow) != 1 || abs(currCol - nextCol) != 1)
                    ) continue

                    // Ensure the nextButton has not been visited
                    if (nextButton !in visited) {
                        val newPath = currentPath + nextButton
                        priorityQueue.add(Node(nextButton, newPath))
                    }
                }
            }
        }

        // If no valid path was found, return an empty list
        return emptyList()
    }

    // Function to find the full path from startButton to endButton via midButton
    fun findPathWithMid(startButton: Int, midButton: Int, endButton: Int): List<Int> {
        // Create a set of visited buttons initially containing startButton
        val visited = mutableSetOf(startButton)

        // Find path from startButton to midButton
        val pathToMid = aStarPath(startButton, midButton, visited)

        // If no valid path to mid, return empty
        if (pathToMid.isEmpty()) return emptyList()

        // Update visited to include path to mid
        visited.addAll(pathToMid)

        // Find path from midButton to endButton, reusing the updated visited set
        val pathToEnd = aStarPath(midButton, endButton, visited)

        // If no valid path to end, return empty
        if (pathToEnd.isEmpty()) return emptyList()

        // Combine paths, excluding the midButton from the second path to avoid duplication
        return pathToMid + pathToEnd.drop(1)
    }


    ////////////////////////////////////////////////////////

    init{
        var point1 = (1..42).random()
        var point2 = (1..42).random()
        var point3 = (1..42).random()

        while(point1 == point2 || point2 == point3){
            point1 = (1..42).random()
            point2 = (1..42).random()
            point3 = (1..42).random()
        }
            solutionList.addAll(findPathWithMid(point1, point2, point3))
        Log.w("points", "($point1, $point2, $point3)")
        Log.w("points", "Solution list: $solutionList")
    }
}