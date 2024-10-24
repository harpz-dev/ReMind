package ca.mobiledev.remind

import android.util.Log
import java.util.Collections

class MazeModel() {

    //6x7 grid
    val ROW= 7
    val COL= 6

    var solutionList: ArrayList<Int> = ArrayList()
    var selectedList: ArrayList<Int> = ArrayList()

    var nodeGraph: Graph = Graph(ROW, COL)


    fun getSolution():ArrayList<Int>{
        return ArrayList(Collections.unmodifiableList(solutionList));
    }
    fun getSelected():ArrayList<Int>{
        return ArrayList(Collections.unmodifiableList(selectedList))
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


    init{
        solutionList.addAll(arrayOf(1, 7, 13, 19, 25))

    }

}