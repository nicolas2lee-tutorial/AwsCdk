package com.github.nicolas2lee.stack;

import com.github.nicolas2lee.stack.lambda.UppercaseFunction;
import software.amazon.awscdk.Stack;
import software.constructs.Construct;

public class LambdaStack extends Stack {
    public LambdaStack(Construct parent, String id) {
        super(parent, id);
        new UppercaseFunction(this, "UppercaseFunction");
    }
}
