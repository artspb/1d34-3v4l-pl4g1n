package me.artspb.idea.eval.plugin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VirtualFile

val PHP_OPENING_TAG = "<?php"

fun getContent(e: AnActionEvent, file: VirtualFile): String {
    val editor = e.dataContext.getData(CommonDataKeys.EDITOR)
    if (editor != null) {
        val content = editor.selectionModel.selectedText ?: editor.document.text
        return if (content.startsWith(PHP_OPENING_TAG)) {
            content
        } else {
            PHP_OPENING_TAG + "\n\n" + content
        }
    } else {
        return String(file.contentsToByteArray())
    }
}
