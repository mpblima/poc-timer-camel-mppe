package br.mppe.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class TimerRouter extends RouteBuilder {
	
	private final String protocoloOk = "{{protocolo.api.protocolo.ok}}";
	private final String protocoloNOk = "{{protocolo.api.protocolo.vencido}}";
	private final String urlRestProtocolo = "{{protocolo.api.url}}";
	
	
	private final String id = "{{timer.protocolo.route.id}}";

    @Override
    public void configure() {
        from("timer:consultaProtocolo?period={{timer.period}}")
        	.routeId(id)
        	.removeHeader("CamelHttp")
        	.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
            .log("Consulta do o protocolo "+protocoloOk+"/")
            .toD(urlRestProtocolo+protocoloOk)
            .log("Resposta da consulta: ${body}")
            .removeHeader("CamelHttp")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
            .log("Consulta do o protocolo "+protocoloNOk+"/")
            .toD(urlRestProtocolo+protocoloNOk)
            .log("Resposta da consulta: ${body}")
            .removeHeader("CamelHttp")
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
            .log("Consulta do o protocolo 12345")
            .toD(urlRestProtocolo+"12345/")
            .log("Resposta da consulta: ${body}")
        .end();
    }

}
