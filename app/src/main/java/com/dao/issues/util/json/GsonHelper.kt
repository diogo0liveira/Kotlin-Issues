package com.dao.issues.util.json

import com.dao.issues.model.State
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Created in 26/03/19 22:33.
 *
 * @author Diogo Oliveira.
 */
class GsonHelper
{
    companion object
    {
        fun build(): Gson
        {
            return GsonBuilder().registerTypeAdapter(State::class.java, EnumStateAdapter()).create()
        }
    }

    private class EnumStateAdapter : JsonSerializer<State>, JsonDeserializer<State>
    {
        override fun serialize(state: State, type: Type, context: JsonSerializationContext): JsonElement
        {
            return context.serialize(state.value())
        }

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): State
        {
            return State.valueOf(json.asString)
        }
    }
}