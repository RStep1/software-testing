package com.mycompany.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

@Disabled
@ExtendWith(AllureJunit5.class)
@Epic("API Testing")
@Feature("GraphQL Job Search API")
public class ApiTest {
    
    private static final String GRAPHQL_API_URL = "/graphql/api";
    private static final String CONSUMER = "loggedin.web.jobs.conv_search_results.center";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String ORIGIN = "https://www.xing.com";
    
    private static final String FULL_TIME_ID = "FULL_TIME.ef2fe9";
    private static final String PART_TIME_ID = "PART_TIME.58889d";
    private static final String FULL_REMOTE_ID = "FULL_REMOTE.050e26";
    private static final String HYBRID_ID = "PARTLY_REMOTE.ca71ca";
    private static final String ENTRY_LEVEL_ID = "2.24d1f6";
    private static final String EXPERIENCED_ID = "3.2ebf16";
    private static final String TECH_INDUSTRY_ID = "90000.597414";
    
    @BeforeAll
    @Step("Setup API test configuration")
    static void setup() {
        RestAssured.baseURI = "https://www.xing.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        allureAddEnvironmentInfo("API Base URL", "https://www.xing.com");
        allureAddEnvironmentInfo("GraphQL Endpoint", GRAPHQL_API_URL);
    }
    
    @Step("Build GraphQL query for job search with keywords: {keywords}, location: {location}")
    private String buildGraphQLJobSearchQuery(String keywords, String location, 
                                     Map<String, String> filters, 
                                     String fields, int limit, int offset) {
        StringBuilder filterBuilder = new StringBuilder();
        
        if (filters != null && !filters.isEmpty()) {
            filterBuilder.append(",\n        \"filter\": {");
            filters.forEach((type, id) -> {
                filterBuilder.append(String.format(
                    "\n          \"%s\": {\"id\": [\"%s\"]},", type, id));
            });
            filterBuilder.setLength(filterBuilder.length() - 1);
            filterBuilder.append("\n        }");
        }
        
        return String.format("""
        {
            "operationName": "JobSearchByQuery",
            "variables": {
                "query": {
                    "keywords": "%s",
                    "location": {"text": "%s"}%s
                },
                "consumer": "%s",
                "limit": %d,
                "offset": %d
            },
            "query": "query JobSearchByQuery($query: JobSearchQueryInput!, $consumer: String!, $offset: Int, $limit: Int) {\\n  jobSearchByQuery(query: $query, consumer: $consumer, offset: $offset, limit: $limit) {\\n    total\\n    collection {\\n      jobDetail {\\n        ... on VisibleJob {\\n          %s\\n        }\\n      }\\n    }\\n  }\\n}"
        }
        """, keywords, location, filterBuilder, CONSUMER, limit, offset, fields);
    }
    
    @Step("Execute GraphQL query and validate response")
    private void executeGraphQLQuery(String graphqlBody) {
        given()
            .contentType("application/json")
            .header("User-Agent", USER_AGENT)
            .header("Origin", ORIGIN)
            .body(graphqlBody)
        .when()
            .post(GRAPHQL_API_URL)
        .then()
            .statusCode(200)
            .body("data.jobSearchByQuery.total", greaterThanOrEqualTo(0));
    }
    
    static Stream<Arguments> jobSearchParameters() {
        return Stream.of(
            Arguments.of("software engineer", "berlin"),
            Arguments.of("java developer", "munich"),
            Arguments.of("data scientist", "hamburg"),
            Arguments.of("web developer", "cologne"),
            Arguments.of("project manager", "frankfurt")
        );
    }
    
