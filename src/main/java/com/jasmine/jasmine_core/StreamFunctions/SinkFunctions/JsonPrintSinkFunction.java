package com.jasmine.jasmine_core.StreamFunctions.SinkFunctions;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.io.PrintStream;

public class JsonPrintSinkFunction<IN> extends RichSinkFunction<IN> {
    private static final long serialVersionUID = 1L;

    private transient PrintStream stream;
    private transient String prefix;
    private boolean target;
    private String staticPrefix;

    public JsonPrintSinkFunction() {
        this(null);
    }

    public JsonPrintSinkFunction(String staticPrefix) {
        this(false);
        this.staticPrefix = "[" + staticPrefix + "] ";
    }

    public JsonPrintSinkFunction(boolean stdErr) {
        this.target = stdErr;
    }

    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.stream = !target ? System.out : System.err;
        this.prefix = (this.staticPrefix != null ? this.staticPrefix : "") + "[" + this.getRuntimeContext().getIndexOfThisSubtask() + "] ";
    }

    @Override
    public void invoke(IN value, Context context) throws Exception {
        if (this.prefix != null)
            this.stream.println(this.prefix + new ObjectMapper().writeValueAsString(value));
        else
            this.stream.println(new ObjectMapper().writeValueAsString(value));
    }

    public void close() {
        this.stream = null;
        this.prefix = null;
    }

    public String toString() {
        return "Print json to " + this.stream;
    }
}
