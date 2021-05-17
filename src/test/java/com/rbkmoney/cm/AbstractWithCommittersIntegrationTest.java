package com.rbkmoney.cm;

import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimCommitterSrv;
import com.rbkmoney.damsel.claim_management.InvalidChangeset;
import com.rbkmoney.woody.thrift.impl.http.THServiceBuilder;
import org.apache.thrift.TException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.TestPropertySource;

import javax.servlet.Servlet;

import java.util.List;

@TestPropertySource(properties = {
        "claim-management.committers[0].id=committer",
        "claim-management.committers[0].uri=http://localhost:8032/committer",
        "claim-management.committers[0].timeout=5000"
})
public abstract class AbstractWithCommittersIntegrationTest extends AbstractIntegrationTest {

    protected Server server;
    protected int serverPort = 8032;
    private HandlerCollection handlerCollection;

    @Before
    public void startJetty() throws Exception {

        server = new Server(serverPort);
        HandlerCollection contextHandlerCollection = new HandlerCollection(true);
        this.handlerCollection = contextHandlerCollection;
        server.setHandler(contextHandlerCollection);

        server.start();
    }

    @After
    public void stopJetty() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected <T> Servlet createThriftRpcService(Class<T> iface, T handler) {
        THServiceBuilder serviceBuilder = new THServiceBuilder();
        return serviceBuilder.build(iface, handler);
    }

    protected void addServlet(Servlet servlet, String mapping) {
        try {
            ServletContextHandler context = new ServletContextHandler();
            ServletHolder defaultServ = new ServletHolder(mapping, servlet);
            context.addServlet(defaultServ, mapping);
            handlerCollection.addHandler(context);
            context.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void startSimpleCommitter() {
        addServlet(createThriftRpcService(ClaimCommitterSrv.Iface.class, new ClaimCommitterSrv.Iface() {
            @Override
            public void accept(String partyId, Claim claim) throws TException {
                //do nothing
            }

            @Override
            public void commit(String partyId, Claim claim) throws TException {
                //do nothing
            }
        }), "/committer");
    }

    protected void startCommitterWithInvalidChangeset() {
        addServlet(createThriftRpcService(ClaimCommitterSrv.Iface.class, new ClaimCommitterSrv.Iface() {
            @Override
            public void accept(String partyId, Claim claim) throws TException {
                throw new InvalidChangeset("invalid changeset", List.of());
            }

            @Override
            public void commit(String partyId, Claim claim) throws TException {
                //do nothing
            }
        }), "/committer");
    }

    protected void startCommitterWithUnexpectedErrorWhenAccept() {
        addServlet(createThriftRpcService(ClaimCommitterSrv.Iface.class, new ClaimCommitterSrv.Iface() {
            @Override
            public void accept(String partyId, Claim claim) throws TException {
                throw new RuntimeException();
            }

            @Override
            public void commit(String partyId, Claim claim) throws TException {
                //do nothing
            }
        }), "/committer");
    }


    protected void startCommitterWithUnexpectedErrorWhenCommit() {
        addServlet(createThriftRpcService(ClaimCommitterSrv.Iface.class, new ClaimCommitterSrv.Iface() {
            @Override
            public void accept(String partyId, Claim claim) throws TException {
                // do nothing
            }

            @Override
            public void commit(String partyId, Claim claim) throws TException {
                throw new RuntimeException();
            }
        }), "/committer");
    }

}
