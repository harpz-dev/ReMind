package ca.mobiledev.remind

import android.util.Log
import java.util.Collections
import java.util.LinkedList

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

    // Directions for moving in the graph: up, down, left, right
    val directions = arrayOf(
        -6,  // up (move to button above)
        6,   // down (move to button below)
        -1,  // left (move to button on the left)
        1    // right (move to button on the right)
    )


    // Check if moving left or right stays within the same row (prevents row-jumping)
    fun sameRow(button1: Int, button2: Int): Boolean {
        return (button1 - 1) / 6 == (button2 - 1) / 6
    }

    // Check if the button number is valid (within the 1 to 42 range)
    fun isValid(button: Int): Boolean {
        return button in 1..42
    }

    // Function that returns the shortest path between two buttons as a list of integers
    fun shortestPath(startButton: Int, endButton: Int): List<Int> {
        val queue: LinkedList<Pair<Int, List<Int>>> = LinkedList()
        val visited = mutableSetOf<Int>()

        // Initialize BFS with the start point
        queue.add(Pair(startButton, listOf(startButton)))
        visited.add(startButton)

        while (queue.isNotEmpty()) {
            val (currentButton, path) = queue.poll()

            // If we've reached the end point, return the path
            if (currentButton == endButton) return path

            // Explore neighbors (up, down, left, right)
            for (direction in directions) {
                val nextButton = currentButton + direction

                // Ensure the move stays within bounds and does not wrap/jump rows
                if (isValid(nextButton) && nextButton !in visited) {
                    // Check for row-jumping on horizontal moves
                    if ((direction == -1 || direction == 1) && !sameRow(currentButton, nextButton)) continue

                    visited.add(nextButton)
                    queue.add(Pair(nextButton, path + nextButton))
                }
            }
        }

        // Return an empty list if there's no valid path
        return emptyList()
    }
    ////////////////////////////////////////////////////////

    init{
        //solutionList.addAll(arrayOf(1, 7, 13, 19, 25,26))
        solutionList.addAll(shortestPath((1..42).random(), (1..42).random()))
    }
}