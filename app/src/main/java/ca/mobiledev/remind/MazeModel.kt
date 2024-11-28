package ca.mobiledev.remind


import android.util.Log
import java.util.Collections

class MazeModel {

    //6x7 grid
    private val row = 7
    private val col = 6

    private var solutionList: ArrayList<Int> = ArrayList()
    private var selectedList: ArrayList<Int> = ArrayList()

    private var nodeGraph: Graph = Graph(row, col)

    private var attempts = 3

    private var level = 1

    fun getLevel(): Int{
        return level
    }
    fun incLevel(){
        level++
    }

    fun clearSelected() {
        selectedList = ArrayList<Int>()
    }

    fun getSolution():ArrayList<Int>{
        return ArrayList(Collections.unmodifiableList(solutionList))
    }
    fun getSelected():ArrayList<Int>{
        return ArrayList(Collections.unmodifiableList(selectedList))
    }

    fun compare() : Boolean {
        Log.d("Compare", "solution: $solutionList")
        Log.d("Compare", "selected: $selectedList")
        return getSelected().hashCode() == getSolution().hashCode()
    }

    fun restart(){
        attempts = 3
        level = 1
        solutionList = generatePath()
    }

    fun attemptsLeft() : Boolean {
        return attempts != 0
    }

    fun decAttempts(){
        attempts -= 1
    }

    fun addPoint(i: Int) {

        val indexOfi = selectedList.indexOf(i)

        if(indexOfi!=-1){ //if i is already in the list

            //remove all remaining elements in the list
            while(indexOfi< (selectedList.size)){
                selectedList.removeAt(indexOfi)

                Log.d("MazeModel", "Removing point and proceeding points")
            }
        }
        else{

            //if nothing is selected
            if(selectedList.isEmpty()){
                selectedList.add(i)

                val rCurrent: Int=(i-1)/col
                val cCurrent: Int= (i-1)%col

                Log.d("points", "Adding first point ($rCurrent , $cCurrent!)")
            }

            //if you select some other node (complex)
            else{
                //check whether it is a valid connection
                val last= selectedList[selectedList.size-1] //grab the last element

                val rLast: Int=(last-1)/col
                val cLast: Int= (last-1)%col

                val rCurrent: Int=(i-1)/col
                val cCurrent: Int= (i-1)%col


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

    ////////////////////////////////////////////////////////
    private fun generatePath(): ArrayList<Int> {
        val maxPathLength = 5 + level/2
        val gridWidth = col
        val gridHeight = row

        val gridSize = gridWidth * gridHeight
        val visited = mutableSetOf<Int>()
        val path = ArrayList<Int>()

        // Helper function to get neighbors in a specific direction
        fun getNeighbor(cell: Int, direction: Pair<Int, Int>): Int? {
            val row = (cell - 1) / gridWidth
            val col = (cell - 1) % gridWidth
            val newRow = row + direction.first
            val newCol = col + direction.second
            return if (newRow in 0 until gridHeight && newCol in 0 until gridWidth) {
                val neighbor = newRow * gridWidth + newCol + 1
                if (neighbor !in visited) neighbor else null
            } else null
        }

        // Possible directions
        val directions = listOf(
            Pair(-1, 0), // up
            Pair(1, 0),  // down
            Pair(0, -1), // left
            Pair(0, 1),  // right
            Pair(-1, -1), // up-left
            Pair(-1, 1),  // up-right
            Pair(1, -1), // down-left
            Pair(1, 1)   // down-right
        )

        // Start from the first button
        var currentCell = 1
        path.add(currentCell)
        visited.add(currentCell)

        // Generate the path
        while (path.size < maxPathLength && currentCell != gridSize) {
            val validDirections = directions.mapNotNull { getNeighbor(currentCell, it) }
            if (validDirections.isEmpty()) break // No valid moves left
            currentCell = validDirections.random()
            path.add(currentCell)
            visited.add(currentCell)
        }

        return path
    }

    fun getNewPath(){
        solutionList = generatePath()
    }

    init {
        //solutionList= arrayListOf(1, 2, 3, 4, 5)
        solutionList= generatePath()
    }
}