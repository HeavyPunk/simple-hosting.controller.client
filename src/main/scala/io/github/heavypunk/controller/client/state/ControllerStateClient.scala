package io.github.heavypunk.controller.client.state

import io.github.heavypunk.controller.client.contracts.state.ControllerPingResponse
import io.github.heavypunk.controller.client.Settings
import com.fasterxml.jackson.databind.json.JsonMapper
import java.net.http.HttpRequest
import org.apache.hc.core5.net.URIBuilder
import java.net.http.HttpClient
import java.net.http.HttpResponse.BodyHandlers
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.DeserializationFeature
import io.github.heavypunk.controller.client.contracts.state.{
    ControllerServerRunningResponse,
    ControllerPingResponse
}

trait ControllerStateClient {
    def ping(): ControllerPingResponse
}

class CommonControllerStateClient (
    settings: Settings
) extends ControllerStateClient {

    var jsoner = JsonMapper.builder
        .addModule(DefaultScalaModule)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build

    val client = HttpClient.newHttpClient
    
    def getBaseRequestBuilder() = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")

    def constructBaseUri() = new URIBuilder()
        .setScheme(settings.scheme)
        .setHost(settings.host)
        .setPort(settings.port)
        .appendPath("state")

    def ping(): ControllerPingResponse = {
        val uri = constructBaseUri()
            .appendPath("_ping")
            .build()
        val req = getBaseRequestBuilder.GET()
            .uri(uri)
            .build()
        try {
            val resp = client.send(req, BodyHandlers.ofString())
            if (resp.statusCode != 200)
                return ControllerPingResponse(null, false)
            var raw = jsoner.readValue(resp.body, classOf[ControllerPingResponse])
            raw.success = raw.serviceName != null && !raw.serviceName.isEmpty
            return raw
        } catch {
            case e: RuntimeException => return ControllerPingResponse(null, false)
        }
    }

    def isGameServerRunning(): ControllerServerRunningResponse = {
        val uri = constructBaseUri()
            .appendPath("is-server-running")
            .build
        val req = getBaseRequestBuilder.GET()
            .uri(uri)
            .build
        val resp = client.send(req, BodyHandlers.ofString())
        var raw = jsoner.readValue(resp.body, classOf[ControllerServerRunningResponse])
        raw
    }
}
