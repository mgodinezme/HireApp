/*
 * Filename: Constants.kt
 * Description: Contains the constant values used throught this project.
 *
 * @author Mauricio Godinez
 *
 */

package com.mgodinez.hireapp.model

class Constants {
    companion object {
        // Path for the HTTP request performed in this project
        const val HTTP_REQUEST_PATH = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

        // Titles for fields used in the CardView forthe class Item
        const val PRE_NAME_TEXT = "Name: "
        const val PRE_ID_TEXT = "List ID: "

        // Messages for the Toast objects indicating success or failure of HTTP request
        const val RESPONSE_SUCCESS = "Data has been successfully synced."
        const val RESPONSE_ERROR = "An error has occurred."

        // Annotation Constants
        const val UNUSED_PARAMETER = "UNUSED_PARAMETER"
    } // End of companion object
} // End of class Constants