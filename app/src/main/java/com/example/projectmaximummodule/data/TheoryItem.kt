package com.example.projectmaximummodule.data

import com.example.projectmaximummodule.data.network.retorfit.response.TheoryResponse

data class TheoryItem (
    val title: String,
    val id: Int,
    var isOpen: Boolean,
    val sublist: MutableList<TheoryItem>?,
    val hasContent: Boolean?,
    val isRoot: Boolean?,
    ) {
    companion object {
        fun convertFromKbs(rawList: List<TheoryResponse.TheoryKbsResponse>?): MutableList<TheoryItem> {
            val newList: MutableList<TheoryItem> = mutableListOf()
            rawList?.forEach { item ->
                newList.add(
                    TheoryItem(
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