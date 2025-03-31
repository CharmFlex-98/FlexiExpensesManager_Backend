package com.charmflex.fem

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.FileInputStream

@SpringBootApplication
class FlexiExpensesManagerServerApplication

fun main(args: Array<String>) {
	val serviceAccount = FileInputStream("src/main/resources/flexiexpensesmanagerproject-f79f3197bbbc.json")
	val options = FirebaseOptions.Builder()
		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
		.build()
	FirebaseApp.initializeApp(options)
	println("init firebaseapp")
	runApplication<FlexiExpensesManagerServerApplication>(*args)
}
