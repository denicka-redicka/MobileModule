package com.example.projectmaximummodule.data.theory

import com.example.projectmaximummodule.data.network.retorfit.response.TheoryResponse

@kotlinx.serialization.Serializable
data class TheoryTopicItem (
    val title: String,
    val id: Int,
    var isOpen: Boolean,
    val sublist: MutableList<TheoryTopicItem>?,
    val hasContent: Boolean?,
    val isRoot: Boolean?,
    ) {
    companion object {
        fun convertFromKbs(rawList: List<TheoryResponse.TheoryKbsResponse>?): MutableList<TheoryTopicItem> {
            val newList: MutableList<TheoryTopicItem> = mutableListOf()
            rawList?.forEach { item ->
                newList.add(
                    TheoryTopicItem(
                        title = item.title,
                        id = item.id,
                        isOpen = false,
                        sublist = convertFromKbs(item.children),
                        hasContent = item.hasContent,
                        isRoot = item.isRoot
                    )
                )
            }
            return newList
        }
    }
}