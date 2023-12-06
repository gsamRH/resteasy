package org.jboss.resteasy.test.microprofile.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.category.MicroProfileDependent;
import org.jboss.resteasy.microprofile.client.RestClientBuilderImpl;
import org.jboss.resteasy.utils.PortProviderUtil;
import org.jboss.resteasy.utils.TestUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * MicroProfile rest client
 * Integration tests
 * Testing that using nonProxyHosts property with * near index 0 doesn't throw
 * regex.PatternSyntaxException while trying to build a rest client
 * https://issues.redhat.com/browse/JBEAP-25126
 */
@RunWith(Arquillian.class)
@RunAsClient
@Category(MicroProfileDependent.class)
public class RestClientNonProxyHostsPatternTest {

    private static final String PROXY_HOST = "localhost";
    private static final String PROXY_PORT = "8080";

    //asterisk must be at index 0
    static final String NON_PROXY_HOSTS = "*.test.com";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = TestUtil.prepareArchive(RestClientNonProxyHostsPatternTest.class.getSimpleName());
        war.addClass(HelloClient.class);
        return TestUtil.finishContainerPrepare(war, null);
    }

    @Test
    public void testProxyOperationAfterNonProxyHostSetWithFirstIndexAsterisk() throws Exception {

        try {
            System.setProperty("http.proxyHost", PROXY_HOST);
            System.setProperty("http.proxyPort", PROXY_PORT);
            System.setProperty("http.nonProxyHosts", NON_PROXY_HOSTS);

            String path = PortProviderUtil.generateURL("", RestClientNonProxyHostsPatternTest.class.getSimpleName());
            RestClientBuilderImpl builder = new RestClientBuilderImpl();
            HelloClient helloClient = builder.baseUrl(new URL(path)).build(HelloClient.class);
            assertNotNull(helloClient);
        } finally {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("http.nonProxyHosts");
        }
    }

    @RegisterRestClient
    @Path("/")
    public interface HelloClient {
        @GET
        @Path("/hello")
        @Produces("text/plain")
        String hello();
    }

}
