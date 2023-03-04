package com.example.web3.eth.nft

import com.example.web3.eth.EthClient
import com.example.web3.eth.contract.ERC165
import com.example.web3.eth.contract.ERC173
import com.example.web3.eth.contract.ERC721
import com.example.web3.eth.contract.ERC721Enumerable
import com.example.web3.eth.contract.ERC721Metadata
import java.math.BigInteger

class NftClient(private val client: EthClient) {

    suspend fun isERC721(address: String): Boolean {
        return ERC165.supportsInterface(ERC721.INTERFACE_ID)
            .let { client.call(address, it) }
    }

    suspend fun collectionOwner(address: String): String {
        return ERC173.owner()
            .let { client.call(address, it) }
    }

    suspend fun collectionName(address: String): String {
        return ERC721Metadata.name()
            .let { client.call(address, it) }
    }

    suspend fun collectionSymbol(address: String): String {
        return ERC721Metadata.symbol()
            .let { client.call(address, it) }
    }

    suspend fun tokenUri(address: String, tokenId: BigInteger): String {
        return ERC721Metadata.tokenURI(tokenId)
            .let { client.call(address, it) }
    }

    suspend fun tokenOwner(address: String, tokenId: BigInteger): String {
        return ERC721.ownerOf(tokenId)
            .let { client.call(address, it) }
    }

    suspend fun totalSupply(address: String): Long {
        return ERC721Enumerable.totalSupply()
            .let { client.call(address, it) }
            .longValueExact()
    }
}
