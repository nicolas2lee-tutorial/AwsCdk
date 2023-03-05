package com.github.nicolas2lee.stack;

import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class EcsStack extends Stack {
    public EcsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public EcsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Vpc vpc = Vpc.Builder.create(this, "myVpc")
                .maxAzs(3)
                .build();

        Cluster ecsCluster = Cluster.Builder.create(this, "EcsCluster")
                .vpc(vpc)
                .build();

        ApplicationLoadBalancedFargateService.Builder.create(this, "FargateService")
                .cluster(ecsCluster)
                .cpu(256)
                .desiredCount(3)
                .taskImageOptions(ApplicationLoadBalancedTaskImageOptions.builder()
                        .image(ContainerImage.fromRegistry("nicolas2lee/quick-start-java-springboot"))
                        .build())
                .memoryLimitMiB(512)
                .publicLoadBalancer(false)
                .build()
        ;


    }
}
