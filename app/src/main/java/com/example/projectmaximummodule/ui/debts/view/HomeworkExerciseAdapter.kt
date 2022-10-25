package com.example.projectmaximummodule.ui.debts.view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import kotlinx.android.synthetic.main.holder_exercise_item.view.*

class HomeworkExerciseAdapter(
    private val lessonId: Long,
    private val onAnswerClickListener: OnAnswerButtonListener,
    private val pageChangedCallback: ExercisePageChangedCallback
) : ListAdapter<TestResponse, HomeworkExerciseAdapter.ExerciseViewHolder>(DiffCallback()) {

    interface OnAnswerButtonListener {
        fun onClick(answer: TestAnswerRequest, testId: Int, currentExercisePosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_exercise_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position), position, itemCount)
    }

    inner class ExerciseViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.exerciseTitle
        private val exerciseNumber = view.exerciseNumber
        private val question = view.questionLayout
        private val triesCount = view.triesCount
        private val answerHoldersList = view.answerInputsList
        private val answerButton = view.answerButton
        private val testChronometer = view.chronometer
        private val retryButton = view.retryButton

        fun bind(test: TestResponse, number: Int, itemCount: Int) {
            if (!pageChangedCallback.chronometerList.containsKey(layoutPosition)) {
                pageChangedCallback.chronometerList += layoutPosition to testChronometer
            }
            answerButton.isEnabled = test.studentTestResult == null
            testChronometer.isAvailable = test.studentTestResult == null
            title.text = view.context.getString(R.string.exercise_title, itemCount)
            val tries = 3 - (test.studentTestResult?.attemptsCount?: 0)
            triesCount.text = if (tries > 0) {
                    view.context.getString(R.string.tries_count_text, tries)
            } else {
                view.context.getString(R.string.no_tries_left)
            }

            exerciseNumber.text = "${number + 1}"
            question.loadData(test.question, "text/html", "UTF-8")


            val adapter = AnswersAdapter(test.educationTestAnswers, test.type)
            test.studentTestResult?.let { result ->
                testChronometer.setSpentTime(result.spentTime)
                adapter.studentAnswers = result.answer.map { it.value?: "" }
                adapter.result = result.result.toBoolean()
            }
            answerHoldersList.adapter = adapter
            answerHoldersList.layoutManager =
                LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

            answerButton.setOnClickListener {
                answerButton.isEnabled = false
                view.context.hideKeyboard(view)
                testChronometer.stop()
                val answerRequest = TestAnswerRequest(
                    answer = adapter.getAnswers(),
                    lessonId = lessonId,
                    spentTime = testChronometer.getTimeCount(),
                    attaches = listOf()
                )
                onAnswerClickListener.onClick(answerRequest, test.id, layoutPosition)
            }

            val isRetryButtonShouldShows = !answerButton.isEnabled && tries > 0 && (test.studentTestResult?.result?.equals("false")?: true)

            retryButton.visibility = if (isRetryButtonShouldShows) View.VISIBLE else View.GONE
            retryButton.setOnClickListener {
                answerButton.isEnabled = true
                adapter.result = null
                adapter.notifyItemRangeChanged(0, test.educationTestAnswers.size)
                testChronometer.resetAndStart()
                retryButton.visibility = View.GONE
            }
        }

        private fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TestResponse>() {
        override fun areItemsTheSame(oldItem: TestResponse, newItem: TestResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TestResponse, newItem: TestResponse): Boolean {
            return oldItem == newItem
        }
    }
}