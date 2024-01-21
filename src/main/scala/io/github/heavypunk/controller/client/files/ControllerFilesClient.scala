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
import io.github.heavypunk.controller.client.contracts.files.RemoveFileRequest
import io.github.heavypunk.controller.client.contracts.files.RemoveFileResponse
import io.github.heavypunk.controller.client.contracts.files.CreateFileRequest
import io.github.heavypunk.controller.client.contracts.files.CreateDirectoryRequest
import io.github.heavypunk.controller.client.contracts.files.CreateDirectoryResponse
import io.github.heavypunk.controller.client.contracts.files.ListDirectoryRequest
import io.github.heavypunk.controller.client.contracts.files.ListDirectoryResponse
import io.github.heavypunk.controller.client.contracts.files.AcceptTaskRequest
import io.github.heavypunk.controller.client.contracts.files.CreateFileResponse
import io.github.heavypunk.controller.client.contracts.files.GetFileContentRequest
import io.github.heavypunk.controller.client.contracts.files.GetFileContentResponse

trait ControllerFilesClient {
    def pushFileToS3(request: PushFileToS3Request, timeout: Duration): PushFileToS3Response
    def pullFileFromS3(request: PullFileFromS3Request, timeout: Duration): PullFileFromS3Response
    def deleteFile(request: RemoveFileRequest, timeout: Duration): RemoveFileResponse
    def createFile(request: CreateFileRequest, timeout: Duration): CreateFileResponse
    def createDirectory(request: CreateDirectoryRequest, timeout: Duration): CreateDirectoryResponse
    def listDirectory(request: ListDirectoryRequest, timeout: Duration): ListDirectoryResponse
    def getFileContent(request: GetFileContentRequest, timeout: Duration): GetFileContentResponse
    def acceptTask(request: AcceptTaskRequest, timeout: Duration): Unit
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

    override def pushFileToS3(request: PushFileToS3Request, timeout: Duration): PushFileToS3Response = {
        val uri = constructBaseUri()
            .appendPath("s3")
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
            .appendPath("s3")
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

    override def deleteFile(request: RemoveFileRequest, timeout: Duration): RemoveFileResponse = {
        val uri = constructBaseUri()
            .appendPath("delete-file")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to delete file: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[RemoveFileResponse])
        res
    }

    override def createFile(request: CreateFileRequest, timeout: Duration): CreateFileResponse = {
        val uri = constructBaseUri()
            .appendPath("create-file")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to create file: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[CreateFileResponse])
        res
    }

    override def createDirectory(request: CreateDirectoryRequest, timeout: Duration): CreateDirectoryResponse = {
        val uri = constructBaseUri()
            .appendPath("create-directory")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to create directory: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[CreateDirectoryResponse])
        res
    }

    override def listDirectory(request: ListDirectoryRequest, timeout: Duration): ListDirectoryResponse = {
        val uri = constructBaseUri()
            .appendPath("list-directory")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to list directory: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[ListDirectoryResponse])
        res
    }

    override def acceptTask(request: AcceptTaskRequest, timeout: Duration): Unit = {
        val uri = constructBaseUri()
            .appendPath("accept-task")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to accept task: code: ${response.statusCode()}")
    }

    override def getFileContent(request: GetFileContentRequest, timeout: Duration): GetFileContentResponse = {
        val uri = constructBaseUri()
            .appendPath("get-file-content-base64")
            .build()
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to get file content: code: ${response.statusCode()}")
        val res = jsoner.readValue(response.body(), classOf[GetFileContentResponse])
        res
    }
}
