package com.example.projectmaximummodule.ui.debts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.EducationTestAnswerResponse
import kotlinx.android.synthetic.main.holder_checkbox_text.view.*
import kotlinx.android.synthetic.main.holder_input_text_answer.view.*
import kotlinx.android.synthetic.main.holder_input_text_with_additional_answer.view.*
import kotlinx.android.synthetic.main.holder_radiobutton_answer.view.*

class AnswersAdapter(
    private val answersVariants: List<EducationTestAnswerResponse>,
    private val type: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPosition = -1

    val studentAnswers: List<String>
        get() = when (type) {
            "type1" -> listOf(answersVariants[selectedPosition].variants[0])
            "type2" -> answersVariants.filter { it.isSelected }.map { it.variants[0] }
            "type6" -> answersVariants.map { it.inputAnswer }
            else -> answersVariants.map { it.inputAnswer }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.holder_radiobutton_answer -> RadioButtonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_radiobutton_answer, parent, false)
            )
            R.layout.holder_checkbox_text -> CheckboxViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_checkbox_text, parent, false)
            )
            R.layout.holder_input_text_with_additional_answer -> InputTextWithAdditionalViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_input_text_with_additional_answer, parent, false)
            )
            R.layout.holder_input_text_answer -> InputTextViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_input_text_answer, parent, false)
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RadioButtonViewHolder -> holder.bind(answersVariants[position], position)
            is CheckboxViewHolder -> holder.bind(answersVariants[position])
            is InputTextViewHolder -> holder.bind(answersVariants[position])
            is InputTextWithAdditionalViewHolder -> holder.bind(answersVariants[position])
        }
    }

    override fun getItemCount(): Int {
        return answersVariants.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            "type1" -> R.layout.holder_radiobutton_answer
            "type2" -> R.layout.holder_checkbox_text
            "type6" -> R.layout.holder_input_text_with_additional_answer
            else -> R.layout.holder_input_text_answer
        }
    }

    inner class RadioButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val radioButton = view.selectButton
        private val answerDescription = view.answerDescriptionImage

        fun bind(variant: EducationTestAnswerResponse, position: Int) {
            radioButton.isChecked = position == selectedPosition
            var description = variant.variants[0]

            if (description.contains("image_description")) {
            }
            radioButton.text = description

            radioButton.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    val backup = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(backup)
                }
            }
        }
    }

    inner class CheckboxViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val checkbox = view.checkboxAnswer
        private val answerText = view.answerCheckboxDescriptionImage

        fun bind(variant: EducationTestAnswerResponse) {
            checkbox.text = variant.variants[0]

            checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
                variant.isSelected = isChecked
            }
        }
    }

    inner class InputTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val inputAnswerEditText = view.answerInputText

        fun bind(variant: EducationTestAnswerResponse) {
            inputAnswerEditText.doOnTextChanged { text, start, before, count ->
                variant.inputAnswer = text.toString()
            }
        }
    }

    inner class InputTextWithAdditionalViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val inputAnswerEditText = view.answerInputText
        private val addFileButton = view.addFileButton

        fun bind(variant: EducationTestAnswerResponse) {
            inputAnswerEditText.doOnTextChanged { text, start, before, count ->
                variant.inputAnswer = text.toString()
            }
        }
    }
}