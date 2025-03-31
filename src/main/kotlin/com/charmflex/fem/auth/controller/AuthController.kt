package com.charmflex.fem.auth.controller

import com.charmflex.fem.auth.controller.model.LoginUserRequest
import com.charmflex.fem.auth.controller.model.UpdateUserInfoResponse
import com.charmflex.fem.auth.application.service.AuthService
import com.google.firebase.auth.FirebaseAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("auth/")
internal class AuthController (
    private val firebaseAuth: FirebaseAuth,
    private val authService: AuthService
) {

    @PutMapping("/users/login")
    fun loginUser(@RequestBody loginUserRequest: LoginUserRequest): ResponseEntity<UpdateUserInfoResponse> {
        val loginUserCommand = LoginUserRequest.toCommand(loginUserRequest)
        val userInfo = authService.loginUser(loginUserCommand)
        val updateUserInfoResponse = UpdateUserInfoResponse(
            createdAt = userInfo.createdAt,
            newUser = userInfo.isNewUser
        )
        return ResponseEntity.ok(updateUserInfoResponse)
    }
}