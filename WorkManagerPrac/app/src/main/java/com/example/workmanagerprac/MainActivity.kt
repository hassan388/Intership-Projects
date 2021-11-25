package com.example.workmanagerprac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.workmanagerprac.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {



    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            val text = binding.editTextTextPersonName.text.toString()
            getUserSkill(text!!)
        }


    }

    private fun getUserSkill(userskill : String){
        val workManager = WorkManager.getInstance(applicationContext)
        val data = Data.Builder()
            .putString(Constants.KEY_USER_COMMENT_TEXT,userskill)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NewWorker>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()


        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer {workInfo ->
                Log.i(" check","${workInfo.state.name}")
                val status : String = workInfo.state.name
                binding.textView.text = status
                if ( workInfo.state == WorkInfo.State.SUCCEEDED){

                    val result = workInfo.outputData.getString(Constants.KEY_USER_SKILL_LEVEL)

                    Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
                }
            })

    }
}