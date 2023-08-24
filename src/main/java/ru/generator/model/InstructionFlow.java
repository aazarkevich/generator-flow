package ru.generator.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InstructionFlow implements Serializable {
    private String name;
    private String type;
    private Flow flows;

    @Data
    public static class Flow {
        private String url;
        private Configuration configuration;

        @Data
        public static class Configuration {
            private Request request;
            private Response response;

            @Data
            public static class Request {
                private String path;
                private String method;
                private List<Header> headers;
                private List<Field> fields;

                @Data
                public static class Header {
                    private String headerName;
                    private String headerValue;
                }

                @Data
                public static class Field {
                    private String fieldName;
                    private String fieldValue;
                }
            }

            @Data
            public static class Response {
                private List<Request.Field> fields;
            }
        }
    }
}
