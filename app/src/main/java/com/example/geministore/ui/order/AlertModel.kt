package com.example.geministore.ui.order

class AlertModel(
    buttonVisible: Int,
    alertVisible: Int,
    headAlert: String,
    totalAlert: String,
    descAlert: String,
    colorAlert: Int
) {
    var headAlert = ""
    var totalAlert = ""
    var descAlert = ""
    var colorAlert = 0
    var buttonVisible = 0
    var alertVisible = 0


    init {
        this.headAlert = headAlert
        this.totalAlert = totalAlert
        this.descAlert = descAlert
        this.colorAlert = colorAlert
        this.buttonVisible = buttonVisible
        this.alertVisible = alertVisible
    }
}