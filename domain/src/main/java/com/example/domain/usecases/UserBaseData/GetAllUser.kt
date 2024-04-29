package com.example.domain.usecases.UserBaseData

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUser @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getAllUser()
}