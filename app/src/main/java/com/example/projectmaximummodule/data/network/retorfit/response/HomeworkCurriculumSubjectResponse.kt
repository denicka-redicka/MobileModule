package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeworkCurriculumSubjectResponse (
    val curriculumSubjects: SubjectInfo,
    val tree: TreeResponse
    )