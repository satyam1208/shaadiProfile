package com.example.demoapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.demoapplication.ui.theme.DemoApplicationTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var resultsList: List<Result>
    private lateinit var db: AppDatabase
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()
        userViewModel.fetchUserList()
        observeViewModel()
    }

    private fun observeViewModel(){
        userViewModel.userList.observe(this) { userList ->
            userList.let {
                resultsList = it
                val users = convertApiResultToUsers(userList)
                insertUsersToDatabase(users)
            }
            initializeAdapter()
        }
    }

    private fun convertApiResultToUsers(apiResult: List<Result>): List<User> {
        return apiResult.map { result ->
            User(
                gender = result.gender,
                name = result.name.first,
                cell = result.cell,
                email = result.email,
                picture = result.picture.medium,
                country = result.location.country,
                city = result.location.city,
                age = result.registered.age,
                status = "No Action"
            )
        }
    }

    private fun insertUsersToDatabase(users: List<User>) {
        GlobalScope.launch {
            users.let {
                db.userDao().insertUser(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }
    private fun observeUsers(cardAdapter: CardAdapter) {
        db.userDao().getAllUsers().observe(this) { users ->
            users.let {
                cardAdapter.dataModelArrayList = it
            }  // Update the list in the adapter
            cardAdapter.notifyDataSetChanged() // Notify the adapter that the data has changed
        }
    }


    private fun initializeAdapter() {
        val profileRV = findViewById<RecyclerView>(R.id.idRVCourse)
        if (this::resultsList.isInitialized) {
            val courseAdapter = CardAdapter(this,db)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            profileRV.layoutManager = linearLayoutManager
            profileRV.adapter = courseAdapter
            observeUsers(courseAdapter)
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoApplicationTheme {
        Greeting("Android")
    }
}