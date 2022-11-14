package com.example.projectmaximummodule.ui.debts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse.Companion.ANSWERS_FIRST_TYPE
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse.Companion.ANSWERS_SECOND_TYPE
import com.example.projectmaximummodule.util.*
import kotlinx.android.synthetic.main.holder_exercise_item.view.*

class HomeworkExerciseAdapter(
    private val lessonId: Long,
    private val onButtonsClickListener: OnButtonsClickListener,
    private val pageChangedCallback: ExercisePageChangedCallback
) : ListAdapter<TestResponse, HomeworkExerciseAdapter.ExerciseViewHolder>(DiffCallback()) {

    interface OnButtonsClickListener {
        fun onAnswerButtonClick(
            answer: TestAnswerRequest,
            testId: Int,
            currentExercisePosition: Int
        )

        fun onSolutionButtonClick(
            answer: ShowSolutionRequest,
            testId: Int,
            currentExercisePosition: Int
        )
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
        private val solutionButton = view.solutionButton
        private val solution = view.solutionLayout

        fun bind(test: TestResponse, number: Int, itemCount: Int) {
            if (!pageChangedCallback.chronometerList.containsKey(layoutPosition)) {
                pageChangedCallback.chronometerList += layoutPosition to testChronometer
            }

            answerButton.isEnabled = test.studentTestResult == null
            testChronometer.isAvailable = test.studentTestResult == null
            title.text = view.context.getString(R.string.exercise_title, itemCount)

            triesCount.toGoneWithIf(test.studentTestResult?.result.toBoolean())
            val tries = 3 - (test.studentTestResult?.attemptsCount ?: 0)
            if (triesCount.isVisible())
                triesCount.text = when {
                    test.studentTestResult?.showSolution
                        ?: false -> view.context.getString(R.string.showed_solution)
                    (tries > 0) -> view.context.getString(R.string.tries_count_text, tries)
                    else -> view.context.getString(R.string.no_tries_left)
                }

            exerciseNumber.text = "${number + 1}"
            question.loadData(test.question, "text/html", "UTF-8")

            solution.toVisibleIf(test.studentTestResult?.showSolution)
            solutionButton.toGoneWithIf(test.studentTestResult?.showSolution)

            if (solution.isVisible())
                solution.solutionView.loadData(test.solution ?: "", "text/html", "UTF-8")

            val adapter = AnswersAdapter(test.educationTestAnswers, test.type)
            val studentResult = test.studentTestResult
            if (studentResult != null) {
                testChronometer.setSpentTime(studentResult.spentTime)
                //If student showed solution displaying right answer
                if (studentResult.showSolution == true) {
                    adapter.studentAnswers = test.educationTestAnswers.filter { it.isRightAnswer }
                        .map { educationTestAnswer ->
                            when (test.type) {
                                ANSWERS_FIRST_TYPE, ANSWERS_SECOND_TYPE -> educationTestAnswer.id.toString()
                                else -> educationTestAnswer.variants[0]
                            }

                        }
                    adapter.result = true
                } else {
                    //Otherwise displaying student's answer
                    adapter.studentAnswers = studentResult.answer.map { it.value ?: "" }
                    adapter.result = studentResult.result.toBoolean()
                }
            }
            answerHoldersList.adapter = adapter
            answerHoldersList.layoutManager =
                LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

            answerButton.setOnClickListener {
                testChronometer.stop()
                answerButton.isEnabled = false
                view.hideKeyboard()
                val answerRequest = TestAnswerRequest(
                    answer = adapter.getAnswers(),
                    lessonId = lessonId,
                    spentTime = testChronometer.getTimeCount(),
                    attaches = listOf()
                )
                onButtonsClickListener.onAnswerButtonClick(answerRequest, test.id, layoutPosition)
            }

            val isRetryButtonShouldShows =
                !answerButton.isEnabled && tries > 0 && (test.studentTestResult?.result?.equals("false")
                    ?: true) && test.studentTestResult?.showSolution != true

            solutionButton.setOnClickListener {
                if (test.studentTestResult?.result.toBoolean()) {
                    //If user's answer is right -> don't send request
                    solution.toVisible()
                    solution.solutionView.loadData(test.solution?: "", "text/html", "UTF-8")
                    solutionButton.toGone()
                    retryButton.toGone()
                } else {
                    //otherwise -> send request
                    testChronometer.stop()
                    view.hideKeyboard()
                    answerButton.toDisable()
                    onButtonsClickListener.onSolutionButtonClick(
                        ShowSolutionRequest(
                            lessonId,
                            testChronometer.getTimeCount(),
                            showSolution = true
                        ),
                        test.id,
                        layoutPosition
                    )
                }
            }

            retryButton.toVisibleIf(isRetryButtonShouldShows)
            retryButton.setOnClickListener {
                answerButton.toEnable()
                adapter.result = null
                adapter.notifyItemRangeChanged(0, test.educationTestAnswers.size)
                testChronometer.resetAndStart()
                retryButton.toGone()
            }
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