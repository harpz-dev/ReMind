package ca.mobiledev.remind
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class PathScoreAdapter(context: Context, items: List<PathScore>) : ArrayAdapter<PathScore>( context, 0, items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        val item = getItem(position)
        val rank = position + 1

        // Check if an existing view is being reused, otherwise inflate the view
        var currView: View
        if (convertView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.item_path_score, parent, false)
        } else {
            currView = convertView
        }

        // Lookup view for data population
        //val attemptNoText: TextView = currView.findViewById(R.id.attemptNoText)
        val rankText: TextView = currView.findViewById(R.id.rank)
        val cupEmoji: TextView = currView.findViewById(R.id.firstPlace)
        val secondPlace: TextView = currView.findViewById(R.id.secondPlace)
        val thirdPlace: TextView = currView.findViewById(R.id.thirdPlace)
        val dateTimeText: TextView = currView.findViewById(R.id.dateTimeText)
        val scoreTextView: TextView= currView.findViewById(R.id.scoreText)
        //val timeTakenTextView: TextView= currView.findViewById(R.id.timeTakenText)

        if (item != null) {
            // TODO
            //  Set the text used by textViewName and textViewNum using the data object
            //  This will need to updated once the entity model has been updated


            //attemptNoText.text = item.attemptNo.toString()

            if (rank == 1) {
                cupEmoji.visibility = View.VISIBLE
                rankText.visibility = View.GONE
            }
            if(rank == 2){
                secondPlace.visibility = View.VISIBLE
                rankText.visibility = View.GONE
            }
            if(rank == 3){
                thirdPlace.visibility = View.VISIBLE
                rankText.visibility = View.GONE
            }
            rankText.text = rank.toString()
            dateTimeText.text= item.dateTime
            "Level: ${item.score}".also { scoreTextView.text = it }
            //timeTakenTextView.text= item.timeTaken.toString()



        }

        // Return the completed view to render on screen
        return currView
    }
}