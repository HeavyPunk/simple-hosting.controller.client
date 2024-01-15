package io.github.heavypunk.controller
package client.server

import io.github.heavypunk.controller.client.contracts.server.{
    StartServerRequest,
    StartServerResponse,
    StopServerRequest,
    StopServerResponse,
    SendMessageRequest,
    SendMessageResponse,
    GetServerInfoResponse,
    GetServerInfoRequest,
    GetServerLogsRequest,
    GetServerLogsResponse,
}
import io.github.heavypunk.controller.client.Settings
import java.net.http.HttpRequest
import org.apache.hc.core5.net.URIBuilder
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import java.net.http.HttpClient
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import io.github.heavypunk.controller.client.contracts.server.CheckServerRunningResponse

trait ControllerServerClient {
    def startServer(request: StartServerRequest, timeout: Duration): StartServerResponse
    def stopServer(request: StopServerRequest, timeout: Duration): StopServerResponse
    def sendMessage(request: SendMessageRequest, timeout: Duration): SendMessageResponse
    def getServerInfo(request: GetServerInfoRequest, timeout: Duration): GetServerInfoResponse
    def getServerLogs(request: GetServerLogsRequest, timeout: Duration): GetServerLogsResponse
    def getServerLogsLastPage(timeout: Duration): GetServerLogsResponse
    def checkServerRunning(timeout: Duration): CheckServerRunningResponse
}

class CommonControllerServerClient (
    settings: Settings
) extends ControllerServerClient {

    var jsoner = JsonMapper.builder()
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
        .appendPath("server")
        
    override def startServer(request: StartServerRequest, timeout: Duration): StartServerResponse = {
        val uri = constructBaseUri()
            .appendPath("start")
            .build()
        val content = jsoner.writeValueAsString(request)

        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to stop server: code: ${response.statusCode()}")

        val res = jsoner.readValue(response.body(), classOf[StartServerResponse])
        res
    }

    override def checkServerRunning(timeout: Duration): CheckServerRunningResponse = {
        val uri = constructBaseUri()
            .appendPath("is-running")
            .build()

        val req = getBaseRequestBuilder.GET()
            .uri(uri)
            .timeout(timeout)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to check server running: code: ${response.statusCode()}")

        val res = jsoner.readValue(response.body(), classOf[CheckServerRunningResponse])
        res
    }

    override def stopServer(request: StopServerRequest, timeout: Duration): StopServerResponse = {
        val uri = constructBaseUri()
            .appendPath("stop")
            .build()
        val content = jsoner.writeValueAsString(request)

        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to stop server: code: ${response.statusCode()}")
        
        val res = jsoner.readValue(response.body(), classOf[StopServerResponse])
        res
    }

    override def sendMessage(request: SendMessageRequest, timeout: Duration): SendMessageResponse = {
        val uri = constructBaseUri()
            .appendPath("messaging")
            .appendPath("post")
            .build
        val content = jsoner.writeValueAsString(request)

        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .build
        val client = HttpClient.newHttpClient
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode != 200)
            throw new RuntimeException(s"[ControllerClient] failed to send message: ${response.statusCode}")
        val res = jsoner.readValue(response.body, classOf[SendMessageResponse])
        res
    }

    override def getServerInfo(request: GetServerInfoRequest, timeout: Duration): GetServerInfoResponse = {
        val uri = constructBaseUri()
            .appendPath("info")
            .build
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .build
        val client = HttpClient.newHttpClient
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode != 200)
            throw new RuntimeException(s"[ControllerClient] failed get server info: ${response.statusCode}")
        val res = jsoner.readValue(response.body, classOf[GetServerInfoResponse])
        res
    }

    override def getServerLogs(request: GetServerLogsRequest, timeout: Duration): GetServerLogsResponse = {
        val uri = constructBaseUri()
            .appendPath("logs")
            .appendPath("get-server-log-on-page")
            .build
        val content = jsoner.writeValueAsString(request)
        val req = getBaseRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .build
        val client = HttpClient.newHttpClient
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode != 200)
            throw new RuntimeException(s"[ControllerClient] failed to get server logs: ${response.statusCode}")
        jsoner.readValue(response.body, classOf[GetServerLogsResponse])
    }

    override def getServerLogsLastPage(timeout: Duration): GetServerLogsResponse = {
        val uri = constructBaseUri()
            .appendPath("logs")
            .appendPath("get-server-last-log")
            .build
        val req = getBaseRequestBuilder().POST(HttpRequest.BodyPublishers.noBody)
            .uri(uri)
            .build
        val client = HttpClient.newHttpClient
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode != 200)
            throw new RuntimeException(s"[ControllerClient] failed to get server logs: ${response.statusCode}")
        jsoner.readValue(response.body, classOf[GetServerLogsResponse])
    }
}
