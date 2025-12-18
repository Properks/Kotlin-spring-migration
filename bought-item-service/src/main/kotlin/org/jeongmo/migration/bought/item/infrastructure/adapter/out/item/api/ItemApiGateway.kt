package org.jeongmo.migration.bought.item.infrastructure.adapter.out.item.api

import io.netty.handler.timeout.TimeoutException
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.out.item.ItemServiceClient
import org.jeongmo.migration.bought.item.application.port.out.item.dto.ItemInfoResponse
import org.namul.api.payload.response.DefaultResponse
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException
import reactor.util.retry.Retry
import java.time.Duration

@Component
class ItemApiGateway(
    private val webClient: WebClient,
): ItemServiceClient {

    private val log = LoggerFactory.getLogger(ItemApiGateway::class.java)
    private val endpointPrefix = "/internal/api/items"

    override fun getItem(itemId: Long): ItemInfoResponse {
        val type = object: ParameterizedTypeReference<DefaultResponse<ItemInfoResponse?>?>() {}
        val response = sendGetRequest("$endpointPrefix/$itemId", type)
        return response?.result ?: run {
            log.warn("[FAIL_API] bought-item-service | Fail item-service api (getItem)")
            throw BoughtItemException(BoughtItemErrorCode.ITEM_NOT_FOUND)
        }
    }

    override fun decreaseItemCount(item: Long) {
        val type = object: ParameterizedTypeReference<DefaultResponse<Any?>?>() {}
        sendDecreaseCountRequest("$endpointPrefix/$item", type) ?: run {
            log.warn("[FAIL_API] bought-item-service | Fail item-service api (decreaseItemCount)")
            throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_DECREASE_ITEM_COUNT)
        }
    }

    private fun <T> sendGetRequest(endpoint: String, responseType: ParameterizedTypeReference<DefaultResponse<T?>?>): DefaultResponse<T?>? {
        try {
            return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(responseType)
                .block(Duration.ofSeconds(5))
        } catch (e: Exception) {
            handleException(e)
            return null
        }
    }

    private fun <T> sendDecreaseCountRequest(endpoint: String, responseType: ParameterizedTypeReference<DefaultResponse<T?>?>): DefaultResponse<T?>? {
        try {
            return webClient.patch()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(
                    Retry.backoff(3, Duration.ofSeconds(1))
                        .filter { it is WebClientException || it is TimeoutException }
                        .doBeforeRetry { log.warn("[API_RETRY] bought-item-service | Retrying to decrease item count {}", it.totalRetries() + 1) }
                )
                .block(Duration.ofSeconds(5))
        } catch (e: Exception) {
            handleException(e)
            return null
        }
    }

    private fun handleException(e: Exception) {
        log.warn("[EXTERNAL_DOMAIN_ERROR] bought-item-service | Error in item-service")
        log.debug("Exception details: ", e)
    }
}