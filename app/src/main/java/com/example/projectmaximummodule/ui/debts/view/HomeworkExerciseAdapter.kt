package com.example.projectmaximummodule.ui.debts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import kotlinx.android.synthetic.main.holder_exercise_item.view.*

class HomeworkExerciseAdapter :
    RecyclerView.Adapter<HomeworkExerciseAdapter.ExerciseViewHolder>() {

    var testsList: List<TestResponse> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_exercise_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(testsList[position], position)
    }

    override fun getItemCount(): Int {
        return testsList.size
    }

    inner class ExerciseViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.exerciseTitle
        private val exerciseNumber = view.exerciseNumber
        private val question = view.questionLayout
        private val triesCount = view.triesCount
        private val answerInputText = view.answerInputText
        private val answerButton = view.answerButton
        private val showResultsButton = view.showResultsButton

        fun bind(test: TestResponse, number: Int) {
            title.text = view.context.getString(R.string.exercise_title, testsList.size)
            triesCount.text = view.context.getString(R.string.tries_count_text, test.attemptsCountLeft ?: 0)
            exerciseNumber.text = "${number + 1}"
            question.loadData(test.question, "text/html", "UTF-8")
        }
    }
}