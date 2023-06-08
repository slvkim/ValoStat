package com.mikyegresl.valostat.storage.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.storage.ValorantStorage
import com.mikyegresl.valostat.base.storage.service.AgentsLocalDataSource

class AgentsLocalDataSourceImpl(
    private val storage: ValorantStorage,
) : AgentsLocalDataSource {

    companion object {
        private const val USA = "United States"
        private const val RUSSIA = "Russia"
        private const val GERMANY = "Germany"
        private const val FRANCE = "France"
        private const val SWEDEN = "Sweden"
        private const val MOROCCO = "Morocco"
        private const val AUSTRALIA = "Australia"
        private const val BRAZIL = "Brazil"
        private const val INDIA = "India"
        private const val UK = "United Kingdom"
        private const val CHINA = "China"
        private const val SOUTH_KOREA = "South Korea"
        private const val JAPAN = "Japan"
        private const val PHILIPPINES = "Philippines"
        private const val GHANA = "Ghana"
        private const val TURKEY = "Turkey"
        private const val MEXICO = "Mexico"
        private const val ALTERNATIVE = "Alternative Timeline Earth"
        private const val UNKNOWN = "Unknown"

        private const val USA_URL = "https://img.icons8.com/color/48/usa.png"
        private const val RUSSIA_URL = "https://img.icons8.com/color/48/russian-federation.png"
        private const val GERMANY_URL = "https://img.icons8.com/color/48/germany.png"
        private const val FRANCE_URL = "https://img.icons8.com/color/48/france.png"
        private const val SWEDEN_URL = "https://img.icons8.com/color/48/sweden.png"
        private const val MOROCCO_URL = "https://img.icons8.com/color/48/morocco.png"
        private const val AUSTRALIA_URL = "https://img.icons8.com/color/48/australia.png"
        private const val BRAZIL_URL = "https://img.icons8.com/color/48/brazil.png"
        private const val INDIA_URL = "https://img.icons8.com/color/48/india.png"
        private const val UK_URL = "https://img.icons8.com/color/48/great-britain.png"
        private const val CHINA_URL = "https://img.icons8.com/color/48/china.png"
        private const val SOUTH_KOREA_URL = "https://img.icons8.com/color/48/south-korea.png"
        private const val JAPAN_URL = "https://img.icons8.com/color/48/japan.png"
        private const val PHILIPPINES_URL = "https://img.icons8.com/color/48/philippines.png"
        private const val GHANA_URL = "https://img.icons8.com/color/48/ghana.png"
        private const val TURKEY_URL = "https://img.icons8.com/color/48/turkey.png"
        private const val MEXICO_URL = "https://img.icons8.com/color/48/mexico.png"
    }
    override fun getAgentOrigin(id: String) : AgentOriginDto {
        val countryName: String
        val iconUrl: String

        when (id) {
            "9f0d8ba9-4140-b941-57d3-a7ad57c6b417",
            "707eab51-4836-f488-046a-cda6bf494859",
            "e370fa57-4757-3604-3648-499e1f642d3f" -> {
                countryName = USA
                iconUrl = USA_URL
            }
            "eb93336a-449b-9c1b-0a54-a891f7921d69" -> {
                countryName = UK
                iconUrl = UK_URL
            }
            "1e58de9c-4950-5125-93e9-a0aee9f98746" -> {
                countryName = GERMANY
                iconUrl = GERMANY_URL
            }
            "117ed9e3-49f3-6512-3ccf-0cada7e3823b" -> {
                countryName = MOROCCO
                iconUrl = MOROCCO_URL
            }
            "ded3520f-4264-bfed-162d-b080e2abccf9",
            "320b2a48-4d9b-a075-30f1-1f93a9b638fa" -> {
                countryName = RUSSIA
                iconUrl = RUSSIA_URL
            }
            "569fdd95-4d10-43ab-ca70-79becc718b46" -> {
                countryName = CHINA
                iconUrl = CHINA_URL
            }
            "7f94d92c-4234-0a36-9646-3a87eb8b5c89" -> {
                countryName = JAPAN
                iconUrl = JAPAN_URL
            }
            "add6443a-41bd-e414-f6ad-e58d267f4e95" -> {
                countryName = SOUTH_KOREA
                iconUrl = SOUTH_KOREA_URL
            }
            "f94c3b30-42be-e959-889c-5aa313dba261" -> {
                countryName = BRAZIL
                iconUrl = BRAZIL_URL
            }
            "95b78ed7-4637-86d9-7e41-71ba8c293152" -> {
                countryName = INDIA
                iconUrl = INDIA_URL
            }
            "41fb69c1-4189-7b37-f117-bcaf1e96f1bf" -> {
                countryName = GHANA
                iconUrl = GHANA_URL
            }
            "a3bfb853-43b2-7238-a4f1-ad90e9e46bcc" -> {
                countryName = MEXICO
                iconUrl = MEXICO_URL
            }
            "6f2a04ca-43e0-be17-7f36-b3908627744d" -> {
                countryName = AUSTRALIA
                iconUrl = AUSTRALIA_URL
            }
            "5f8d3a7f-467b-97f3-062c-13acf203c006" -> {
                countryName = SWEDEN
                iconUrl = SWEDEN_URL
            }
            "22697a3d-45bf-8dd7-4fec-84a9e28c69d7" -> {
                countryName = FRANCE
                iconUrl = FRANCE_URL
            }
            "bb2a4828-46eb-8cd1-e765-15848195d751" -> {
                countryName = PHILIPPINES
                iconUrl = PHILIPPINES_URL
            }
            "dade69b4-4f5a-8528-247b-219e5a1facd6" -> {
                countryName = TURKEY
                iconUrl = TURKEY_URL
            }
            "601dbbe7-43ce-be57-2a40-4abd24953621" -> {
                countryName = ALTERNATIVE
                iconUrl = ""
            }
            else -> {
                countryName = UNKNOWN
                iconUrl = ""
            }
        }
        return AgentOriginDto(countryName, iconUrl)
    }

    override fun getPointsForUltimate(id: String): Int =
        when (id) {
            "117ed9e3-49f3-6512-3ccf-0cada7e3823b",
            "a3bfb853-43b2-7238-a4f1-ad90e9e46bcc",
            "eb93336a-449b-9c1b-0a54-a891f7921d69" -> 6
            "41fb69c1-4189-7b37-f117-bcaf1e96f1bf",
            "9f0d8ba9-4140-b941-57d3-a7ad57c6b417",
            "e370fa57-4757-3604-3648-499e1f642d3f",
            "95b78ed7-4637-86d9-7e41-71ba8c293152",
            "add6443a-41bd-e414-f6ad-e58d267f4e95",
            "bb2a4828-46eb-8cd1-e765-15848195d751",
            "8e253930-4c05-31dd-1b6c-968525494517",
            "6f2a04ca-43e0-be17-7f36-b3908627744d",
            "7f94d92c-4234-0a36-9646-3a87eb8b5c89" -> 7
            "707eab51-4836-f488-046a-cda6bf494859",
            "1e58de9c-4950-5125-93e9-a0aee9f98746",
            "ded3520f-4264-bfed-162d-b080e2abccf9",
            "320b2a48-4d9b-a075-30f1-1f93a9b638fa",
            "569fdd95-4d10-43ab-ca70-79becc718b46",
            "f94c3b30-42be-e959-889c-5aa313dba261",
            "5f8d3a7f-467b-97f3-062c-13acf203c006",
            "22697a3d-45bf-8dd7-4fec-84a9e28c69d7",
            "dade69b4-4f5a-8528-247b-219e5a1facd6",
            "601dbbe7-43ce-be57-2a40-4abd24953621" -> 8
            else -> 0
        }

    override suspend fun getAgents(): JsonElement? =
        storage.getAgents()

    override suspend fun saveAgents(agents: JsonElement) =
        storage.saveAgents(agents)

    override suspend fun removeAgents() =
        storage.removeAgents()

}