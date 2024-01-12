package io.github.heavypunk.controller.client.files

import io.github.heavypunk.controller.client.contracts.files.PushFileToS3Request
import io.github.heavypunk.controller.client.contracts.files.PullFileFromS3Response
import io.github.heavypunk.controller.client.contracts.files.PushFileToS3Response
import io.github.heavypunk.controller.client.contracts.files.PullFileFromS3Request
import io.github.heavypunk.controller.client.contracts.files.PollTaskRequest
import io.github.heavypunk.controller.client.contracts.files.PollTaskResponse
import io.github.heavypunk.controller.client.Settings
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import java.net.http.HttpRequest
import org.apache.hc.core5.net.URIBuilder
import java.time.Duration
import java.net.http.HttpClient
import java.net.http.HttpResponse.BodyHandlers

trait ControllerFilesClient {
    def pushFileToS3(request: PushFileToS3Request, timeout: Duration): PushFileToS3Response
    def pullFileFromS3(request: PullFileFromS3Request, timeout: Duration): PullFileFromS3Response
    def pollTask(request: PollTaskRequest, timeout: Duration): PollTaskResponse
}

class CommonControllerFilesClient (
    settings: Settings,
) extends ControllerFilesClient {

    val jsoner = JsonMapper.builder()
        .addModule(DefaultScalaModule)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()
    
    def getBaseRequestBuilder() = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
    
    def constructBaseUri() = new URIBuilder()
        .setScheme(settings.scheme)
        .setHost(settings.host)
        .setPort(settings.port)
        .appendPath("files")
        .appendPath("s3")

    override def pushFileToS3(request: PushFileToS3Request, timeout: Duration): PushFileToS3Response = {
        val uri = constructBaseUri()
            .appendPath("push-file")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to push file: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[PushFileToS3Response])
        res
    }

    override def pullFileFromS3(request: PullFileFromS3Request, timeout: Duration): PullFileFromS3Response = {
        val uri = constructBaseUri()
            .appendPath("save-file")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to push file: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[PullFileFromS3Response])
        res
    }

    override def pollTask(request: PollTaskRequest, timeout: Duration): PollTaskResponse = {
        val uri = constructBaseUri()
            .appendPath("task-status")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to push file: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[PollTaskResponse])
        res
    }
}
