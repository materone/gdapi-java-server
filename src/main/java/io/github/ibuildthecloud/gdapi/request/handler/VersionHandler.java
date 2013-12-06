package io.github.ibuildthecloud.gdapi.request.handler;

import io.github.ibuildthecloud.gdapi.context.ApiContext;
import io.github.ibuildthecloud.gdapi.factory.SchemaFactory;
import io.github.ibuildthecloud.gdapi.model.Schema;
import io.github.ibuildthecloud.gdapi.request.ApiRequest;
import io.github.ibuildthecloud.model.impl.VersionImpl;
import io.github.ibuildthecloud.url.UrlBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class VersionHandler extends AbstractResponseGenerator {

    @Override
    protected void generate(ApiRequest request) throws IOException {
        if (request.getType() == null && request.getApiVersion() != null) {
            UrlBuilder urlBuilder = ApiContext.getUrlBuilder();
            SchemaFactory schemaFactory = ApiContext.getContext().getSchemaFactory();
            VersionImpl version = new VersionImpl(request.getApiVersion());

            Map<String,URL> links = version.getLinks();

            for ( Schema schema : schemaFactory.listSchemas() ) {
                URL link = urlBuilder.resourceCollection(schema.getId());
                if ( link != null ) {
                    links.put(schema.getPluralName(), link);
                }
            }

            request.setResponseObject(version);
        }
    }

}