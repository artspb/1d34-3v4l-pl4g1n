package me.artspb.idea.eval.plugin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VirtualFile

val PHP_OPENING_TAG_BYTES = "<?php".toByteArray()
val LINE_SEPARATORS_BYTES = "\n\n".toByteArray()

fun getContent(e: AnActionEvent, file: VirtualFile): ByteArray {
    val rawContent = getContentFromSelection(e) ?: file.contentsToByteArray()
    return if (rawContent.startsWith(PHP_OPENING_TAG_BYTES)) {
        rawContent
    } else {
        PHP_OPENING_TAG_BYTES + LINE_SEPARATORS_BYTES + rawContent
    }
}

fun getContentFromSelection(e: AnActionEvent): ByteArray? {
    val editor = e.dataContext.getData(CommonDataKeys.EDITOR)
    return editor?.selectionModel?.selectedText?.toByteArray()
}

fun ByteArray.startsWith(arr: ByteArray): Boolean {
    if (arr.size > this.size) {
        return false
    }
    for (i in arr.indices) {
        if (this[i] != arr[i]) {
            return false
        }
    }
    return true
}