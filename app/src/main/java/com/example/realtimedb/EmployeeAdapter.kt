import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedb.Employee
import com.example.realtimedb.R

class EmployeeAdapter(private val employeeList: List<Employee>) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employeeList[position]
        holder.bind(employee)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)

        fun bind(employee: Employee) {
            nameTextView.text = employee.name
            emailTextView.text = employee.email
            phoneTextView.text = employee.phone
        }
    }
}