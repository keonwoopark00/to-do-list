package com.edgar.todolist.util

import androidx.fragment.app.FragmentManager
import com.edgar.todolist.ui.BottomSheet

/**
 * BottomSheetHelper.kt
 * provides functions to display a BottomSheet
 */

fun displayBottomSheet(helper: BottomSheetHelper, block: (s: String) -> Unit) {
    // declare a BottomSheet
    val bs = BottomSheet(helper.title)
    // put block for btn click listener
    bs.onButtonClick = {
        block(it)
        bs.dismiss()
    }
    // pop up the BottomSheet
    bs.show(helper.manager, helper.tag)
}

class BottomSheetHelper(
    var title: String,
    var tag: String,
    var manager: FragmentManager
)