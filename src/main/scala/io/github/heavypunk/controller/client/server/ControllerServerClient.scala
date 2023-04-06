package io.github.heavypunk.controller
package client.server

import io.github.heavypunk.controller.client.contracts.server.{StartServerRequest, StartServerResponse, StopServerRequest, StopServerResponse}
import io.github.heavypunk.controller.client.Settings
import java.net.http.HttpRequest
import org.apache.hc.core5.net.URIBuilder
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import java.net.http.HttpClient
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

trait ControllerServerClient {
    def startServer(request: StartServerRequest, timeout: Duration): StartServerResponse
    def stopServer(request: StopServerRequest, timeout: Duration): StopServerResponse
}

class CommonControllerServerClient(val settings: Settings) extends ControllerServerClient {

    var jsoner = JsonMapper.builder()
        .addModule(DefaultScalaModule)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()

    var baseRequest = HttpRequest.newBuilder()
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

        val req = baseRequest.POST(HttpRequest.BodyPublishers.ofString(content))
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

    override def stopServer(request: StopServerRequest, timeout: Duration): StopServerResponse = {
        val uri = constructBaseUri()
            .appendPath("stop")
            .build()
        val content = jsoner.writeValueAsString(request)

        val req = baseRequest.POST(HttpRequest.BodyPublishers.ofString(content))
            .uri(uri)
            .build()
        val client = HttpClient.newHttpClient()
        val response = client.send(req, BodyHandlers.ofString())
        if (response.statusCode() >= 400)
            throw new RuntimeException(s"[ControllerClient] failed to stop server: code: ${response.statusCode()}")
        
        val res = jsoner.readValue(response.body(), classOf[StopServerResponse])
        res
    }
}
