package client

import io.github.heavypunk.controller.client.server.CommonControllerServerClient
import io.github.heavypunk.controller.client.state.CommonControllerStateClient
import io.github.heavypunk.controller.client.contracts.server.{
    StopServerRequest,
    StartServerRequest,
    SendMessageRequest,
    GetServerInfoRequest,
    GetServerLogsRequest
}
import io.github.heavypunk.controller.client.Settings
import java.time.Duration

class ClientTestsPlayground extends munit.FunSuite {
    test("Start server playground") {
        val client = new CommonControllerServerClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.startServer(new StartServerRequest(false, false), Duration.ofMinutes(2))
        assert(res.success && res.error.equals(""))
    }

    test("Stop server playground") {
        val client = new CommonControllerServerClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.stopServer(new StopServerRequest(false), Duration.ofMinutes(2))
        assert(res.success && res.error.equals(""))
    }

    test("Ping controller check") {
        val client = new CommonControllerStateClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.ping()
        assert(res.success && res.serviceName.equals("controller"))
    }

}

class ClientMessengerPlaygroundTests extends munit.FunSuite {
    test("Send rcon message") {
        val client = new CommonControllerServerClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.sendMessage(new SendMessageRequest(
            "help", 
            "rcon"
        ), Duration.ofMinutes(2))
        assert(res.success)
    }

    test("Get server info") {
        val client = new CommonControllerServerClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.getServerInfo(new GetServerInfoRequest(
            "query"
        ), Duration.ofMinutes(2))
        assert(res.success)
    }
}


class ClientLogsPlaygroundTests extends munit.FunSuite {
    test("Get logs on page") {
        val client = new CommonControllerServerClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.getServerLogs(
            new GetServerLogsRequest(page = 0),
            Duration.ofMinutes(2)
        );
        assert(res.success)
    }

    test("Get logs on last page") {
        val client = new CommonControllerServerClient(new Settings("http", "127.0.0.1", 8989))
        val res = client.getServerLogsLastPage(Duration.ofMinutes(2))
        assert(res.success)
    }
}
