package com.stiehl.peopleapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.stiehl.peopleapp.R
import com.stiehl.peopleapp.database.AppDatabase
import com.stiehl.peopleapp.database.daos.PersonDAO
import com.stiehl.peopleapp.models.Person
import kotlinx.android.synthetic.main.item_person.view.*
import kotlinx.android.synthetic.main.item_person_disabled.view.*
import kotlinx.android.synthetic.main.item_person_editing.view.*

class PersonAdapter(context: Context) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    private val dao: PersonDAO
    private val people: MutableList<Person>
    private var personEditing: Person? = null

    init {
        // Create DB instance
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "person-db"
        )
            .allowMainThreadQueries()
            .build()

        // Get DAO
        dao = db.personDAO()

        // Get all the people
        people = dao.getAll().toMutableList()
    }

//    fun add(person: Person) {
//        person.id = dao.insert(person)
//
//        people.add(person)
//        notifyItemInserted(people.size - 1)
//    }

    fun add() {
        val person = Person("", "", "")
        personEditing = person
        people.add(0, person)
        notifyItemInserted(0)
    }

    fun edit(person: Person) {
        personEditing = person
        val position = people.indexOf(person)
        notifyItemChanged(position)
    }

    fun save(person: Person) {
        if (person.id == null) {
            dao.insert(person)
        } else {
            dao.update(person)
        }
        val position = people.indexOf(person)
        personEditing = null
        notifyItemChanged(position)
    }

    override fun getItemCount() = people.size

    override fun getItemViewType(position: Int): Int {
        val person = people[position]
        return if (person.enabled) {
            if (person == personEditing) {
                R.layout.item_person_editing
            } else {
                R.layout.item_person
            }
        } else {
            R.layout.item_person_disabled
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.fillView(person)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(person: Person) {
            if (person.enabled) {
                if (person == personEditing) {
                    itemView.txtFirstName.setText(person.firstName)
                    itemView.txtLastName.setText(person.lastName)
                    itemView.txtTitle.setText(person.title)
                    itemView.btOK.setOnClickListener {
                        person.firstName = itemView.txtFirstName.text.toString()
                        person.lastName = itemView.txtLastName.text.toString()
                        person.title = itemView.txtTitle.text.toString()
                        person.enabled = true

                        save(person)
                    }
                } else {
                    itemView.lbTitle.text = person.title
                    itemView.lbFirstName.text = person.firstName
                    itemView.lbLastName.text = person.lastName
                    itemView.imPicture.setOnClickListener {
                        edit(person)
                    }
                }
            } else {
                itemView.lbFullName.text = "${person.firstName} ${person.lastName}"
            }

            itemView.setOnClickListener {
                person.enabled = !person.enabled
                dao.update(person)
                val position = people.indexOf(person)
                notifyItemChanged(position)
            }

            itemView.setOnLongClickListener {
                val position = people.indexOf(person)
                people.removeAt(position)
                dao.delete(person)
                notifyItemRemoved(position)
                true
            }
        }
    }
}