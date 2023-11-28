package com.pr.logpractice

import groovy.json.JsonBuilder

import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import net.grinder.script.GTest
import net.grinder.script.Grinder
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
// import static net.grinder.util.GrinderUtils.* // You can use this if you're using nGrinder after 3.2.3
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPRequestControl
import org.ngrinder.http.HTTPResponse
import org.ngrinder.http.cookie.Cookie
import org.ngrinder.http.cookie.CookieManager

/**
 * A simple example using the HTTP plugin that shows the retrieval of a single page via HTTP.
 *
 * This script is automatically generated by ngrinder.
 *
 * @author admin
 */
@RunWith(GrinderRunner)
class Calendar {

    public static GTest test1
    public static GTest test2

    public static HTTPRequest request
    public static Map<String, String> headers = [:]
    public static Map<String, Object> params = [:]
    public static List<Cookie> cookies = []

    @BeforeProcess
    public static void beforeProcess() {
        HTTPRequestControl.setConnectionTimeout(300000)
        test1 = new GTest(1, "/v1/calendars?from=2023-11-01&to=2023-11-30")
        test2 = new GTest(2, "/v1/calendars/recent")
        request = new HTTPRequest()
        headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1zazMxMTNAbmF2ZXIuY29tIiwiYXV0aCI6IkdVRVNUIiwiZXhwIjoxNzAwOTQyMTI0fQ.ogrEboGlF5w3f4Y7fL25eSwAf2U8njtFkm0LcuO-u5o")
        grinder.logger.info("프로세스 시작 전")
    }

    @BeforeThread
    public void beforeThread() {
        test1.record(this, "test1")
        test2.record(this, "test2")
        grinder.statistics.delayReports = true
        //grinder.logger.info("스레드 시작 전")
    }

    @Before
    public void before() {
        request.setHeaders(headers)
        CookieManager.addCookies(cookies)
        //grinder.logger.info("헤더 쿠키 초기화")
    }

    @Test
    public void test1() {
        // from부터 to까지 일정 조회
        HTTPResponse response = request.GET("https://love-back.kro.kr/v1/calendars?from=2023-11-01&to=2023-11-30", params)

        if (response.statusCode == 200) {
            grinder.logger.info("일정 조회 성공: {}", response.statusCode)

        } else {
            grinder.logger.info("일정 조회 실패 응답: {}", response.getBodyText())
            grinder.logger.error("일정 조회 실패: {}", response.statusCode)
        }

        assertThat(response.statusCode, is(200))
    }

    @Test
    public void test2() {
        // 최근 일정 조회
        HTTPResponse response = request.GET("https://love-back.kro.kr/v1/calendars/recent", params)

        if (response.statusCode == 200) {
            grinder.logger.info("최근 일정 조회 성공: {}", response.statusCode)
        } else {
            grinder.logger.info("최근 일정 조회 실패 응답: {}", response.getBodyText())
            grinder.logger.error("최근 일정 조회 실패: {}", response.statusCode)
        }

        assertThat(response.statusCode, is(200))

    }
}