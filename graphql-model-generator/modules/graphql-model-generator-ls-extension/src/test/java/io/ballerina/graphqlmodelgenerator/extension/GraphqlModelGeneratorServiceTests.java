package io.ballerina.graphqlmodelgenerator.extension;

import io.ballerina.tools.text.LinePosition;
import org.ballerinalang.langserver.util.TestUtil;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GraphqlModelGeneratorServiceTests {
    private static final Path RES_DIR = Paths.get("src", "test", "resources").toAbsolutePath();
    private static final String BALLERINA = "ballerina";
    private static final String RESPONSES = "responses";
    private static final String PROJECT_DESIGN_SERVICE = "graphqlDesignService/getGraphqlModel";

    @Test(description = "test resource with record and service class as output")
    public void testDifferentOutputsAndOperations() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "01_graphql_service.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(50, 0), LinePosition.from(89, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("01_graphql_service.json"));
    }


    @Test(description = "test resource with record and service class as output")
    public void testInputObjects() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "02_graphql_service.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(14, 0), LinePosition.from(25, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("02_graphql_service.json"));
    }

    @Test(description = "test resource with record and service class as output")
    public void testServiceWithInterfaces() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "03_service_with_interfaces.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(52, 0), LinePosition.from(59, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("03_service_with_interfaces.json"));
    }

    @Test(description = "test resource with record and service class as output")
    public void testServiceWithUnionOutput() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "04_service_with_union_outputs.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(48, 0), LinePosition.from(57, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("04_service_with_union_output.json"));
    }

    @Test(description = "test resource with record and service class as output")
    public void testObjectsFromDifferentFiles() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "05_outputs_from_different_file.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(4, 0), LinePosition.from(13, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("05_outputs_from_different_file.json"));
    }

    @Test(description = "test resource with record and service class as output")
    public void testFileUploads() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "06_file_uploads.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(2, 0), LinePosition.from(13, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("06_file_uploads.json"));
    }


    @Test(description = "test resource with invalid output")
    public void testResourceWithInvalidOutput() throws IOException, ExecutionException, InterruptedException {
        Path projectPath = RES_DIR.resolve(BALLERINA).resolve(
                Path.of("graphql_services", "08_resource_with_invalid_return.bal"));

        Endpoint serviceEndpoint = TestUtil.initializeLanguageSever();
        TestUtil.openDocument(serviceEndpoint, projectPath);

        GraphqlDesignServiceRequest request = new GraphqlDesignServiceRequest(projectPath.toString(),
                LinePosition.from(2, 0), LinePosition.from(7, 1));

        Assert.assertEquals(getFormattedResponse(request, serviceEndpoint),
                getExpectedResponse("08_resource_with_invalid_return.json"));
    }

    private String getExpectedResponse(String fileName) throws IOException {
        return Files.readString(RES_DIR.resolve(RESPONSES).resolve(Path.of(fileName)))
                .replaceAll("\\s+", "")
                .replaceAll("\\{srcPath}", RES_DIR.toString());
    }

    private String getFormattedResponse(GraphqlDesignServiceRequest request, Endpoint serviceEndpoint)
            throws ExecutionException, InterruptedException {
        CompletableFuture<?> result = serviceEndpoint.request(PROJECT_DESIGN_SERVICE, request);
        GraphqlDesignServiceResponse response = (GraphqlDesignServiceResponse) result.get();
        return response.getGraphqlDesignModel().toString().replaceAll("\\s+", "");
    }
}
