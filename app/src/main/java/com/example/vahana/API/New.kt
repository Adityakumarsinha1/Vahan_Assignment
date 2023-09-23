package com.example.vahana.API

data class New(val name: String,
               val domains: List<String>,
               val country: String,
               val alpha_two_code: String,
               val web_pages: List<String>,
               val state_province: String)