    @ParameterizedTest
    @MethodSource("jobSearchParameters")
    @Description("Test basic job search with different job titles and locations")
    void testBasicSearch(String jobTitle, String location) {
        String query = buildGraphQLJobSearchQuery(
            jobTitle, 
            location, 
            null, 
            "title\\n location { city }", 
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Search completed for: " + jobTitle + " in " + location);
    }

    @Test
    @Description("Test remote job search functionality")
    void testRemoteJobs() {
        String query = buildGraphQLJobSearchQuery(
            "developer", 
            "germany", 
            Map.of("remoteOption", FULL_REMOTE_ID),
            "title",
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Remote jobs search executed with FULL_REMOTE filter");
    }

    @Test
    @Description("Test part-time remote job search")
    void testPartTimeRemoteJobs() {
        String query = buildGraphQLJobSearchQuery(
            "java", 
            "germany", 
            Map.of(
                "employmentType", PART_TIME_ID,
                "remoteOption", FULL_REMOTE_ID
            ),
            "title\\n employmentType { localizationValue }",
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Part-time remote jobs search executed");
    }

    @Test
    @Description("Test senior level job search")
    void testSeniorLevelJobs() {
        String query = buildGraphQLJobSearchQuery(
            "engineer", 
            "berlin", 
            Map.of("careerLevel", EXPERIENCED_ID),
            "title",
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Senior level jobs search executed");
    }

    @Test
    @Description("Test technology industry job search")
    void testTechnologyIndustry() {
        String query = buildGraphQLJobSearchQuery(
            "developer", 
            "berlin", 
            Map.of("industry", TECH_INDUSTRY_ID),
            "title",
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Technology industry jobs search executed");
    }

    @Test
    @Description("Test full-time job search")
    void testFullTimeJobs() {
        String query = buildGraphQLJobSearchQuery(
            "software engineer", 
            "munich", 
            Map.of("employmentType", FULL_TIME_ID),
            "title\\n employmentType { localizationValue }\\n companyInfo { company { companyName } }",
            10, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Full-time jobs search executed");
    }

    @Test
    @Description("Test hybrid work job search")
    void testHybridJobs() {
        String query = buildGraphQLJobSearchQuery(
            "project manager", 
            "hamburg", 
            Map.of("remoteOption", HYBRID_ID),
            "title\\n location { city }",
            8, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Hybrid jobs search executed");
    }

    @Test
    @Description("Test entry-level job search")
    void testEntryLevelJobs() {
        String query = buildGraphQLJobSearchQuery(
            "developer", 
            "berlin", 
            Map.of("careerLevel", ENTRY_LEVEL_ID),
            "title\\n careerLevel { localizationValue }",
            6, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Entry-level jobs search executed");
    }

    @Test
    @Description("Test job search with multiple filters combination")
    void testMultipleFiltersCombination() {
        String query = buildGraphQLJobSearchQuery(
            "data scientist", 
            "frankfurt", 
            Map.of(
                "careerLevel", EXPERIENCED_ID,
                "employmentType", FULL_TIME_ID,
                "remoteOption", HYBRID_ID
            ),
            "title\\n employmentType { localizationValue }",
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Multiple filters combination search executed");
    }

    @Test
    @Description("Test job search without keywords")
    void testSearchWithoutKeywords() {
        String query = buildGraphQLJobSearchQuery(
            "", 
            "berlin", 
            Map.of("employmentType", FULL_TIME_ID),
            "title\\n companyInfo { company { companyName } }",
            5, 
            0
        );
        executeGraphQLQuery(query);
        allureAddLog("Search without keywords executed");
    }

    @Disabled("for debugging")
    @Test
    @Description("Debug available filters in the system")
    void debugAvailableFilters() {
        String graphqlBody = """
        {
            "operationName": "JobSearchByQuery",
            "variables": {
                "query": {
                    "keywords": "software",
                    "location": {"text": "berlin"}
                },
                "consumer": "%s",
                "limit": 1,
                "offset": 0,
                "returnAggregations": true
            },
            "query": "query JobSearchByQuery($query: JobSearchQueryInput!, $consumer: String!, $offset: Int, $limit: Int, $returnAggregations: Boolean) {\\n  jobSearchByQuery(query: $query, consumer: $consumer, offset: $offset, limit: $limit, returnAggregations: $returnAggregations) {\\n    total\\n    aggregations {\\n      employmentTypes { id employmentType { localizationValue } }\\n      remoteOptions { id remoteOption { localizationValue } }\\n      careerLevels { id careerLevel { localizationValue } }\\n      industries { id industry { localizationValue } }\\n      __typename\\n    }\\n  }\\n}"
        }
        """.formatted(CONSUMER);

        Response response = given()
            .contentType("application/json")
            .header("User-Agent", USER_AGENT)
            .header("Origin", ORIGIN)
            .body(graphqlBody)
        .when()
            .post(GRAPHQL_API_URL)
        .then()
            .statusCode(200)
            .extract()
            .response();

        allureAddLog("Available employment types: " + 
            response.jsonPath().getString("data.jobSearchByQuery.aggregations.employmentTypes"));
        allureAddLog("Available remote options: " + 
            response.jsonPath().getString("data.jobSearchByQuery.aggregations.remoteOptions"));
        allureAddLog("Available career levels: " + 
            response.jsonPath().getString("data.jobSearchByQuery.aggregations.careerLevels"));
        allureAddLog("Available industries: " + 
            response.jsonPath().getString("data.jobSearchByQuery.aggregations.industries"));
    }

    @Attachment(value = "Environment Info", type = "text/plain")
    private static String allureAddEnvironmentInfo(String key, String value) {
        return key + ": " + value;
    }

    @Attachment(value = "Execution Log", type = "text/plain")
    private String allureAddLog(String message) {
        return message;
    }
}