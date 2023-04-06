package client

import io.github.heavypunk.controller.client.server.CommonControllerServerClient
import io.github.heavypunk.controller.client.contracts.server.StartServerRequest
import java.time.Duration
import io.github.heavypunk.controller.client.contracts.server.StopServerRequest
import io.github.heavypunk.controller.client.Settings

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
}
