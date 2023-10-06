package ru.alexeyoss.foodie.coreui.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.alexeyoss.foodie.core_ui.presentation.R

/**
 * Base class for flexible creating [AlertDialog]
 * */
object AlertDialogBuilder {

    /**
     * Create Cancelable [AlertDialog] with default workaround
     * */
    fun createPermissionDialog(
        context: Context,
        @StringRes message: Int?,
        @StringRes positiveBtnText: Int?,
        onPositive: () -> Unit
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setMessage(message ?: R.string.defaultRationalText)
            .setPositiveButton(positiveBtnText ?: R.string.defaultPositiveBtnText) { dialog, _ ->
                dialog.dismiss()
                onPositive()
            }
            .create()
    }
}
