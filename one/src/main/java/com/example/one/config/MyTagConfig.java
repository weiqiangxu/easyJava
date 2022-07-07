package com.example.one.config;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * todo {这里必须加注释}
 *
 * @author xuweiqiang
 * @version 2.0.0
 * @date 2022/7/7 10:26
 */

@Configuration
public class MyTagConfig implements WebMvcTagsProvider {

    private final boolean ignoreTrailingSlash;

    private final List<WebMvcTagsContributor> contributors;

    public MyWebMvcTagsProvider() {
        this(false);
    }

    /**
     * Creates a new {@link DefaultWebMvcTagsProvider} that will provide tags from the
     * given {@code contributors} in addition to its own.
     * @param contributors the contributors that will provide additional tags
     * @since 2.3.0
     */
    public MyWebMvcTagsProvider(List<WebMvcTagsContributor> contributors) {
        this(false, contributors);
    }

    public MyWebMvcTagsProvider(boolean ignoreTrailingSlash) {
        this(ignoreTrailingSlash, Collections.emptyList());
    }

    /**
     * Creates a new {@link DefaultWebMvcTagsProvider} that will provide tags from the
     * given {@code contributors} in addition to its own.
     * @param ignoreTrailingSlash whether trailing slashes should be ignored when
     * determining the {@code uri} tag.
     * @param contributors the contributors that will provide additional tags
     * @since 2.3.0
     */
    public MyWebMvcTagsProvider(boolean ignoreTrailingSlash, List<WebMvcTagsContributor> contributors) {
        this.ignoreTrailingSlash = ignoreTrailingSlash;
        this.contributors = contributors;
    }


    /**
     * Provides tags to be associated with metrics for the given {@code request} and
     * {@code response} exchange.
     *
     * @param request   the request
     * @param response  the response
     * @param handler   the handler for the request or {@code null} if the handler is
     *                  unknown
     * @param exception the current exception, if any
     * @return tags to associate with metrics for the request and response exchange
     */
    @Override public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
        Tags tags = Tags.of( myMethod(request),WebMvcTags.uri(request, response, this.ignoreTrailingSlash),
                WebMvcTags.exception(exception), WebMvcTags.status(response), WebMvcTags.outcome(response));
        for (WebMvcTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.getTags(request, response, handler, exception));
        }
        return tags;
    }

    /**
     * 更改指标内的默认的标签名称
     *
     * @author xuweiqiang
     * @date 2022/7/7 11:11
     * @param request todo {这里必须添加参数注释}
     * @return Tag
     */
    public Tag myMethod(HttpServletRequest request) {
        return (request != null) ? Tag.of("you_method", request.getMethod()) : Tag.of("method", "UNKNOWN");
    }

    /**
     * Provides tags to be used by {@link LongTaskTimer long task timers}.
     *
     * @param request the HTTP request
     * @param handler the handler for the request or {@code null} if the handler is
     *                unknown
     * @return tags to associate with metrics recorded for the request
     */
    @Override public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Objec