import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriecounterapp.Meals
import com.example.caloriecounterapp.R

class MealAdapter(private val context: Context, mealModelArrayList: ArrayList<Meals>) :
    RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    private val mealModelArrayList: ArrayList<Meals>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_file_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealAdapter.ViewHolder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: Meals = mealModelArrayList[position]
        holder.courseNameTV.text = "Food Description: \n" + model.name
        holder.courseRatingTV.text = "Calories: \n" + model.calories.toString()
        holder.courseIV.text = "Date: \n" + model.date
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return mealModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseIV: TextView
        val courseNameTV: TextView
        val courseRatingTV: TextView
        init {
            courseIV = itemView.findViewById(R.id.idIVCourseImage)
            courseNameTV = itemView.findViewById(R.id.idTVCourseName)
            courseRatingTV = itemView.findViewById(R.id.idTVCourseRating)
        }
    }

    // Constructor
    init {
        this.mealModelArrayList = mealModelArrayList
    }
}