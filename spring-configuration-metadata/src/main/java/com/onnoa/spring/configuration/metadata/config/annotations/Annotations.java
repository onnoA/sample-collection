package com.onnoa.spring.configuration.metadata.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

public class Annotations {

    @Retention( RetentionPolicy.RUNTIME )
    @Target( { ElementType.TYPE_USE, ElementType.TYPE_PARAMETER } )
    public @interface NonEmpty {
    }

    public static class Holder< @NonEmpty T > extends @NonEmpty Object {
        public void method() throws @NonEmpty Exception {
            System.out.println("test........... ");
        }
    }

    public static void main(String[] args) throws Exception {
        final Holder< String > holder = new @NonEmpty Holder< String >();
        holder.method();
        @NonEmpty Collection< @NonEmpty String > strings = new ArrayList<>();
        strings.add(null);
        System.out.println(strings);
    }
}
