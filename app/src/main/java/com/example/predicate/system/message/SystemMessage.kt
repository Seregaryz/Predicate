package com.example.predicate.system.message

data class SystemMessage(
    val text: String,
    val type: SystemMessageType = SystemMessageType.ALERT
)