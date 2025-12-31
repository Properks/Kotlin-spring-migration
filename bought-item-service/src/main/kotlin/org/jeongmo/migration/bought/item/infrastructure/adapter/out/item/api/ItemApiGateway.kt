package org.jeongmo.migration.bought.item.infrastructure.adapter.out.item.api

import io.netty.handler.timeout.TimeoutException
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.out.item.ItemServiceClient
import org.jeongmo.migration.bought.item.application.port.out.item.dto.ItemInfoResponse
import org.jeongmo.migration.bought.item.infrastructure.adapter.out.item.api.dto.DecreaseItemStockRequest
import org.jeongmo.migration.bought.item.infrastructure.adapter.out.item.api.dto.IncreaseItemStockRequest
import org.jeongmo.migration.common.utils.idempotency.IDEMPOTENCY_KEY_NAME
import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyGenerator
import org.namul.api.payload.response.supports.DefaultResponse
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException
import reactor.util.retry.Retry
import java.time.Duration

@Component
class ItemApiGateway(
    private val webClient: WebClient,
    private val idempotencyKeyGenerator: IdempotencyKeyGenerator,
): ItemServiceClient {

    private val log = LoggerFactory.getLogger(ItemApiGateway::class.java)
    private val endpointPrefix = "/internal/api/items"

    override fun getItem(itemId: Long): ItemInfoResponse {
        val type = object: ParameterizedTypeReference<DefaultResponse<ItemInfoResponse?>?>() {}
        val response = sendGetRequest("$endpointPrefix/$itemId", type)
        return try {
            response?.result ?: run {
                log.warn("[FAIL_API] bought-item-service | Cannot get response from item domain (getItem)")
                throw BoughtItemException(BoughtItemErrorCode.ITEM_NOT_FOUND)
            }
        } catch (e: Exception) {
            log.warn("[FAIL_API] bought-item-service | Fail item-service api (getItem)")
            throw BoughtItemException(BoughtItemErrorCode.ITEM_NOT_FOUND, e)
        }
    }

    override fun decreaseItemCount(ownerId: Long, itemId: Long, quantity: Long) {
        val type = object: ParameterizedTypeReference<DefaultResponse<Any?>?>() {}
        val decreaseItemStockRequest = DecreaseItemStockRequest(quantity)
        try {
            sendDecreaseCountRequest("$endpointPrefix/${itemId}/decrease-stock", ownerId, decreaseItemStockRequest, type) ?: run {
                log.warn("[FAIL_API] bought-item-service | Cannot get response from item domain (DecreaseItemCount)")
                throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_INCREASE_ITEM_COUNT)
            }
        } catch (e: Exception) {
            log.warn("[FAIL_API] bought-item-service | Fail item-service api (DecreaseItemCount)")
            throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_DECREASE_ITEM_COUNT, e)
        }
    }

    override fun increaseItemCount(ownerId: Long, itemId: Long, quantity: Long) {
        val type = object: ParameterizedTypeReference<DefaultResponse<Any?>?>() {}
        val increaseRequest = IncreaseItemStockRequest(quantity)
        try {
            sendIncreaseCountRequest("$endpointPrefix/${itemId}/increase-stock", ownerId, increaseRequest, type) ?: run {
                log.warn("[FAIL_API] bought-item-service | Cannot get response from item domain (IncreaseItemCount)")
                throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_INCREASE_ITEM_COUNT)
            }
        } catch (e: Exception) {
            log.warn("[FAIL_API] bought-item-service | Fail item-service api (IncreaseItemCount)")
            throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_INCREASE_ITEM_COUNT, e)
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
            throw e
        }
    }

    private fun <T> sendDecreaseCountRequest(endpoint: String, ownerId: Long, request: DecreaseItemStockRequest, responseType: ParameterizedTypeReference<DefaultResponse<T?>?>): DefaultResponse<T?>? {
        val idempotencyKey = idempotencyKeyGenerator.generateKey("decrease-stock", HttpMethod.PATCH, endpoint, "ownerId$ownerId", "quantity${request.quantity}")
        try {
            return webClient.patch()
                .uri(endpoint)
                .header(IDEMPOTENCY_KEY_NAME, idempotencyKey)
                .bodyValue(request)
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
            throw e
        }
    }

    private fun <T> sendIncreaseCountRequest(endpoint: String, ownerId: Long, request: IncreaseItemStockRequest, responseType: ParameterizedTypeReference<DefaultResponse<T?>?>): DefaultResponse<T?>? {
        val idempotencyKey = idempotencyKeyGenerator.generateKey("increase-stock", HttpMethod.PATCH, endpoint, "ownerId$ownerId", "quantity${request.quantity}")
        try {
            return webClient.patch()
                .uri(endpoint)
                .header(IDEMPOTENCY_KEY_NAME, idempotencyKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(
                    Retry.backoff(3, Duration.ofSeconds(1))
                        .filter { it is WebClientException || it is TimeoutException }
                        .doBeforeRetry { log.warn("[API_RETRY] bought-item-service | Retrying to increase item count {}", it.totalRetries() + 1) }
                )
                .block(Duration.ofSeconds(5))
        } catch (e: Exception) {
            handleException(e)
            throw e
        }
    }

    private fun handleException(e: Exception) {
        log.warn("[EXTERNAL_DOMAIN_ERROR] bought-item-service | Error in item-service")
        log.debug("Exception details: ", e)
    }
}