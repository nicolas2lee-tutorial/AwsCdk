package com.github.nicolas2lee.stack.lambda;


import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

import java.util.Map;

public class UppercaseFunction extends Construct {

    @SuppressWarnings("serial")
    public UppercaseFunction(Construct scope, String id) {
        super(scope, id);

        Bucket bucket = new Bucket(this, "UppercaseStore");

        Function handler = Function.Builder.create(this, "UppercaseHandler")
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("resources/springboot-minify-lambda-0.0.1-SNAPSHOT-all.jar"))
                .handler("nicolas2lee.github.com.example.lambda.spring.minify.Main")
                .timeout(Duration.seconds(10))
                .memorySize(512)
                .environment(Map.of(   // Java 9 or later
                        "BUCKET", bucket.getBucketName()))
                .build();

        bucket.grantReadWrite(handler);

        RestApi api = RestApi.Builder.create(this, "Uppercase-API")
                .restApiName("Uppercase Service").description("This service services widgets.")
                .build();

        LambdaIntegration getWidgetsIntegration = LambdaIntegration.Builder.create(handler)
                .requestTemplates(java.util.Map.of(   // Map.of is Java 9 or later
                        "application/json", "{ \"statusCode\": \"200\" }"))
                .build();

        api.getRoot().addMethod("GET", getWidgetsIntegration);
    }
}