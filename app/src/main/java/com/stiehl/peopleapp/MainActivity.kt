package com.stiehl.peopleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.stiehl.peopleapp.adapters.PersonAdapter
import com.stiehl.peopleapp.models.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PersonAdapter(applicationContext)
        listPeople.adapter = adapter
        listPeople.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        )

        btAdd.setOnClickListener {
            adapter.add()
        }
    }
}