package me.artspb.idea.eval.plugin

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAware
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair

class EvalAction : AnAction(), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val file = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)
        if (file != null) {
            val content = getContent(e, file)
            val post = HttpPost("https://3v4l.org/new")
            post.entity = UrlEncodedFormEntity(listOf(
                    BasicNameValuePair("title", file.name),
                    BasicNameValuePair("code", content)
            ))
            val response = HttpClients.createDefault().execute(post)
            val location = response.getFirstHeader("location")
            if (location != null) {
                val value = location.value
                val url: String
                if (value.startsWith("https://")) {
                    url = value
                } else {
                    url = "https://3v4l.org$value"
                }
                BrowserUtil.browse(url)
            }
        }
    }
}
