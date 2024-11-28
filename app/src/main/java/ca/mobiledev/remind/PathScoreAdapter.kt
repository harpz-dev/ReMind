package ca.mobiledev.remind
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PathScoreAdapter(context: Context, items: List<PathScore>) : ArrayAdapter<PathScore>( context, 0, items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        val item = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view
        var currView: View
        if (convertView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.item_path_score, parent, false)
        } else {
            currView = convertView
        }

        // Lookup view for data population
        val attemptNoText: TextView = currView.findViewById(R.id.attemptNoText)
        val dateTimeText: TextView = currView.findViewById(R.id.dateTimeText)
        val scoreTextView: TextView= currView.findViewById(R.id.scoreText)
        val timeTakenTextView: TextView= currView.findViewById(R.id.timeTakenText)

        if (item != null) {
            // TODO
            //  Set the text used by textViewName and textViewNum using the data object
            //  This will need to updated once the entity model has been updated

            attemptNoText.text = item.attemptNo.toString()
            dateTimeText.text= item.dateTime
            scoreTextView.text= item.score.toString()
            timeTakenTextView.text= item.timeTaken.toString()



        }

        // Return the completed view to render on screen
        return currView
    }
}