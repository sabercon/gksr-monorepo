package com.example.web3.eth.nft

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * See [here](https://docs.opensea.io/docs/metadata-standards)
 */
@JsonNaming(SnakeCaseStrategy::class)
data class TokenMetadata(
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val externalUrl: String? = null,
    val attributes: List<Attribute> = listOf(),
) {
    @JsonNaming(SnakeCaseStrategy::class)
    data class Attribute(
        val traitType: String,
        val displayType: String? = null,
        val value: String,
    )
}
